package io.github.softwarecancer.im;

import org.apache.maven.DefaultMaven
import org.apache.maven.Maven
import org.apache.maven.execution.*
import org.apache.maven.plugin.Mojo
import org.apache.maven.plugin.testing.AbstractMojoTestCase
import org.apache.maven.project.MavenProject
import org.apache.maven.project.ProjectBuilder
import org.eclipse.aether.DefaultRepositorySystemSession
import org.eclipse.aether.internal.impl.SimpleLocalRepositoryManagerFactory
import org.eclipse.aether.repository.LocalRepository
import org.junit.Assert
import java.io.File
import java.util.*
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test


class SimpleImParameterConfigMojoTest : AbstractMojoTestCase() {

  @BeforeTest
  @Throws(Exception::class)
  override fun setUp() {
    // required for mojo lookups to work
    super.setUp()
  }

  @AfterTest
  @Throws(Exception::class)
  override fun tearDown() {
    // required
    super.tearDown()
    getTestFile("target/generated-test-sources/project-to-test").deleteRecursively()
  }

  fun newMavenSession(): MavenSession {
    return try {
      val request: MavenExecutionRequest = DefaultMavenExecutionRequest()
      val result: MavenExecutionResult = DefaultMavenExecutionResult()

      // populate sensible defaults, including repository basedir and remote repos
      val populator: MavenExecutionRequestPopulator
      populator = container.lookup(MavenExecutionRequestPopulator::class.java)
      populator.populateDefaults(request)

      // this is needed to allow java profiles to get resolved
      request.systemProperties = System.getProperties()

      // and this is needed so that the repo session in the maven session
      // has a repo manager, and it points at the local repo
      // (cf MavenRepositorySystemUtils.newSession() which is what is otherwise done)
      val maven = container.lookup(Maven::class.java) as DefaultMaven
      val repoSession =
        maven.newRepositorySession(request) as DefaultRepositorySystemSession
      repoSession.localRepositoryManager = SimpleLocalRepositoryManagerFactory().newInstance(
        repoSession,
        LocalRepository(request.localRepository.basedir)
      )
      MavenSession(
        container,
        repoSession,
        request, result
      )
    } catch (e: java.lang.Exception) {
      throw RuntimeException(e)
    }
  }

  /** Extends the super to use the new [.newMavenSession] introduced here
   * which sets the defaults one expects from maven; the standard test case leaves a lot of things blank  */
  override fun newMavenSession(project: MavenProject?): MavenSession? {
    val session = newMavenSession()
    session.currentProject = project
    session.projects = Arrays.asList(project)
    return session
  }

  /** As [.lookupConfiguredMojo] but taking the pom file
   * and creating the [MavenProject].  */
  @Throws(Exception::class)
  fun lookupConfiguredMojo(pom: File, goal: String?): Mojo {
    assertNotNull(pom)
    assertTrue(pom.exists())
    val buildingRequest = newMavenSession().projectBuildingRequest
    val projectBuilder = lookup(ProjectBuilder::class.java)
    val project = projectBuilder.build(pom, buildingRequest).project
    return lookupConfiguredMojo(project, goal)
  }

  @Test
  @Throws(Exception::class)
  fun testMojoGoal() {
    val pom: File = getTestFile("src/test/resources/test-pom.xml")
    assertNotNull(pom)

    val mojo = lookupConfiguredMojo(pom, MOJO_GOAL_NAME) as SimpleImParameterConfigMojo
    assertNotNull(mojo)

    mojo.execute()
    Assert.assertTrue(getTestFile("target/generated-test-sources/project-to-test").exists())
  }

}
