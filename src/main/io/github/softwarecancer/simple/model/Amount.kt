package io.github.softwarecancer.simple.model

import java.math.BigDecimal
import kotlin.IllegalStateException

class Amount(private val amount: String, private val currency: String?, private val amountUsd: String?) {

  companion object {
    lateinit var fx: FxRateConverter
    private val currencyRegex: Regex = """\w{3}""".toRegex()
  }

  fun getAmount(): BigDecimal {
    return when {
      // return the amount usd if it is available
      !amountUsd.isNullOrBlank() -> BigDecimal(amountUsd)
      // convert the amount to USD using fx implementation if currency is provided and matches currency code format
      !currency.isNullOrBlank() && currency.matches(currencyRegex) -> fx.convert(BigDecimal(amount), currency, "USD")
      // if the currency is not in the expected format then throw
      !currency.isNullOrBlank() && !currency.matches(currencyRegex) -> throw IllegalStateException("Currency did not match expected regex: [${currency}]")
      // all other cases try to return just the amount since there is no associated currency
      else -> BigDecimal(amount) // should throw if poorly formatted
    }
  }
}
