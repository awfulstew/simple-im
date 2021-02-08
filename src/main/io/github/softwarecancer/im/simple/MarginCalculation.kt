package io.github.softwarecancer.im.simple

import java.math.BigDecimal

sealed class TotalMargin(
  _children: List<ImModelMargin>,
) : ImResult<TotalIdentifier, ImModelMargin> {
  override val level = "0.Total"
  override val identifier = TotalIdentifier()
  override val margin = sumMargin(_children)
}

class WorstOf(
  override val children: List<ImModelMargin>,
  override val currency: String,
  override val regulator: String,
) : TotalMargin(children) {

  companion object {
    @JvmStatic
    fun calculate(crif: List<Crif>, config: CalculationConfig): ImResult<TotalIdentifier, ImModelMargin> {
      return Regulation.findRegulationSet(crif, config.role).map { regulator ->
        val configWithRegulation = config.from().regulator(regulator).build()
        WorstOf(
          children = crif.asSequence()
            .filter { it.regulation.contains(configWithRegulation.regulator, configWithRegulation.role) }
            .groupBy { it.imModel }
            .map { ImModelMargin.calculate(model = it.key, crif = it.value, configWithRegulation) }
            .filterNot { it.margin == BigDecimal.ZERO }
            .toList(),
          currency = configWithRegulation.resultCurrency,
          regulator = configWithRegulation.regulator,
        )
      }.maxByOrNull { it.margin } ?: BlankResult(currency = config.resultCurrency, regulator = Regulation.BLANK_REGULATOR_STRING)
    }
  }
}

class Simm(
  override val children: List<ImModelMargin>,
  override val currency: String,
  override val regulator: String,
) : TotalMargin(children) {
  companion object {
    @JvmStatic
    fun calculate(crif: List<Crif>, config: CalculationConfig): ImResult<TotalIdentifier, ImModelMargin> {
      return Simm(
        children = crif.asSequence()
          .filter { it.imModel == ImModel.SIMM }
          .map { SimmMargin.calculate(crif, config) }
          .toList(),
        currency = config.resultCurrency,
        regulator = config.regulator
      )
    }
  }
}

class Schedule(
  override val children: List<ImModelMargin>,
  override val currency: String,
  override val regulator: String,
) : TotalMargin(children) {
  companion object {
    @JvmStatic
    fun calculate(crif: List<Crif>, config: CalculationConfig): ImResult<TotalIdentifier, ImModelMargin> {
      return Simm(
        children = crif.asSequence()
          .filter { it.imModel == ImModel.SCHEDULE }
          .map { ScheduleMargin.calculate(crif, config) }
          .toList(),
        currency = config.resultCurrency,
        regulator = config.regulator
      )
    }
  }
}

sealed class ImModelMargin(
  override val identifier: ImModel,
  override val margin: BigDecimal,
  override val children: List<ProductMargin>,
  override val currency: String,
  override val regulator: String,
) : ImResult<ImModel, ProductMargin> {

  companion object {
    @JvmStatic
    fun calculate(model: ImModel, crif: List<Crif>, config: CalculationConfig): ImModelMargin {
      return when (model) {
        ImModel.SIMM -> SimmMargin.calculate(crif, config)
        ImModel.SCHEDULE -> ScheduleMargin.calculate(crif, config)
      }
    }
  }

  override val level = "1.Model"
}

class SimmMargin(
  override val children: List<ProductMargin>,
  override val currency: String,
  override val regulator: String,
) : ImModelMargin(ImModel.SIMM, sumMargin(children), children, currency, regulator) {

  companion object {
    @JvmStatic
    fun calculate(crif: List<Crif>, config: CalculationConfig): SimmMargin {
      TODO("implement im-model calculation")
    }
  }

}

class ScheduleMargin(
  override val margin: BigDecimal,
  override val children: List<Nothing>,
  override val currency: String,
  override val regulator: String,
) : ImModelMargin(ImModel.SCHEDULE, margin, children, currency, regulator) {

  companion object {
    @JvmStatic
    fun calculate(crif: List<Crif>, config: CalculationConfig): ScheduleMargin {
      TODO("implement im-model calculation")
    }
  }

}

sealed class ProductMargin(
  override val identifier: ProductType,
  override val margin: BigDecimal,
  override val children: List<Nothing>,
  override val currency: String,
  override val regulator: String,
) : ImResult<ProductType, Nothing> {

}