<#assign buildTime = .now>
/**
 * SimpleImParameterIdentifier.java
 *
 * Generated on ${buildTime?string.full} by simple-im-maven-plugin (${pluginVersion})
 * DO NOT EDIT
 */
package ${modelPackage}.parameter;

import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(value="SimpleImParameterMojo", date="${buildTime?iso_utc}")
public class SimpleImParameterIdentifier {

  private final SimpleImParameterType type;
  private final String product;
  private final String risk;
  private final String bucket;
  private final String labelOne;
  private final String labelTwo;

  public SimpleImParameterIdentifier(SimpleImParameterType type, String product, String risk, String bucket, String labelOne, String labelTwo) {
    this.type = type;
    this.product = product;
    this.risk = risk;
    this.bucket = bucket;
    this.labelOne = labelOne;
    this.labelTwo = labelTwo;
  }

  public SimpleImParameterType getType() {
    return type;
  }

  public String getProduct() {
    return product;
  }

  public String getRisk() {
    return risk;
  }

  public String getBucket() {
    return bucket;
  }

  public String getLabelOne() {
    return labelOne;
  }

  public String getLabelTwo() {
    return labelTwo;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof SimpleImParameterIdentifier)) {
      return false;
    } else {
      SimpleImParameterIdentifier id = (SimpleImParameterIdentifier) other;
      return id.getType() == this.type
        && Objects.equals(id.getProduct(), this.product)
        && Objects.equals(id.getRisk(), this.risk)
        && Objects.equals(id.getBucket(), this.bucket)
        && Objects.equals(id.getLabelOne(), this.labelOne)
        && Objects.equals(id.getLabelTwo(), this.labelTwo);
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, product, risk, bucket, labelOne, labelTwo);
  }

}
