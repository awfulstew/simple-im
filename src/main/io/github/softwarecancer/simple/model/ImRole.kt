package io.github.softwarecancer.simple.model

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