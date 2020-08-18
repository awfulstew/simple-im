package io.github.softwarecancer.simple.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Transient
import java.lang.IllegalStateException

@Serializable
data class Crif(@SerialName("tradeId") val id: String? = null,
                @SerialName("valuationDate") val valDate: String? = null, val endDate: String? = null,
                @SerialName("notional") val notionalString: String? = null, val notionalCurrency: String? = null, val notionalUsd: String? = null,
                @SerialName("imModel") val model: String? = null,
                @SerialName("productClass") val productType: String, val riskType: String,
                val qualifier: String? = null, val bucket: String? = null, val labelOne: String? = null, val labelTwo: String? = null,
                @SerialName("postRegulation") val post: String? = null, @SerialName("collectRegulation") val collect: String? = null,
                @SerialName("amount") val amountString: String, val amountCurrency: String? = null, val amountUsd: String? = null) {

  @Transient
  val regulation: Regulation = Regulation(
    post ?: Regulation.BLANK_REGULATOR_STRING,
    collect ?: Regulation.BLANK_REGULATOR_STRING
  )

  val notional: Amount by lazy {
    Amount(notionalString!!, notionalCurrency!!, notionalUsd)
  }

  @Transient
  val amount: Amount = Amount(amountString, amountCurrency, amountUsd)

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

}