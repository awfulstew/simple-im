package io.github.softwarecancer.im.simple.model

class Regulation(private val postRegulation: String, private val collectRegulation: String) {

  private val spaceRegex = """\s+""".toRegex()
  private val bracketRegex = """\[(.*)]""".toRegex()
  private val quoteRegex = """"([^,]*)"""".toRegex()

  companion object {
    @JvmStatic val INCLUDED = "INCLUDED"
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
      Role.SECURED -> collectRegulation.toUpperCase()
      Role.PLEDGOR -> postRegulation.toUpperCase()
    }

    regulation = regulation.replace(bracketRegex, "$1")
    regulation = regulation.replace(quoteRegex, "$1")
    regulation = regulation.replace(spaceRegex, "")
    return regulation.split(",").toSet()
  }

  fun contains(regulator: String, role: Role): Boolean {
    return byRole(role).any { it == ALL_REGULATORS_STRING
        || regulator == INCLUDED && postRegulation == INCLUDED
        || regulator == INCLUDED && collectRegulation == INCLUDED
        || it.equals(regulator, ignoreCase = true)
    }
  }

}

fun findRegulationSet(crif: List<Crif>, role: Regulation.Role): Set<String> {
  // get the full set of regulators for the current role
  val regulators: Set<String> = crif.asSequence()
    .map { it.regulation.byRole(role) }.flatten()
    .filterNot { it == Regulation.BLANK_REGULATOR_STRING }
    .filterNot { it == Regulation.ALL_REGULATORS_STRING }
    .toSet()

  // now we need to check which 'mode' of regulation we are in: all blank, 'included', or standard regs
  val modeCheck: MutableSet<String> = HashSet(regulators)

  // we also need to check the  other roles regs because having all post regs blank but all collect regs filled
  // would not fall under the 'all blank' mode of calculation -- so add other role to the set we will check
  crif.map { it.regulation.byRole(role.swap()) }.flatten()
    .filterNot { it == Regulation.BLANK_REGULATOR_STRING }
    .filterNot { it == Regulation.ALL_REGULATORS_STRING }
    .forEach(modeCheck::add)

  // now use the mode check to determine what the set of regulators is
  return when {
    modeCheck.size == 0 -> setOf(Regulation.BLANK_REGULATOR_STRING)
    modeCheck.size == 1 && modeCheck.contains(Regulation.INCLUDED) -> setOf(Regulation.INCLUDED)
    else -> regulators
  }
}