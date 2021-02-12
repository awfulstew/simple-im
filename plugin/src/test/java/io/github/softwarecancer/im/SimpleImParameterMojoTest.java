package io.github.softwarecancer.im;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;

import java.io.File;

public class SimpleImParameterMojoTest extends AbstractMojoTestCase {

  protected void setUp() throws Exception {
    // required for mojo lookups to work
    super.setUp();
  }

  protected void tearDown() throws Exception {
    // required
    super.tearDown();
  }

  public void testMojoGoal() throws Exception {
    File pom = getTestFile("src/test/resources/test-pom.xml");
    assertNotNull(pom);

    SimpleImParameterMojo mojo = (SimpleImParameterMojo) lookupMojo("calibrate", pom);
    assertNotNull(mojo);

    mojo.execute();
  }

}
