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
  private val resultCurrency: String?,
  private val role: Regulation.Role?,
  private val simmCalculationType: SimmCalculationType?,
  private val holdingPeriod: HoldingPeriod?,
  private val calculationCurrency: String?,
  private val useSingleProductType: Boolean?,
  private val countTradesPerLevel: Boolean?,
  private val enableConcentrationThresholds: Boolean?,
  private val scheduleCalculationType: ScheduleCalculationType?,
  private val netGrossRate: BigDecimal?
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
