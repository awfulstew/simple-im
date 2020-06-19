package io.github.softwarecancer.simple

import kotlinx.serialization.Serializable

var SPACE_REGEX = "\\s+".toRegex()
var BRACKET_REGEX = "\\[(.*)]".toRegex()
var QUOTE_REGEX = "\"([^,]*)\"".toRegex()
var FIRST_CAPTURE_GROUP = "$1"
var INCLUDED = "included"
var BLANK_REGULATOR_STRING = ""
var ALL_REGULATORS_STRING = "SIMPLE_ALL_REGS_WILDCARD"

enum class ImRole(name: String) {
  SECURED("Secured"),
  PLEDGOR("Pledgor");

  fun swap(): ImRole {
    return when(this) {
      SECURED -> PLEDGOR
      PLEDGOR -> SECURED
    }
  }

  override fun toString(): String {
    return name
  }
}

@Serializable
data class Crif(val model: String, val postRegulation: String, val collectRegulation: String) {
  companion object {

  }

  fun regulation(role: ImRole): String {
    var regulation: String = when (role) {
      ImRole.SECURED -> collectRegulation
      ImRole.PLEDGOR -> postRegulation
    }

    regulation = regulation.replace(BRACKET_REGEX, FIRST_CAPTURE_GROUP)
    regulation = regulation.replace(QUOTE_REGEX, FIRST_CAPTURE_GROUP)
    regulation = regulation.replace(SPACE_REGEX, "")
    return regulation
  }

  fun regulationSet(role: ImRole): Set<String> {
    return regulation(role).split(",").toSet()
  }
}