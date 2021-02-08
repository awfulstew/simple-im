package io.github.softwarecancer.im.simple

import java.math.BigDecimal
import kotlin.IllegalStateException

class Amount(_amount: String, _currency: String = FxRate.USD, private val amountUsd: String?) {

  companion object {
    val currencyRegex: Regex = """\w{3}""".toRegex()
  }

  private val string: String = _amount
  private val amount: BigDecimal = BigDecimal(_amount) // should throw if amount is poorly formatted
  private val currency: String =
    if (_currency matches currencyRegex) _currency.toUpperCase()
    else throw IllegalStateException("Currency did not match expected currency code format: [${_currency}]")

  fun getAmount(config: CalculationConfig): BigDecimal {
    return when {
      // return amount if currency is equal to result currency
      currency == config.resultCurrency -> amount
      // return the amount usd if it is available
      !amountUsd.isNullOrBlank() -> config.fxRate.convert(BigDecimal(amountUsd), FxRate.USD, config.resultCurrency)
      // convert the amount to USD using fx implementation - we have already checked that currency matches format
      else -> config.fxRate.convert(amount, currency, config.resultCurrency)
    }
  }

  fun getAmountUsd(config: CalculationConfig): BigDecimal {
    return when {
      // return amount if currency is USD
      currency == FxRate.USD -> amount
      // return the amount usd if it is available
      !amountUsd.isNullOrBlank() -> BigDecimal(amountUsd)
      // convert the amount to USD using fx implementation - we have already checked that currency matches format
      else -> config.fxRate.convert(amount, currency, FxRate.USD)
    }
  }
}
