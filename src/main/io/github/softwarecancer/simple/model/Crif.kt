package io.github.softwarecancer.simple.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Crif(@SerialName("tradeId") val id: String? = null,
                @SerialName("valuationDate") val valDate: String? = null, val endDate: String? = null,
                val notional: String? = null, val notionalCurrency: String? = null, val notionalUsd: String? = null,
                @SerialName("imModel") val model: String? = null,
                @SerialName("productClass") val productType: String, val riskType: String,
                val qualifier: String? = null, val bucket: String? = null, val labelOne: String? = null, val labelTwo: String? = null,
                @SerialName("postRegulation") val post: String? = null, @SerialName("collectRegulation") val collect: String? = null,
                val amount: String, val amountCurrency: String? = null, val amountUsd: String? = null) {

  fun regulation(): Regulation {
    return Regulation(
      post ?: Regulation.BLANK_REGULATOR_STRING,
      collect ?: Regulation.BLANK_REGULATOR_STRING
    )
  }

}