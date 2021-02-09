package io.github.softwarecancer.im.simple

import java.math.BigDecimal

enum class SimmCalculationType {
  NONE,
  STANDARD,
  ADDITIONAL,
  TOTAL
}

enum class ScheduleCalculationType {
  NONE,
  WITH_PVS,
  WITHOUT_PVS
}

enum class HoldingPeriod {
  NO_PERIOD,
  ONE_DAY,
  TEN_DAY
}

data class CalculationConfig(
  val resultCurrency: String,
  val role: Regulation.Role,
  val simmCalculationType: SimmCalculationType,
  val holdingPeriod: HoldingPeriod,
  val calculationCurrency: String,
  val countTradesPerLevel: Boolean,
  val enableConcentrationThresholds: Boolean,
  val scheduleCalculationType: ScheduleCalculationType,
  val netGrossRate: BigDecimal,
  val fxRate: FxRate,
  val regulator: String,
  val enableScheduleProductMargin: Boolean,
) {

  fun from(): Builder {
    return Builder(
      resultCurrency = this.resultCurrency,
      role = this.role,
      simmCalculationType = this.simmCalculationType,
      holdingPeriod = this.holdingPeriod,
      calculationCurrency = this.calculationCurrency,
      countTradesPerLevel = this.countTradesPerLevel,
      enableConcentrationThresholds = this.enableConcentrationThresholds,
      scheduleCalculationType = this.scheduleCalculationType,
      netGrossRate = this.netGrossRate,
      fxRate = this.fxRate,
      regulator = this.regulator,
      enableScheduleProductMargin = this.enableScheduleProductMargin,
    )
  }

  class Builder(
    private var resultCurrency: String = "USD",
    private var role: Regulation.Role = Regulation.Role.UNSET,
    private var simmCalculationType: SimmCalculationType = SimmCalculationType.NONE,
    private var holdingPeriod: HoldingPeriod = HoldingPeriod.NO_PERIOD,
    private var calculationCurrency: String = "USD",
    private var countTradesPerLevel: Boolean = false,
    private var enableConcentrationThresholds: Boolean = true,
    private var scheduleCalculationType: ScheduleCalculationType = ScheduleCalculationType.NONE,
    private var netGrossRate: BigDecimal = BigDecimal.ZERO,
    private var fxRate: FxRate = FxRate.NoConversion(),
    private var regulator: String = Regulation.BLANK_REGULATOR_STRING,
    private var enableScheduleProductMargin: Boolean = true,
    ) {
    fun resultCurrency(currency: String): Builder = apply { this.resultCurrency = currency }
    fun role(role: Regulation.Role): Builder = apply { this.role = role }
    fun simmCalculationType(type: SimmCalculationType): Builder = apply { this.simmCalculationType = type }
    fun holdingPeriod(period: HoldingPeriod): Builder = apply { this.holdingPeriod = period }
    fun calculationCurrency(currency: String): Builder = apply { this.calculationCurrency = currency }
    fun countTradesPerLevel(countTradesPerLevel: Boolean): Builder = apply { this.countTradesPerLevel = countTradesPerLevel }
    fun enableConcentrationThresholds(enabled: Boolean): Builder = apply { this.enableConcentrationThresholds = enabled }
    fun scheduleCalculationType(type: ScheduleCalculationType): Builder = apply { this.scheduleCalculationType = type }
    fun netGrossRate(netGrossRate: BigDecimal): Builder = apply { this.netGrossRate = netGrossRate }
    fun fxRate(fxRate: FxRate): Builder = apply { this.fxRate = fxRate }
    fun regulator(regulator: String): Builder = apply { this.regulator = regulator }
    fun enableScheduleProductMargin(enableScheduleProductMargin: Boolean) = apply { this.enableScheduleProductMargin = enableScheduleProductMargin }
    fun build() = CalculationConfig(
      resultCurrency = this.resultCurrency,
      role = this.role,
      simmCalculationType = this.simmCalculationType,
      holdingPeriod = this.holdingPeriod,
      calculationCurrency = this.calculationCurrency,
      countTradesPerLevel = this.countTradesPerLevel,
      enableConcentrationThresholds = this.enableConcentrationThresholds,
      scheduleCalculationType = this.scheduleCalculationType,
      netGrossRate = this.netGrossRate,
      fxRate = this.fxRate,
      regulator = this.regulator,
      enableScheduleProductMargin = this.enableScheduleProductMargin,
    )
  }

}
