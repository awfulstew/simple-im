package io.github.softwarecancer.im.simple

import java.lang.IllegalStateException

class Regulation(_postRegulation: String, _collectRegulation: String) {

  companion object {
    const val INCLUDED = "INCLUDED"
    const val BLANK_REGULATOR_STRING = ""
    const val ALL_REGULATORS_STRING = "SIMPLE_ALL_REGS_WILDCARD"
    val spaceRegex = """\s+""".toRegex()
    val bracketRegex = """\[(.*)]""".toRegex()
    val quoteRegex = """"([^,]*)"""".toRegex()

    @JvmStatic fun findRegulationSet(crif: List<Crif>, role: Role): Set<String> {
      // get the overall mode of this set of CRIF by checking the mode of each one
      val mode: Mode = crif.asSequence()
        .map { it.regulation.mode }
        .reduce { acc, it -> acc.chooseByComparison(it) }

      // we return a different regulation set depending on the mode
      return when (mode) {
        Mode.BLANK -> setOf(BLANK_REGULATOR_STRING)
        Mode.INCLUDED -> setOf(INCLUDED)
        Mode.REGULAR -> crif.asSequence()
          .map { it.regulation.byRole(role) }
          .flatten()
          .filterNot { it == BLANK_REGULATOR_STRING }
          .filterNot { it == ALL_REGULATORS_STRING }
          .toSet()
      }
    }
  }

  enum class Role(private val role: String) {
    SECURED("Secured"),
    PLEDGOR("Pledgor"),
    UNSET("Unset");

    override fun toString(): String {
      return role
    }
  }

  enum class Mode {
    BLANK,
    INCLUDED,
    REGULAR;

    fun chooseByComparison(other: Mode): Mode {
      return when (this) {
        BLANK -> other
        REGULAR -> REGULAR
        INCLUDED -> when (other) {
          REGULAR -> REGULAR
          else -> this
        }
      }
    }
  }

  private val post: Set<String> = fromString(_postRegulation)
  private val collect: Set<String> = fromString(_collectRegulation)
  val mode: Mode by lazy {
    val all = post.plus(collect)
      .filterNot { it == ALL_REGULATORS_STRING }
      .filterNot { it == BLANK_REGULATOR_STRING }
      .toSet()
    when {
      all.isEmpty() -> Mode.BLANK
      all.size == 1 && all.contains(INCLUDED) -> Mode.INCLUDED
      else -> Mode.REGULAR
    }
  }

  private fun fromString(regulation: String): Set<String> {
    return regulation.toUpperCase()
      .replace(bracketRegex, "$1")
      .replace(quoteRegex, "$1")
      .replace(spaceRegex, "")
      .split(",")
      .toSet()
  }

  fun byRole(role: Role): Set<String> {
    return when (role) {
      Role.SECURED -> collect
      Role.PLEDGOR -> post
      Role.UNSET -> throw IllegalStateException("Cannot get regulation by role when the role is UNSET.")
    }
  }

  fun contains(regulator: String, role: Role): Boolean {
    return byRole(role).any {
      when {
        // if any of the regulators for the crif are all regulators string then accept
        it == ALL_REGULATORS_STRING -> true
        // if we are checking with the all regulators string then accept
        regulator == ALL_REGULATORS_STRING -> true
        mode == Mode.BLANK -> regulator == BLANK_REGULATOR_STRING
        mode == Mode.INCLUDED -> regulator.toUpperCase() == INCLUDED
        else -> it.equals(regulator, ignoreCase = true)
      }
    }
  }

}