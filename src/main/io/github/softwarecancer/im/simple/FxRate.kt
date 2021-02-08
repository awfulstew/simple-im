package io.github.softwarecancer.im.simple

import java.math.BigDecimal

interface FxRate {

  companion object {
    const val USD = "USD"
  }

  class NoConversion : FxRate {
    override fun convert(amount: BigDecimal, from: String, to: String): BigDecimal = amount
  }

  fun convert(amount: BigDecimal, from: String, to: String): BigDecimal
}