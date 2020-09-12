package io.github.softwarecancer.im.simple.model

import java.math.BigDecimal

interface FxRate {
  fun convert(amount: BigDecimal, from: String, to: String): BigDecimal
}