package io.github.softwarecancer.im.simple.model

import java.math.BigDecimal

interface ImTree<out MarginIdentifier, out ImTree> {
  val level: String
  val identifier: MarginIdentifier
  val margin: BigDecimal
  val children: List<ImTree>
}

interface MarginResult<out E, out F> : ImTree<E, F> {
  val currency: String
  val regulator: String
}

class BlankResult(
  override val currency: String
) : MarginResult<TotalIdentifier, Nothing> {
  override val level = "1.Total"
  override val identifier = TotalIdentifier()
  override val margin: BigDecimal = BigDecimal.ZERO
  override val children: List<Nothing> = emptyList()
  override val regulator = Regulation.BLANK_REGULATOR_STRING
}