package io.github.softwarecancer.im.simple.model

import java.math.BigDecimal

class WorstOfMargin(
  override val margin: BigDecimal,
  override val children: List<ImModelMargin>,
  override val currency: String,
  override val regulator: String
) : ImTree<TotalIdentifier, ImModelMargin>, MarginResult {
  override val level: String = "1.Total"
  override val identifier: TotalIdentifier = TotalIdentifier()
  companion object {
    @JvmStatic fun calculate(crif: List<Crif>, config: CalculationConfig) {

    }
  }
}

class ImModelMargin(
  override val identifier: ImModel,
  override val margin: BigDecimal,
) : ImTree<ImModel, Nothing> {
  override val level: String = "2.Model"
  override val children: List<Nothing> = emptyList()
}