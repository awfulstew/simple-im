<#assign buildTime = .now>
/**
 * SimpleImParameterInfo.java
 *
 * Generated on ${buildTime?string.full} by simple-im-maven-plugin (${pluginVersion})
 * DO NOT EDIT
 */
package ${modelPackage}.parameter;

import java.util.Objects;
import javax.annotation.Generated;

@Generated(value="SimpleImParameterMojo", date="${buildTime?iso_utc}")
public class SimpleImParameterInfo {

  private final String version;
  private final String mpor;
  private final String imModel;

  public SimpleImParameterInfo(String model, String version, String mpor) {
    this.imModel = model;
    this.version = version;
    this.mpor = mpor;
  }

  public String getImModel() {
    return imModel;
  }

  public String getMpor() {
    return mpor;
  }

  public String getVersion() {
    return version;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof SimpleParameterInfo)) {
      return false;
    } else {
      SimpleImParameterInfo info = (SimpleImParameterInfo) other;
      return Objects.equals(info.getMpor(), this.mpor)
        && Objects.equals(info.getVersion(), this.version)
        && Objects.equals(info.getImModel(), this.imModel);
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(imModel, version, mpor);
  }

}
