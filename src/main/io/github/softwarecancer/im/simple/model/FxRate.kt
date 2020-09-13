package io.github.softwarecancer.im.simple.model

import java.math.BigDecimal

interface FxRate {

  companion object {
    // singleton fx rate implementation that we use for this calculation
    lateinit var singleton: FxRate
  }

  fun convert(amount: BigDecimal, from: String, to: String): BigDecimal
}