package io.github.softwarecancer.simple.model

class Regulation(private val postRegulation: String, private val collectRegulation: String) {

  private val spaceRegex = """\s+""".toRegex()
  private val bracketRegex = """\[(.*)]""".toRegex()
  private val quoteRegex = """"([^,]*)"""".toRegex()

  companion object {
    @JvmStatic val INCLUDED = "included"
    @JvmStatic val BLANK_REGULATOR_STRING = ""
    @JvmStatic val ALL_REGULATORS_STRING = "SIMPLE_ALL_REGS_WILDCARD"
  }

  enum class Role(private val role: String) {
    SECURED("Secured"),
    PLEDGOR("Pledgor");

    fun swap(): Role {
      return when(this) {
        SECURED -> PLEDGOR
        PLEDGOR -> SECURED
      }
    }

    override fun toString(): String {
      return role
    }
  }

  fun byRole(role: Role): Set<String> {
    var regulation: String = when (role) {
      Role.SECURED -> collectRegulation
      Role.PLEDGOR -> postRegulation
    }

    regulation = regulation.replace(bracketRegex, "$1")
    regulation = regulation.replace(quoteRegex, "$1")
    regulation = regulation.replace(spaceRegex, "")
    return regulation.split(",").toSet()
  }

}