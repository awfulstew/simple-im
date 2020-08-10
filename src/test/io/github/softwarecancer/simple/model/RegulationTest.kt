package io.github.softwarecancer.simple.model

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class RegulationTest {

  @Test
  fun `test crif regulation from role method`() {
    val reg = Regulation("[\"CPS\",\"   HELLO\", \"OTHER\" ]", "[]")
    assertEquals(setOf("CPS", "HELLO", "OTHER"), reg.byRole(ImRole.PLEDGOR))
    assertEquals(setOf(""), reg.byRole(ImRole.SECURED))
  }

}