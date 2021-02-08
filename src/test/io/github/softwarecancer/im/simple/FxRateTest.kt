package io.github.softwarecancer.im.simple

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

    val config = CalculationConfig.Builder().fxRate(SimpleFxRate()).resultCurrency(FxRate.USD).build()

    val eur = Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00", amountCurrency = "EUR")
    assertEquals(BigDecimal.valueOf(150), eur.value.getAmount(config).setScale(0))
    val other = Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00", amountCurrency = "USD")
    assertEquals(BigDecimal.valueOf(100), other.value.getAmount(config).setScale(0))
  }
}