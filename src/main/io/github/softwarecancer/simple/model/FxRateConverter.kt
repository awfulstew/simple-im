package io.github.softwarecancer.simple.model

import java.math.BigDecimal

interface FxRateConverter {
  fun convert(amount: BigDecimal, from: String, to: String): BigDecimal
}