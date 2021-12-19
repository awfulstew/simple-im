<#assign buildTime = .now>
/**
 * SimpleImParameterType.java
 *
 * Generated on ${buildTime?string.full} by simple-im-maven-plugin
 * DO NOT EDIT
 */
package ${modelPackage}.parameter;

import javax.annotation.processing.Generated;

@Generated(value="SimpleImParameterMojo", date="${buildTime?iso_utc}")
public enum SimpleImParameterType {
  WEIGHT,
  CONCENTRATION_THRESHOLD,
  INNER_CORRELATION,
  OUTER_CORRELATION,
  HVR,
  CURRENCY;
}