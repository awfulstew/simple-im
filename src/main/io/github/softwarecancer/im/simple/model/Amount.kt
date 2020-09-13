package io.github.softwarecancer.im.simple.model

import java.math.BigDecimal
import kotlin.IllegalStateException

class Amount(private val amount: String, private val currency: String?, private val amountUsd: String?) {

  companion object {
    private val currencyRegex: Regex = """\w{3}""".toRegex()
  }

  fun getAmount(): BigDecimal {
    return when {
      // return the amount usd if it is available
      !amountUsd.isNullOrBlank() -> BigDecimal(amountUsd)
      // convert the amount to USD using fx implementation if currency is provided and matches currency code format
      !currency.isNullOrBlank() && currency.matches(currencyRegex) -> FxRate.singleton.convert(BigDecimal(amount), currency, "USD")
      // if the currency is not in the expected format then throw
      !currency.isNullOrBlank() && !currency.matches(currencyRegex) -> throw IllegalStateException("Currency did not match expected currency code format: [${currency}]")
      // all other cases try to return just the amount since there is no associated currency
      else -> BigDecimal(amount) // should throw if poorly formatted
    }
  }

  fun getAmount(model: ImModel, riskType: String, role: Regulation.Role): BigDecimal {
    return when {
      role == Regulation.Role.PLEDGOR && model == ImModel.SIMM && RiskType.SIMM_STANDARD.labels.contains(riskType) -> getAmount().negate()
      role == Regulation.Role.SECURED && model == ImModel.SCHEDULE && RiskType.SCHEDULE_PV.labels.contains(riskType) -> getAmount().negate()
      else -> getAmount() // return the normal amount without conversion
    }
  }
}
