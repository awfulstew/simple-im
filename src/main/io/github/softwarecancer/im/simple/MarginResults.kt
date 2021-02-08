package io.github.softwarecancer.im.simple

import java.math.BigDecimal

interface ImResult< out T : MarginIdentifier, out U > {
  val level: String
  val identifier: T
  val margin: BigDecimal
  val currency: String
  val children: List<U>
  val regulator: String
}

class BlankResult(
  override val currency: String = FxRate.USD,
  override val regulator: String = Regulation.BLANK_REGULATOR_STRING,
) : ImResult<TotalIdentifier, Nothing> {
  override val level = "1.Total"
  override val identifier = TotalIdentifier()
  override val margin: BigDecimal = BigDecimal.ZERO
  override val children: List<Nothing> = emptyList()
}