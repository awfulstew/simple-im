package io.github.softwarecancer.im.simple.model

import java.math.BigDecimal

class WorstOfMargin(
  override val margin: BigDecimal,
  override val children: List<ImModelMargin>,
  override val currency: String,
  override val regulator: String
) : MarginResult<TotalIdentifier, ImModelMargin> {
  override val level = "1.Total"
  override val identifier = TotalIdentifier()
  companion object {
    @JvmStatic fun calculate(crif: List<Crif>, config: CalculationConfig): MarginResult<TotalIdentifier, ImModelMargin> {
      // TODO: while result currency is referenced here, we do not actually convert from USD
      return findRegulationSet(crif, config.role!!).map { regulator ->
        val margins = crif.filter { it.regulation.contains(regulator, config.role) }
          .groupBy { it.imModel }
          .map { ImModelMargin.calculate(it.key, it.value, config) }
          .toList()
        WorstOfMargin(
          margins.map { it.margin }.reduce(BigDecimal::add),
          margins,
          config.resultCurrency!!,
          regulator
        )
      }.maxByOrNull { it.margin }.let { BlankResult(config.resultCurrency!!) }
    }
  }
}

class ImModelMargin(
  override val identifier: ImModel,
  override val margin: BigDecimal,
) : ImTree<ImModel, Nothing> {
  override val level = "2.Model"
  override val children: List<Nothing> = emptyList()
  companion object {
    @JvmStatic
    fun calculate(model: ImModel, crif: List<Crif>, config: CalculationConfig): ImModelMargin {
      TODO("implement im-model calculation")
    }
  }
}