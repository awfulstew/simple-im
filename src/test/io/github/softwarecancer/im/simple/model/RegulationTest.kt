package io.github.softwarecancer.im.simple.model

import kotlin.test.Test
import kotlin.test.assertEquals

class RegulationTest {

  @Test
  fun `test crif regulation from role method`() {
    val reg = Regulation("[\"CPS\",\"   HELLO\", \"OTHER\" ]", "[]")
    assertEquals(setOf("CPS", "HELLO", "OTHER"), reg.byRole(Regulation.Role.PLEDGOR))
    assertEquals(setOf(""), reg.byRole(Regulation.Role.SECURED))
  }

  @Test
  fun `test all blank regulation set with null`() {
    val crif = listOf(
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00"),
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00"),
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00"),
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00"),
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00"),
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00"),
    )

    assertEquals(setOf(Regulation.BLANK_REGULATOR_STRING), findRegulationSet(crif, Regulation.Role.SECURED))
    assertEquals(setOf(Regulation.BLANK_REGULATOR_STRING), findRegulationSet(crif, Regulation.Role.PLEDGOR))
  }

  @Test
  fun `test all blank with various blank formatted strings`() {
    val crif = listOf(
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00", collect = "[]"),
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00", collect = "\"   \""),
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00", post = "[       ]"),
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00", collect = "     "),
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00", collect = "[\"\", \"\"]"),
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00", post = "   \"\"    "),
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00", post = ""),
      )

    assertEquals(setOf(Regulation.BLANK_REGULATOR_STRING), findRegulationSet(crif, Regulation.Role.SECURED))
    assertEquals(setOf(Regulation.BLANK_REGULATOR_STRING), findRegulationSet(crif, Regulation.Role.PLEDGOR))
  }

  @Test
  fun `test included regulation set`() {
    val crif = listOf(
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00", post = "included"),
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00", collect = "included"),
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00", post = "included"),
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00", collect = "included"),
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00", collect = "included"),
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00", post = "included"),
    )

    assertEquals(setOf(Regulation.INCLUDED), findRegulationSet(crif, Regulation.Role.SECURED))
    assertEquals(setOf(Regulation.INCLUDED), findRegulationSet(crif, Regulation.Role.PLEDGOR))
  }

  @Test
  fun `test included regulation set when one side all blank`() {
    val crif = listOf(
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00", post = "included", collect = "[]"),
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00", collect = ""),
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00", post = "included"),
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00", collect = "     "),
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00", collect = "[\"\", \"\"]"),
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00", post = "included"),
    )

    assertEquals(setOf(Regulation.INCLUDED), findRegulationSet(crif, Regulation.Role.SECURED))
    assertEquals(setOf(Regulation.INCLUDED), findRegulationSet(crif, Regulation.Role.PLEDGOR))
  }

  @Test
  fun `test standard regulation check`() {
    val crif = listOf(
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00", post = "[\"ABC\", \"XYZ\"]", collect = "[]"),
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00", collect = "ABC"),
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00", post = "ABC"),
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00", collect = "[XYZ]"),
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00", collect = "[\"\", \"\"]"),
      Crif(riskType = RiskType.SIMM_FX_LABEL, productType = "RatesFx", amount = "100.00", post = "ABC"),
    )

    assertEquals(setOf("ABC", "XYZ"), findRegulationSet(crif, Regulation.Role.SECURED))
    assertEquals(setOf("ABC", "XYZ"), findRegulationSet(crif, Regulation.Role.PLEDGOR))
  }

}