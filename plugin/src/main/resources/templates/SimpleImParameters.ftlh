<#assign buildTime = .now>
/**
 * SimpleImParameters.java
 *
 * Generated on ${buildTime?string.full} by simple-im-maven-plugin
 * DO NOT EDIT
 */
package ${modelPackage}.parameter;

import ${modelPackage}.parameter.SimpleImParameterInfo;
import ${modelPackage}.parameter.SimpleImParameterIdentifier;
import ${modelPackage}.parameter.SimpleImParameterType;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;

@Generated(value="SimpleImParameterMojo", date="${buildTime?iso_utc}")
public class SimpleImParameters {

  private static final BigDecimal CREDIT_UNDER_2_YEARS = new BigDecimal(".02");
  private static final BigDecimal CREDIT_2_TO_5_YEARS = new BigDecimal(".05");
  private static final BigDecimal CREDIT_OVER_5_YEARS = new BigDecimal(".10");
  private static final BigDecimal COMMODITY = new BigDecimal(".15");
  private static final BigDecimal EQUITY = new BigDecimal(".15");
  private static final BigDecimal FX = new BigDecimal(".06");
  private static final BigDecimal RATES_UNDER_2_YEARS = new BigDecimal(".01");
  private static final BigDecimal RATES_2_TO_5_YEARS = new BigDecimal(".02");
  private static final BigDecimal RATES_OVER_5_YEARS = new BigDecimal(".04");
  private static final BigDecimal OTHER = new BigDecimal(".15");

  public static final Map<SimpleImParameterInfo, Map<SimpleImParameterIdentifier, BigDecimal>> PARAMETERS = new HashMap<>();
  static {
    // add the default schedule (grid) parameter values
    Map<SimpleImParameterIdentifier, BigDecimal> SCHEDULE_DEFAULT_NULL = new HashMap<>();
    SCHEDULE_DEFAULT_NULL.put(new SimpleImParameterIdentifier(SimpleImParameterType.WEIGHT, "credit", null, "1", null, null), CREDIT_UNDER_2_YEARS);
    SCHEDULE_DEFAULT_NULL.put(new SimpleImParameterIdentifier(SimpleImParameterType.WEIGHT, "credit", null, "2", null, null), CREDIT_2_TO_5_YEARS);
    SCHEDULE_DEFAULT_NULL.put(new SimpleImParameterIdentifier(SimpleImParameterType.WEIGHT, "credit", null, "3", null, null), CREDIT_OVER_5_YEARS);
    SCHEDULE_DEFAULT_NULL.put(new SimpleImParameterIdentifier(SimpleImParameterType.WEIGHT, "commodity", null, "1", null, null), COMMODITY);
    SCHEDULE_DEFAULT_NULL.put(new SimpleImParameterIdentifier(SimpleImParameterType.WEIGHT, "equity", null, "1", null, null), EQUITY);
    SCHEDULE_DEFAULT_NULL.put(new SimpleImParameterIdentifier(SimpleImParameterType.WEIGHT, "fx", null, "1", null, null), FX);
    SCHEDULE_DEFAULT_NULL.put(new SimpleImParameterIdentifier(SimpleImParameterType.WEIGHT, "rates", null, "1", null, null), RATES_UNDER_2_YEARS);
    SCHEDULE_DEFAULT_NULL.put(new SimpleImParameterIdentifier(SimpleImParameterType.WEIGHT, "rates", null, "2", null, null), RATES_2_TO_5_YEARS);
    SCHEDULE_DEFAULT_NULL.put(new SimpleImParameterIdentifier(SimpleImParameterType.WEIGHT, "rates", null, "3", null, null), RATES_OVER_5_YEARS);
    SCHEDULE_DEFAULT_NULL.put(new SimpleImParameterIdentifier(SimpleImParameterType.WEIGHT, "other", null, "1", null, null), OTHER);
    PARAMETERS.put(new SimpleImParameterInfo("schedule", "default", null), SCHEDULE_DEFAULT_NULL);

    <#list files as file>
    // add the ${file.version?string!default} simm parameter for ${file.mpor?string!null} day margin period-of-risk
    Map<SimpleImParameterIdentifier, BigDecimal> SIMM_${file.version?replace(".", "_")!DEFAULT}_MPOR${file.mpor!NULL} = new HashMap<>();
    <#list file.parameters as parameter>
    <#assign parameterType = parameter.type?lower_case?switch("weight", "SimpleImParameterType.WEIGHT", "conc", "SimpleImParameterType.CONCENTRATION_THRESHOLD", "corr", "SimpleImParameterType.INNER_CORRELATION", "corrouter", "SimpleImParameterType.OUTER_CORRELATION", "hvr", "SimpleImParameterType.HVR")>
    SIMM_${file.version?replace(".", "_")!DEFAULT}_MPOR${file.mpor!NULL}.put(new SimpleImParameterIdentifier(${parameterType}, null, "${parameter.risk}", <#if parameter.bucket??>"${parameter.bucket}"<#else>null</#if>, <#if parameter.labelOne??>"${parameter.labelOne}"<#else>null</#if>, <#if parameter.labelTwo??>"${parameter.labelTwo}"<#else>null</#if>), new BigDecimal("${parameter.value}"));
    </#list>
    PARAMETERS.put(new SimpleImParameterInfo("simm", "${file.version!default}", <#if file.mpor??>"${file.mpor}"<#else>null</#if>), SIMM_${file.version?replace(".", "_")!DEFAULT}_MPOR${file.mpor!NULL});

    </#list>
  }

  public static final Map<SimpleImParameterInfo, Map<SimpleImParameterIdentifier, String>> CURRENCY_LISTS = new HashMap<>();
  static {
    <#list files as file>
    // add the ${file.version!default} simm currency list for ${file.mpor!null} day margin period-of-risk
    Map<SimpleImParameterIdentifier, String> SIMM_${file.version?replace(".", "_")!DEFAULT}_MPOR${file.mpor?string!NULL} = new HashMap<>();
    <#list file.currencies as currency>
    SIMM_${file.version?replace(".", "_")!DEFAULT}_MPOR${file.mpor?string!NULL}.put(new SimpleImParameterIdentifier(SimpleImParameterType.CURRENCY, null, "${currency.risk}", "${currency.bucket}", null, null), "${currency.value}");
    </#list>
    CURRENCY_LISTS.put(new SimpleImParameterInfo("simm", "${file.version!default}", <#if file.mpor??>"${file.mpor}"<#else>null</#if>), SIMM_${file.version?replace(".", "_")!DEFAULT}_MPOR${file.mpor!NULL});

    </#list>
  }

  }

