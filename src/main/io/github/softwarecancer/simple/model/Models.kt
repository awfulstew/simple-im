package io.github.softwarecancer.simple.model

enum class ImModel(val labels: List<String>) {
  SIMM(listOf("SIMM", "SIMM-P")),
  SCHEDULE(listOf("SCHEDULE"));
}

enum class ProductType(val label: String) {
  SIMM_RATES_FX("RatesFx".toUpperCase()),
  SIMM_CREDIT("Credit".toUpperCase()),
  SIMM_EQUITY("Equity".toUpperCase()),
  SIMM_COMMODITY("Commodity".toUpperCase()),
  SIMM_SINGLE("Single".toUpperCase()),
  SCHEDULE_FX("FX".toUpperCase()),
  SCHEDULE_RATES("Rates".toUpperCase()),
  SCHEDULE_CREDIT("Credit".toUpperCase()),
  SCHEDULE_EQUITY("Equity".toUpperCase()),
  SCHEDULE_COMMODITY("Commodity".toUpperCase());
}

enum class RiskType(val labels: List<String>) {
  SIMM_INTEREST_RATES(listOf(
    RiskType.SIMM_IR_CURVE_LABEL,
    RiskType.SIMM_IR_VOL_LABEL,
    RiskType.SIMM_INFLATION_LABEL,
    RiskType.SIMM_INFLATION_VOL_LABEL,
    RiskType.SIMM_XCCY_BASIS_LABEL
  )),
  SIMM_CREDIT_QUALIFYING(listOf(
    RiskType.SIMM_CREDIT_Q_LABEL,
    RiskType.SIMM_CREDIT_Q_VOL_LABEL,
    RiskType.SIMM_BASE_CORR_LABEL
  )),
  SIMM_CREDIT_NON_QUALIFYING(listOf(
    RiskType.SIMM_CREDIT_NON_Q_LABEL,
    RiskType.SIMM_CREDIT_NON_Q_VOL_LABEL
  )),
  SIMM_EQUITY(listOf(
    RiskType.SIMM_EQUITY_LABEL,
    RiskType.SIMM_EQUITY_VOL_LABEL
  )),
  SIMM_COMMODITY(listOf(
    RiskType.SIMM_COMMODITY_LABEL,
    RiskType.SIMM_COMMODITY_VOL_LABEL
  )),
  SIMM_FX(listOf(
    RiskType.SIMM_FX_LABEL,
    RiskType.SIMM_FX_VOL_LABEL
  )),
  SIMM_STANDARD(listOf(
    RiskType.SIMM_IR_CURVE_LABEL,
    RiskType.SIMM_IR_VOL_LABEL,
    RiskType.SIMM_INFLATION_LABEL,
    RiskType.SIMM_INFLATION_VOL_LABEL,
    RiskType.SIMM_XCCY_BASIS_LABEL,
    RiskType.SIMM_CREDIT_Q_LABEL,
    RiskType.SIMM_CREDIT_Q_VOL_LABEL,
    RiskType.SIMM_BASE_CORR_LABEL,
    RiskType.SIMM_CREDIT_NON_Q_LABEL,
    RiskType.SIMM_CREDIT_NON_Q_VOL_LABEL,
    RiskType.SIMM_EQUITY_LABEL,
    RiskType.SIMM_EQUITY_VOL_LABEL,
    RiskType.SIMM_COMMODITY_LABEL,
    RiskType.SIMM_COMMODITY_VOL_LABEL,
    RiskType.SIMM_FX_LABEL,
    RiskType.SIMM_FX_VOL_LABEL
  )),
  SIMM_ADD_ON_NOTIONAL(listOf(RiskType.ADD_ON_NOTIONAL_LABEL)),
  SIMM_ADD_ON_FIXED_AMOUNT(listOf(RiskType.ADD_ON_FIXED_LABEL)),
  SIMM_ADD_ON_NOTIONAL_FACTOR(listOf(RiskType.ADD_ON_FACTOR_LABEL)),
  SIMM_ADD_ON_PRODUCT_MULTIPLIER(listOf(RiskType.ADD_ON_MULTIPLIER_LABEL)),
  SIMM_ADD_ON(listOf(
    RiskType.ADD_ON_NOTIONAL_LABEL,
    RiskType.ADD_ON_FACTOR_LABEL,
    RiskType.ADD_ON_FIXED_LABEL,
    RiskType.ADD_ON_MULTIPLIER_LABEL
  )),
  SCHEDULE_NOTIONAL(listOf(RiskType.SCHEDULE_NOTIONAL_LABEL)),
  SCHEDULE_PV(listOf(RiskType.SCHEDULE_PV_LABEL)),
  SCHEDULE(listOf(
    RiskType.SCHEDULE_NOTIONAL_LABEL,
    RiskType.SCHEDULE_PV_LABEL
  ));

  companion object {
    private val SIMM_IR_CURVE_LABEL: String = "Risk_IRCurve".toUpperCase()
    private val SIMM_IR_VOL_LABEL: String = "Risk_IRVol".toUpperCase()
    private val SIMM_INFLATION_LABEL: String = "Risk_Inflation".toUpperCase()
    private val SIMM_INFLATION_VOL_LABEL: String = "Risk_InflationVol".toUpperCase()
    private val SIMM_XCCY_BASIS_LABEL: String = "Risk_XCcyBasis".toUpperCase()
    private val SIMM_CREDIT_Q_LABEL: String = "Risk_CreditQ".toUpperCase()
    private val SIMM_CREDIT_Q_VOL_LABEL: String = "Risk_CreditVol".toUpperCase()
    private val SIMM_BASE_CORR_LABEL : String = "Risk_BaseCorr".toUpperCase()
    private val SIMM_CREDIT_NON_Q_LABEL : String = "Risk_CreditNonQ".toUpperCase()
    private val SIMM_CREDIT_NON_Q_VOL_LABEL : String = "Risk_CreditVolNonQ".toUpperCase()
    private val SIMM_EQUITY_LABEL : String = "Risk_Equity".toUpperCase()
    private val SIMM_EQUITY_VOL_LABEL : String = "Risk_EquityVol".toUpperCase()
    private val SIMM_COMMODITY_LABEL : String = "Risk_Commodity".toUpperCase()
    private val SIMM_COMMODITY_VOL_LABEL : String = "Risk_CommodityVol".toUpperCase()
    private val SIMM_FX_LABEL : String = "Risk_FX".toUpperCase()
    private val SIMM_FX_VOL_LABEL : String = "Risk_FXVol".toUpperCase()
    private val ADD_ON_FIXED_LABEL: String = "Param_AddOnFixedAmount".toUpperCase()
    private val ADD_ON_FACTOR_LABEL: String = "Param_AddOnNotionalFactor".toUpperCase()
    private val ADD_ON_NOTIONAL_LABEL: String = "Notional".toUpperCase()
    private val ADD_ON_MULTIPLIER_LABEL: String = "Param_ProductClassMultiplier".toUpperCase()
    private val SCHEDULE_NOTIONAL_LABEL: String = "Notional".toUpperCase()
    private val SCHEDULE_PV_LABEL: String = "PV".toUpperCase()
  }
}