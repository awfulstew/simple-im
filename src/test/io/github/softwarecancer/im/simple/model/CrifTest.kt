package io.github.softwarecancer.im.simple.model

import kotlin.test.Test
import kotlin.test.assertEquals

class CrifTest {

  @Test
  fun `check sensitivity type is correctly initialized`() {
    val delta = Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00")
    assertEquals(SensitivityType.DELTA, delta.sensitivity)
    val vega = Crif(riskType = RiskType.SIMM_FX_VOL_LABEL, productType = "RatesFx", amount = "10.50")
    assertEquals(SensitivityType.VEGA, vega.sensitivity)
    val base = Crif(riskType = RiskType.SIMM_BASE_CORR_LABEL, productType = "Credit", amount = "120.50")
    assertEquals(SensitivityType.BASE_CORRELATION, base.sensitivity)
  }

  @Test
  fun `set sensitivity from a copy`() {
    val delta = Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00")
    assertEquals(SensitivityType.DELTA, delta.sensitivity)
    val curvature = delta.copy(_sensitivity = SensitivityType.CURVATURE)
    assertEquals(SensitivityType.CURVATURE, curvature.sensitivity)
    assertEquals(SensitivityType.DELTA, delta.sensitivity)
  }

  private enum class ListCarry(val carried: List<String>) {
    ONE(listOf("one".toUpperCase())),
    TWO(listOf(ListCarry.TWO_LABEL));

    companion object {
      const val TWO_LABEL: String = "two"
    }
  }

  @Test
  fun `building enum types that carry lists`() {
    assert("ONE" in ListCarry.ONE.carried)
    assert("two" in ListCarry.TWO.carried)
  }

}