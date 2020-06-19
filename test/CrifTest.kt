import io.github.softwarecancer.simple.Crif
import io.github.softwarecancer.simple.ImRole
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CrifTest {

  @Test
  fun `test crif regulation from role method`() {
    var crif = Crif("SIMM", "[\"CPS\",\"   HELLO\", \"OTHER\" ]", "[]")
    assertEquals(setOf("CPS", "HELLO", "OTHER"), crif.regulationSet(ImRole.PLEDGOR))
    assertEquals(setOf(""), crif.regulationSet(ImRole.SECURED))
  }

}