package io.github.softwarecancer.im

import com.univocity.parsers.tsv.TsvParser
import com.univocity.parsers.tsv.TsvParserSettings
import freemarker.template.Configuration
import freemarker.template.Template
import freemarker.template.TemplateExceptionHandler
import org.apache.commons.io.filefilter.HiddenFileFilter
import org.apache.maven.artifact.Artifact
import org.apache.maven.model.Plugin
import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugin.MojoExecutionException
import org.apache.maven.plugins.annotations.LifecyclePhase
import org.apache.maven.plugins.annotations.Mojo
import org.apache.maven.plugins.annotations.Parameter
import org.apache.maven.project.MavenProject
import java.io.File
import java.io.FileFilter
import java.nio.charset.StandardCharsets

// overall parameters
const val MOJO_GOAL_NAME = "simm-generate-parameters"

// calibration file headers
private const val RISK_TYPE_HEADER = "RiskType"
private const val BUCKET_HEADER = "Bucket"
private const val LABEL_ONE_HEADER = "Label1"
private const val LABEL_TWO_HEADER = "Label2"
private const val PARAMETER_HEADER = "Parameter"

// type prefixes
private const val INFORMATION_PREFIX = "info"
private const val PARAMETER_PREFIX = "calib"

// type suffixes
private const val VERSION_SUFFIX = "version"
private const val MPOR_SUFFIX = "mpor"
private const val CURRENCY_LIST_SUFFIX = "currencylist"
private const val CONCENTRATION_SUFFIX = "conc"
private const val HVR_SUFFIX = "hvr"
private const val WEIGHT_SUFFIX = "weight"
private const val INNER_CORR_SUFFIX = "corr"
private const val OUTER_CORR_SUFFIX = "outercorr"

// data model fields
private const val MODEL_PACKAGE_FIELD = "modelPackage"
private const val FILES_FIELD = "files"
private const val VERSION_FIELD = "version"
private const val MPOR_FIELD = "mpor"
private const val PARAMETERS_FIELD = "parameters"
private const val CURRENCIES_FIELD = "currencies"
private const val FILE_NAME_FIELD = "fileName"
private const val TYPE_FIELD = "type"
private const val RISK_FIELD = "risk"
private const val BUCKET_FIELD = "bucket"
private const val LABEL_ONE_FIELD = "labelOne"
private const val LABEL_TWO_FIELD = "labelTwo"
private const val VALUE_FIELD = "value"

// file extensions
private const val TEMPLATE_EXT = ".ftlh"
private const val JAVA_EXT = ".java"

private val TEMPLATE_FILE_NAMES = listOf(
  "SimpleImParameterIdentifier",
  "SimpleImParameterInfo",
  "SimpleImParameterType",
  "SimpleImParameters"
)

@Mojo(name = MOJO_GOAL_NAME, threadSafe = true, defaultPhase = LifecyclePhase.GENERATE_SOURCES)
class SimpleImParameterConfigMojo : AbstractMojo() {

  @Parameter(defaultValue = "\${project}", required = true, readonly = true)
  lateinit var basis: MavenProject

  @Parameter(defaultValue = "\${project.build.directory}/generated-sources", required = true, readonly = true)
  lateinit var target: File

