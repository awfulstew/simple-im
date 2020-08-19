package io.github.softwarecancer.im.simple.model

import java.math.BigDecimal

interface FxRateConverter {
  fun convert(amount: BigDecimal, from: String, to: String): BigDecimal
}