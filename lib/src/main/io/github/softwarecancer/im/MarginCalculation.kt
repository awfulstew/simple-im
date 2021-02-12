package io.github.softwarecancer.im

import java.math.BigDecimal

fun calculateWorstOfMargin(crif: List<Crif>, config: CalculationConfig): TotalMargin {
  return Regulation.findRegulationSet(crif, config.role)
    .map { config.from().regulator(it).build() }
    .map { calculateMargin(crif, it) }
    .maxByOrNull { it.margin } ?: TotalMargin(emptyList(), config.resultCurrency, Regulation.BLANK_REGULATOR_STRING)
}

fun calculateMargin(crif: List<Crif>, config: CalculationConfig): TotalMargin {
  return TotalMargin(
    children = crif.asSequence()
      .filter { it.regulation.contains(config.regulator, config.role) }
      .groupBy { it.imModel }
      .map { calculateImModelMargin(model = it.key, crif = it.value, config) }
      .filterNot { it.margin == BigDecimal.ZERO }
      .toList(),
    currency = config.resultCurrency,
    regulator = config.regulator
  )
}

fun calculateSimmMargin(crif: List<Crif>, config: CalculationConfig): TotalMargin {
  return TotalMargin(
    children = crif.asSequence()
      .filter { it.regulation.contains(config.regulator, config.role) }
      .filter { it.imModel == ImModel.SIMM }
      .map { calculateImModelSimmMargin(crif, config) }
      .toList(),
    currency = config.resultCurrency,
    regulator = config.regulator
  )
}

fun calculateScheduleMargin(crif: List<Crif>, config: CalculationConfig): TotalMargin {
  return TotalMargin(
    children = crif.asSequence()
      .filter { it.imModel == ImModel.SCHEDULE }
      .map { calculateImModelScheduleMargin(crif, config) }
      .toList(),
    currency = config.resultCurrency,
    regulator = config.regulator
  )
}

fun calculateImModelMargin(model: ImModel, crif: List<Crif>, config: CalculationConfig): ImModelMargin {
  return when (model) {
    ImModel.SIMM -> calculateImModelSimmMargin(crif, config)
    ImModel.SCHEDULE -> calculateImModelScheduleMargin(crif, config)
  }
}

fun calculateImModelSimmMargin(crif: List<Crif>, config: CalculationConfig): ImModelMargin {
  TODO()
}

fun calculateImModelScheduleMargin(crif: List<Crif>, config: CalculationConfig): ImModelMargin {
  val netted = crif.groupBy { it.identifier }
    .map { group ->
      ScheduleSensitivity(
        config = config,
        group = group.key,
        crif = group.value,
        margin = crif.map { it.getValue(config) }.reduceOrNull(BigDecimal::add)
          ?: BigDecimal.ZERO,
        index = 3,
        type = "Trade",
      )
    }
  val pvs = netted.filter { it.group.risk == RiskType.SCHEDULE_PV }
  val grossIm = netted.filter { it.group.risk == RiskType.SCHEDULE_NOTIONAL }
    .ifEmpty { pvs.map { it.notional } }
    .map { it.margin.abs() }
    .reduceOrNull(BigDecimal::add) ?: BigDecimal.ZERO

  return when (config.scheduleCalculationType) {
    ScheduleCalculationType.NONE -> ImModelMargin(
      identifier = ImModel.SCHEDULE,
      margin = BigDecimal.ZERO,
      children = emptyList(),
      currency = config.resultCurrency,
      regulator = config.regulator,
    )
    ScheduleCalculationType.WITHOUT_PVS -> TODO()
    ScheduleCalculationType.WITH_PVS -> TODO()
  }
}