  @Throws(MojoExecutionException::class)
  override fun execute() {
    log.info("------------------------------------------------------------------------")
    log.info("  GENERATE DATA MODEL FROM CALIBRATIONS")
    log.info("------------------------------------------------------------------------")
    val dataModel = hashMapOf<String, Any>()
    dataModel[MODEL_PACKAGE_FIELD] = basis.groupId // assume set from the build project groupId
    dataModel[FILES_FIELD] = File("${basis.resources[0].directory}/calibrations")
      .listFiles(HiddenFileFilter.VISIBLE as FileFilter)!!
      .onEach { log.info("Generating data model for calibrations file [${it.name}]") }
      .map { processCalibrationFile(it) }
      .onEach { log.info("Calibration file [${it[FILE_NAME_FIELD]}] . . . . . DONE!") }
      .toList()
    log.info("------------------------------------------------------------------------")
    log.info("  APPLYING DATA MODEL TO TEMPLATES")
    log.info("------------------------------------------------------------------------")
    val config = Configuration(Configuration.VERSION_2_3_30)
    config.setClassForTemplateLoading(this.javaClass, "/templates")
    config.defaultEncoding = StandardCharsets.UTF_8.name()
    config.templateExceptionHandler = TemplateExceptionHandler.RETHROW_HANDLER
    config.logTemplateExceptions = false
    config.wrapUncheckedExceptions = true
    config.fallbackOnNullLoopVariable = false
    val packagePath = basis.groupId.replace(".", System.getProperty("file.separator"))
    val targetDir = File("${target.path}/${basis.artifactId}/${packagePath}/parameter").apply { mkdirs() }
    log.info("Generated parameters to be written under [${target.path}/${basis.artifactId}]")
    TEMPLATE_FILE_NAMES.onEach { log.info("Processing template file [${it}${TEMPLATE_EXT}]") }
      .onEach {
        val template: Template = config.getTemplate("${it}${TEMPLATE_EXT}")
        val output = File(targetDir, "${it}${JAVA_EXT}").apply { createNewFile() }
        template.process(dataModel, output.bufferedWriter(charset = Charsets.UTF_8))
      }.onEach { log.info("Template file [${it}${TEMPLATE_EXT}] . . . . . DONE!") }
  }
}

fun processCalibrationFile(file: File): MutableMap<String, Any> {
  val settings = TsvParserSettings().apply { isHeaderExtractionEnabled = true }
  val parser = TsvParser(settings)
  val fileModel = hashMapOf<String, Any>()
  val parameters = arrayListOf<Map<String, String?>>()
  val currencies = arrayListOf<Map<String, String?>>()
  parser.beginParsing(file) // initialize the parser using the file
  file.useLines(charset = Charsets.UTF_8) { lines -> // now we run over each of the lines and process
    lines.map { parser.parseRecord(it) }.filterNotNull().forEach {
      val risk: String = requireNotNull(it.getString(RISK_TYPE_HEADER)).lowercase()
      when {
        // parse out information headers
        risk.startsWith(INFORMATION_PREFIX) && risk.endsWith(VERSION_SUFFIX) -> fileModel[VERSION_FIELD] = it.getString(PARAMETER_HEADER)
        risk.startsWith(INFORMATION_PREFIX) && risk.endsWith(MPOR_SUFFIX) -> fileModel[MPOR_FIELD] = it.getString(PARAMETER_HEADER)
        // currency list is information but requires more set up
        risk.startsWith(INFORMATION_PREFIX) && risk.endsWith(CURRENCY_LIST_SUFFIX) -> {
          val currency: MutableMap<String, String?> = HashMap()
          val split = risk.split("_", ignoreCase = true, limit = 3)
          currency[RISK_FIELD] = split[1]
          currency[BUCKET_FIELD] = it.getString(BUCKET_HEADER)
          currency[VALUE_FIELD] = requireNotNull(it.getString(PARAMETER_HEADER))
          currencies.add(currency)
        }
        // process the actual parameters into the map
        risk.startsWith(PARAMETER_PREFIX) -> {
          val parameter: MutableMap<String, String?> = HashMap()
          val split = risk.split("_", ignoreCase = true, limit = 3)
          parameter[TYPE_FIELD] = split[2];
          parameter[RISK_FIELD] = split[1];
          parameter[BUCKET_FIELD] = it.getString(BUCKET_HEADER);
          parameter[LABEL_ONE_FIELD] = it.getString(LABEL_ONE_HEADER);
          parameter[LABEL_TWO_FIELD] = it.getString(LABEL_TWO_HEADER);
          parameter[VALUE_FIELD] = requireNotNull(it.getString(PARAMETER_HEADER));
          parameters.add(parameter);
        }
        else -> throw MojoExecutionException("Unknown risk type encountered in calibration file: [${risk}]")
      }
    }
  }
  fileModel[FILE_NAME_FIELD] = file.name
  fileModel[PARAMETERS_FIELD] = parameters
  fileModel[CURRENCIES_FIELD] = currencies
  return fileModel
}
