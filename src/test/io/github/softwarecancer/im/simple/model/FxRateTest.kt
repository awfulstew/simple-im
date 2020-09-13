package io.github.softwarecancer.im.simple.model

import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class FxRateTest {

  @Test
  fun `test setting the fx rate`() {
    class SimpleFxRate : FxRate {
      override fun convert(amount: BigDecimal, from: String, to: String): BigDecimal {
        return if (from.equals("EUR", ignoreCase = true)) {
          amount.multiply(BigDecimal.valueOf(1.5))
        } else {
          amount
        }
      }
    }

    FxRate.singleton = SimpleFxRate()

    val eur = Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00", amountCurrency = "EUR")
    assertEquals(BigDecimal.valueOf(150), eur.value.getAmount().setScale(0))
    val other = Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00", amountCurrency = "USD")
    assertEquals(BigDecimal.valueOf(100), other.value.getAmount().setScale(0))
  }

  @Test
  fun `test unset fx rate throws`() {
    assertFailsWith(UninitializedPropertyAccessException::class) {
      Crif(
        riskType = RiskType.SIMM_FX_LABEL,
        productType = "RatesFx",
        amount = "100.00",
        amountCurrency = "EUR"
      ).value.getAmount()
    }
  }
}