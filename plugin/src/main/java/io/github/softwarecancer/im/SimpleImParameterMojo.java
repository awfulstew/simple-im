package io.github.softwarecancer.im;

import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Mojo(name = "calibrate", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class SimpleImParameterMojo extends AbstractMojo {

  // calibration file headers
  private static final String RISK_TYPE_HEADER = "RiskType";
  private static final String BUCKET_HEADER = "Bucket";
  private static final String LABEL_ONE_HEADER = "Label1";
  private static final String LABEL_TWO_HEADER = "Label2";
  private static final String PARAMETER_HEADER = "Parameter";

  // type prefixes
  private static final String INFORMATION_PREFIX = "info";
  private static final String PARAMETER_PREFIX = "calib";

  // type suffixes
  private static final String CURRENCY_LIST_SUFFIX = "currencylist";
  private static final String CONCENTRATION_SUFFIX = "conc";
  private static final String HVR_SUFFIX = "hvr";
  private static final String WEIGHT_SUFFIX = "weight";
  private static final String INNER_CORR_SUFFIX = "corr";
  private static final String OUTER_CORR_SUFFIX = "outercorr";

  // data model fields
  private static final String MODEL_PACKAGE_FIELD = "modelPackage";
  private static final String PLUGIN_VERSION_FIELD = "pluginVersion";
  private static final String FILES_FIELD = "files";
  private static final String VERSION_FIELD = "version";
  private static final String MPOR_FIELD = "mpor";
  private static final String PARAMETERS_FIELD = "parameters";
  private static final String CURRENCIES_FIELD = "currencies";
  private static final String TYPE_FIELD = "type";
  private static final String RISK_FIELD = "risk";
  private static final String BUCKET_FIELD = "bucket";
  private static final String LABEL_ONE_FIELD = "labelOne";
  private static final String LABEL_TWO_FIELD = "labelTwo";
  private static final String VALUE_FIELD = "value";

  // file extensions
  private static final String TEMPLATE_EXT = ".ftlh";
  private static final String JAVA_EXT = ".java";

  private static final List<String> TEMPLATE_FILE_NAMES = Arrays.asList(
    "SimpleImParameterIdentifier",
    "SimpleImParameterInfo",
    "SimpleImParameterType",
    "SimpleImParameters"
  );

  @Parameter(required = true)
  private File calibrationDirectory;

  @Parameter(required = true)
  private File relativeTargetJavaDir;

  @Parameter(required = true)
  private String modelPackage;

  @Parameter(required = true)
  private String pluginVersion;

  public void execute() throws MojoExecutionException {
    try {
      getLog().info("------------------------------------------------------------------------");
      getLog().info("  GENERATE DATA MODEL FROM CALIBRATIONS");
      getLog().info("------------------------------------------------------------------------");

      // lets prepare the overall data model
      Map<String, Object> dataModel = new HashMap<>();
      dataModel.put(PLUGIN_VERSION_FIELD, pluginVersion);
      dataModel.put(MODEL_PACKAGE_FIELD, modelPackage);

      List<Map<String, Object>> fileModels = new ArrayList<>(); // run over files
      for (File calibration: Objects.requireNonNull(calibrationDirectory.listFiles((FileFilter) HiddenFileFilter.VISIBLE))) {

        getLog().info("Processing calibration file [" + calibration.getName() + "]");
        // lets set up the maps we will need
        Map<String, Object> fileModel = new HashMap<>();
        List<Map<String, String>> parameters = new ArrayList<>();
        List<Map<String, String>> currencies = new ArrayList<>();

        try (Reader reader = new InputStreamReader(new FileInputStream(calibration), StandardCharsets.UTF_8)) {
          TsvParserSettings settings = new TsvParserSettings();
          settings.setHeaderExtractionEnabled(true);
          TsvParser parser = new TsvParser(settings);
          for (Record record: parser.parseAllRecords(reader)) {
            String risk = Objects.requireNonNull(record.getString(RISK_TYPE_HEADER)).toLowerCase();
            if (risk.startsWith(INFORMATION_PREFIX) && risk.endsWith("version")) {
              fileModel.put(VERSION_FIELD, record.getString(PARAMETER_HEADER));
            } else if (risk.startsWith(INFORMATION_PREFIX) && risk.endsWith("mpor")) {
              fileModel.put(MPOR_FIELD, record.getString(PARAMETER_HEADER));
            } else if (risk.startsWith(INFORMATION_PREFIX) && risk.endsWith(CURRENCY_LIST_SUFFIX)) {
              // this is a currency parameter
              Map<String, String> currency = new HashMap<>();
              String[] split = risk.split("_", 3);
              currency.put(RISK_FIELD, split[1]);
              currency.put(BUCKET_FIELD, record.getString(BUCKET_HEADER));
              currency.put(VALUE_FIELD, Objects.requireNonNull(record.getString(PARAMETER_HEADER)));
              currencies.add(currency);
            } else if (risk.startsWith(PARAMETER_PREFIX)) {
              // this is a parameter field
              Map<String, String> parameter = new HashMap<>();
              String[] split = risk.split("_", 3);
              parameter.put(TYPE_FIELD, split[2]);
              parameter.put(RISK_FIELD, split[1]);
              parameter.put(BUCKET_FIELD, record.getString(BUCKET_HEADER));
              parameter.put(LABEL_ONE_FIELD, record.getString(LABEL_ONE_HEADER));
              parameter.put(LABEL_TWO_FIELD, record.getString(LABEL_TWO_HEADER));
              parameter.put(VALUE_FIELD, Objects.requireNonNull(record.getString(PARAMETER_HEADER)));
              parameters.add(parameter);
            } else {
              throw new MojoExecutionException("Unknown risk type encountered in calibration file: [" + risk + "] ");
            }
          }
        }

        // now add currencies and parameters to file model
        fileModel.put(CURRENCIES_FIELD, currencies);
        fileModel.put(PARAMETERS_FIELD, parameters);
        fileModels.add(fileModel);
      }

      getLog().info("------------------------------------------------------------------------");
      getLog().info("  BUILD TEMPLATE CONTEXT FOR PROCESSING");

      Configuration config = new Configuration(Configuration.VERSION_2_3_30);
      config.setClassForTemplateLoading(this.getClass(), "/templates");
      config.setDefaultEncoding(StandardCharsets.UTF_8.name());
      config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
      config.setLogTemplateExceptions(false);
      config.setWrapUncheckedExceptions(true);
      config.setFallbackOnNullLoopVariable(false);

      getLog().info("  APPLY DATA MODEL TO TEMPLATES");
      getLog().info("------------------------------------------------------------------------");

      // set the files to the top level model
      dataModel.put(FILES_FIELD, fileModels);

      // now fetch our template files so we can process with the data model
      File packageUnderTarget = setUpOutputLocations();
      for (String name : TEMPLATE_FILE_NAMES) {
        getLog().info("Processing template file [" + name + TEMPLATE_EXT + "]");
        Template template = config.getTemplate(name + TEMPLATE_EXT);
        File output = new File(packageUnderTarget, name + JAVA_EXT);
        output.createNewFile();
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(output))) {
          template.process(dataModel, writer);
        }
      }

      getLog().info("------------------------------------------------------------------------");
    } catch (IOException | TemplateException | NullPointerException ex) {
      throw new MojoExecutionException(ex.getMessage(), ex);
    }
  }

  private File setUpOutputLocations() {
    String fileSeparator = System.getProperty("file.separator");
    String packageDirectoryName = modelPackage.replace(".", fileSeparator) + fileSeparator + "parameter";
    File packageUnderTarget = new File(relativeTargetJavaDir, packageDirectoryName);
    packageUnderTarget.mkdirs(); // should make everything on the way down
    return packageUnderTarget;
  }
}
