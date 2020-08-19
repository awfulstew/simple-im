package io.github.softwarecancer.im.simple.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Transient
import kotlin.IllegalStateException

@Serializable
data class Crif @JvmOverloads constructor(
  @SerialName("tradeId") val id: String? = null,
  @SerialName("valuationDate") val valDate: String? = null,
  val endDate: String? = null,
  @SerialName("notional") val notionalString: String? = null,
  val notionalCurrency: String? = null,
  val notionalUsd: String? = null,
  @SerialName("imModel") val model: String? = null,
  @SerialName("productClass") val productType: String,
  val riskType: String,
  val qualifier: String? = null,
  @SerialName("bucket") val bucketLabel: String? = null,
  val labelOne: String? = null,
  val labelTwo: String? = null,
  @SerialName("postRegulation") val post: String? = null,
  @SerialName("collectRegulation") val collect: String? = null,
  val amount: String,
  val amountCurrency: String? = null,
  val amountUsd: String? = null,
  @Transient private val _sensitivity: SensitivityType? = null
) {

  @Transient
  val regulation: Regulation = Regulation(
    post ?: Regulation.BLANK_REGULATOR_STRING,
    collect ?: Regulation.BLANK_REGULATOR_STRING
  )

  val notional: Amount by lazy {
    Amount(notionalString!!, notionalCurrency!!, notionalUsd)
  }

  @Transient
  val value: Amount = Amount(amount, amountCurrency, amountUsd)

  val imModel: ImModel by lazy {
    if (model.isNullOrBlank()) {
      when (riskType.toUpperCase()) {
        // TODO: if the notional string is ever change to differentiate between schedule and simm then we need to update this
        "Notional".toUpperCase() -> throw IllegalStateException("Model cannot be unspecified for the notional risk type")
        in RiskType.SIMM_ADD_ON.labels -> ImModel.SIMM
        in RiskType.SIMM_STANDARD.labels -> ImModel.SIMM
        in RiskType.SCHEDULE.labels -> ImModel.SCHEDULE
        else -> throw IllegalStateException("Unknown risk type found when determining unspecified model: [${riskType}]")
      }
    } else {
      when (model.toUpperCase()) {
        in ImModel.SIMM.labels -> ImModel.SIMM
        in ImModel.SCHEDULE.labels -> ImModel.SCHEDULE
        else -> throw IllegalStateException("Unknown IM model: [${model}]")
      }
    }
  }

  val product: ProductType by lazy {
    when (imModel) {
      ImModel.SIMM -> when (productType.toUpperCase()) {
        ProductType.SIMM_RATES_FX.label -> ProductType.SIMM_RATES_FX
        ProductType.SIMM_CREDIT.label -> ProductType.SIMM_CREDIT
        ProductType.SIMM_EQUITY.label -> ProductType.SIMM_EQUITY
        ProductType.SIMM_COMMODITY.label -> ProductType.SIMM_COMMODITY
        ProductType.SIMM_SINGLE.label -> ProductType.SIMM_SINGLE
        else -> throw IllegalStateException("Unknown product label found when determining class for SIMM: [${productType}]")
      }
      ImModel.SCHEDULE -> when(productType.toUpperCase()) {
        ProductType.SCHEDULE_RATES.label -> ProductType.SCHEDULE_RATES
        ProductType.SCHEDULE_EQUITY.label -> ProductType.SCHEDULE_EQUITY
        ProductType.SCHEDULE_FX.label -> ProductType.SCHEDULE_FX
        ProductType.SCHEDULE_COMMODITY.label -> ProductType.SCHEDULE_COMMODITY
        ProductType.SCHEDULE_CREDIT.label -> ProductType.SCHEDULE_CREDIT
        else -> throw IllegalStateException("Unknown product label found when determining class for Schedule: [${productType}]")
      }
    }
  }

  val risk: RiskType by lazy {
    when (riskType.toUpperCase()) {
      in RiskType.SIMM_FX.labels -> RiskType.SIMM_FX
      in RiskType.SIMM_EQUITY.labels -> RiskType.SIMM_EQUITY
      in RiskType.SIMM_CREDIT_QUALIFYING.labels -> RiskType.SIMM_CREDIT_QUALIFYING
      in RiskType.SIMM_CREDIT_NON_QUALIFYING.labels -> RiskType.SIMM_CREDIT_NON_QUALIFYING
      in RiskType.SIMM_COMMODITY.labels -> RiskType.SIMM_COMMODITY
      in RiskType.SIMM_INTEREST_RATES.labels -> RiskType.SIMM_INTEREST_RATES
      in RiskType.SIMM_ADD_ON_FIXED_AMOUNT.labels -> RiskType.SIMM_ADD_ON_FIXED_AMOUNT
      in RiskType.SIMM_ADD_ON_NOTIONAL.labels -> RiskType.SIMM_ADD_ON_NOTIONAL
      in RiskType.SIMM_ADD_ON_NOTIONAL_FACTOR.labels -> RiskType.SIMM_ADD_ON_NOTIONAL_FACTOR
      in RiskType.SIMM_ADD_ON_PRODUCT_MULTIPLIER.labels -> RiskType.SIMM_ADD_ON_PRODUCT_MULTIPLIER
      else -> throw IllegalStateException("Unknown risk label found when determining risk type: [${riskType}]")
    }
  }

  val sensitivity: SensitivityType by lazy {
    when {
      _sensitivity != null -> _sensitivity
      riskType.endsWith(SensitivityType.VEGA_SUFFIX, ignoreCase = true) -> SensitivityType.VEGA
      riskType.equals(RiskType.SIMM_BASE_CORR_LABEL, ignoreCase = true) -> SensitivityType.BASE_CORRELATION
      riskType.toUpperCase() in RiskType.SIMM_STANDARD.labels -> SensitivityType.DELTA
      else -> throw IllegalStateException("Unknown risk type when determining sensitivity type: [${riskType}]")
    }
  }

  val bucket: BucketType by lazy {
    when (risk) {
      RiskType.SIMM_FX -> BucketType.FX
      RiskType.SIMM_INTEREST_RATES -> when {
        qualifier.isNullOrBlank() -> throw IllegalStateException("Qualifier was not specified for IR sensitivity")
        qualifier in BucketType.InterestRateVolatility.REGULAR.currencies -> BucketType.IR_REG
        qualifier in BucketType.InterestRateVolatility.LOW_VOLATILITY.currencies -> BucketType.IR_LOW
        else -> BucketType.IR_HIGH
      }
      else -> BucketType.values().filter { it.bucket.equals(bucketLabel, ignoreCase = true) && it.risk == risk }
        .getOrElse(0) { throw IllegalStateException("Unable to find bucket for bucket [${bucketLabel}] in [${risk}]") }
    }
  }

}