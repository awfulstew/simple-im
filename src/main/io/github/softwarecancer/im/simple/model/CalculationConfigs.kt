package io.github.softwarecancer.im.simple.model

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
  ONE_DAY,
  TEN_DAY
}

class CalculationConfig(
  val resultCurrency: String?,
  val role: Regulation.Role?,
  val simmCalculationType: SimmCalculationType?,
  val holdingPeriod: HoldingPeriod?,
  val calculationCurrency: String?,
  val useSingleProductType: Boolean?,
  val countTradesPerLevel: Boolean?,
  val enableConcentrationThresholds: Boolean?,
  val scheduleCalculationType: ScheduleCalculationType?,
  val netGrossRate: BigDecimal?
) {

  data class Builder(
    private var resultCurrency: String? = null,
    private var role: Regulation.Role? = null,
    private var simmCalculationType: SimmCalculationType? = null,
    private var holdingPeriod: HoldingPeriod? = null,
    private var calculationCurrency: String? = null,
    private var useSingleProductType: Boolean? = null,
    private var countTradesPerLevel: Boolean? = null,
    private var enableConcentrationThresholds: Boolean? = null,
    private var scheduleCalculationType: ScheduleCalculationType? = null,
    private var netGrossRate: BigDecimal? = null
  ) {
    fun resultCurrency(currency: String): Builder = apply { this.resultCurrency = currency }
    fun role(role: Regulation.Role): Builder = apply { this.role = role }
    fun simmCalculationType(type: SimmCalculationType): Builder = apply { this.simmCalculationType = type }
    fun holdingPeriod(period: HoldingPeriod): Builder = apply { this.holdingPeriod = period }
    fun calculationCurrency(currency: String): Builder = apply { this.calculationCurrency = currency }
    fun useSingleProductType(useSingleProductType: Boolean): Builder = apply { this.useSingleProductType = useSingleProductType }
    fun countTradesPerLevel(countTradesPerLevel: Boolean): Builder = apply { this.countTradesPerLevel = countTradesPerLevel }
    fun enableConcentrationThresholds(enabled: Boolean): Builder = apply { this.enableConcentrationThresholds = enabled }
    fun scheduleCalculationType(type: ScheduleCalculationType): Builder = apply { this.scheduleCalculationType = type }
    fun netGrossRate(netGrossRate: BigDecimal): Builder = apply { this.netGrossRate = netGrossRate }
    fun build() = CalculationConfig(
      this.resultCurrency,
      this.role,
      this.simmCalculationType,
      this.holdingPeriod,
      this.calculationCurrency,
      this.useSingleProductType,
      this.countTradesPerLevel,
      this.enableConcentrationThresholds,
      this.scheduleCalculationType,
      this.netGrossRate
    )
  }

}
