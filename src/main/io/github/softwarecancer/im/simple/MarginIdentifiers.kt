package io.github.softwarecancer.im.simple

interface MarginIdentifier {
  val identifier: String
}

class TotalIdentifier : MarginIdentifier {
  override val identifier: String = "Total"
}

enum class ImModel(override val identifier: String, val labels: List<String>): MarginIdentifier {
  SIMM("Simm", listOf("SIMM", "SIMM-P")),
  SCHEDULE("Schedule", listOf("SCHEDULE"));
}

enum class ProductType(override val identifier: String, val label: String): MarginIdentifier {
  SIMM_RATES_FX("RatesFx", "RatesFx".toUpperCase()),
  SIMM_CREDIT("Credit", "Credit".toUpperCase()),
  SIMM_EQUITY("Equity", "Equity".toUpperCase()),
  SIMM_COMMODITY("Commodity", "Commodity".toUpperCase()),
  SIMM_SINGLE("Single", "Single".toUpperCase()),
  SCHEDULE_FX("FX", "FX".toUpperCase()),
  SCHEDULE_RATES("Rates", "Rates".toUpperCase()),
  SCHEDULE_CREDIT("Credit", "Credit".toUpperCase()),
  SCHEDULE_EQUITY("Equity", "Equity".toUpperCase()),
  SCHEDULE_COMMODITY("Commodity", "Commodity".toUpperCase());
}

enum class RiskType(override val identifier: String, val labels: List<String>): MarginIdentifier {
  SIMM_INTEREST_RATES("IR", listOf(
    RiskType.SIMM_IR_CURVE_LABEL.toUpperCase(),
    RiskType.SIMM_IR_VOL_LABEL.toUpperCase(),
    RiskType.SIMM_INFLATION_LABEL.toUpperCase(),
    RiskType.SIMM_INFLATION_VOL_LABEL.toUpperCase(),
    RiskType.SIMM_XCCY_BASIS_LABEL.toUpperCase()
  )),
  SIMM_CREDIT_QUALIFYING("CreditQ", listOf(
    RiskType.SIMM_CREDIT_Q_LABEL.toUpperCase(),
    RiskType.SIMM_CREDIT_Q_VOL_LABEL.toUpperCase(),
    RiskType.SIMM_BASE_CORR_LABEL.toUpperCase()
  )),
  SIMM_CREDIT_NON_QUALIFYING("CreditNonQ", listOf(
    RiskType.SIMM_CREDIT_NON_Q_LABEL.toUpperCase(),
    RiskType.SIMM_CREDIT_NON_Q_VOL_LABEL.toUpperCase()
  )),
  SIMM_EQUITY("Equity", listOf(
    RiskType.SIMM_EQUITY_LABEL.toUpperCase(),
    RiskType.SIMM_EQUITY_VOL_LABEL.toUpperCase()
  )),
  SIMM_COMMODITY("Commodity", listOf(
    RiskType.SIMM_COMMODITY_LABEL.toUpperCase(),
    RiskType.SIMM_COMMODITY_VOL_LABEL.toUpperCase()
  )),
  SIMM_FX("FX", listOf(
    RiskType.SIMM_FX_LABEL.toUpperCase(),
    RiskType.SIMM_FX_VOL_LABEL.toUpperCase()
  )),
  SIMM_STANDARD("Standard", listOf(
    RiskType.SIMM_IR_CURVE_LABEL.toUpperCase(),
    RiskType.SIMM_IR_VOL_LABEL.toUpperCase(),
    RiskType.SIMM_INFLATION_LABEL.toUpperCase(),
    RiskType.SIMM_INFLATION_VOL_LABEL.toUpperCase(),
    RiskType.SIMM_XCCY_BASIS_LABEL.toUpperCase(),
    RiskType.SIMM_CREDIT_Q_LABEL.toUpperCase(),
    RiskType.SIMM_CREDIT_Q_VOL_LABEL.toUpperCase(),
    RiskType.SIMM_BASE_CORR_LABEL.toUpperCase(),
    RiskType.SIMM_CREDIT_NON_Q_LABEL.toUpperCase(),
    RiskType.SIMM_CREDIT_NON_Q_VOL_LABEL.toUpperCase(),
    RiskType.SIMM_EQUITY_LABEL.toUpperCase(),
    RiskType.SIMM_EQUITY_VOL_LABEL.toUpperCase(),
    RiskType.SIMM_COMMODITY_LABEL.toUpperCase(),
    RiskType.SIMM_COMMODITY_VOL_LABEL.toUpperCase(),
    RiskType.SIMM_FX_LABEL.toUpperCase(),
    RiskType.SIMM_FX_VOL_LABEL.toUpperCase()
  )),
  SIMM_ADD_ON_NOTIONAL("Notional", listOf(RiskType.ADD_ON_NOTIONAL_LABEL.toUpperCase())),
  SIMM_ADD_ON_FIXED_AMOUNT("Fixed", listOf(RiskType.ADD_ON_FIXED_LABEL.toUpperCase())),
  SIMM_ADD_ON_NOTIONAL_FACTOR("Factor", listOf(RiskType.ADD_ON_FACTOR_LABEL.toUpperCase())),
  SIMM_ADD_ON_PRODUCT_MULTIPLIER("Multiplier", listOf(RiskType.ADD_ON_MULTIPLIER_LABEL.toUpperCase())),
  SIMM_ADD_ON("AddOn", listOf(
    RiskType.ADD_ON_NOTIONAL_LABEL.toUpperCase(),
    RiskType.ADD_ON_FACTOR_LABEL.toUpperCase(),
    RiskType.ADD_ON_FIXED_LABEL.toUpperCase(),
    RiskType.ADD_ON_MULTIPLIER_LABEL.toUpperCase()
  )),
  SCHEDULE_NOTIONAL("Notional", listOf(RiskType.SCHEDULE_NOTIONAL_LABEL.toUpperCase())),
  SCHEDULE_PV("PV", listOf(RiskType.SCHEDULE_PV_LABEL.toUpperCase())),
  SCHEDULE("Schedule", listOf(
    RiskType.SCHEDULE_NOTIONAL_LABEL.toUpperCase(),
    RiskType.SCHEDULE_PV_LABEL.toUpperCase()
  ));

  companion object {
    const val SIMM_IR_CURVE_LABEL: String = "RISK_IRCurve"
    const val SIMM_IR_VOL_LABEL: String = "Risk_IRVol"
    const val SIMM_INFLATION_LABEL: String = "Risk_Inflation"
    const val SIMM_INFLATION_VOL_LABEL: String = "Risk_InflationVol"
    const val SIMM_XCCY_BASIS_LABEL: String = "Risk_XCcyBasis"
    const val SIMM_CREDIT_Q_LABEL: String = "Risk_CreditQ"
    const val SIMM_CREDIT_Q_VOL_LABEL: String = "Risk_CreditVol"
    const val SIMM_CREDIT_NON_Q_LABEL : String = "Risk_CreditNonQ"
    const val SIMM_CREDIT_NON_Q_VOL_LABEL : String = "Risk_CreditVolNonQ"
    const val SIMM_EQUITY_LABEL : String = "Risk_Equity"
    const val SIMM_EQUITY_VOL_LABEL : String = "Risk_EquityVol"
    const val SIMM_COMMODITY_LABEL : String = "Risk_Commodity"
    const val SIMM_COMMODITY_VOL_LABEL : String = "Risk_CommodityVol"
    const val SIMM_FX_LABEL : String = "Risk_FX"
    const val SIMM_FX_VOL_LABEL : String = "Risk_FXVol"
    const val ADD_ON_FIXED_LABEL: String = "Param_AddOnFixedAmount"
    const val ADD_ON_FACTOR_LABEL: String = "Param_AddOnNotionalFactor"
    const val ADD_ON_NOTIONAL_LABEL: String = "Notional"
    const val ADD_ON_MULTIPLIER_LABEL: String = "Param_ProductClassMultiplier"
    const val SCHEDULE_NOTIONAL_LABEL: String = "Notional"
    const val SCHEDULE_PV_LABEL: String = "PV"
    const val SIMM_BASE_CORR_LABEL : String = "Risk_BaseCorr"
  }
}

enum class SensitivityType(override val identifier: String): MarginIdentifier {
  DELTA("Delta"),
  VEGA("Vega"),
  CURVATURE("Curvature"),
  BASE_CORRELATION("BaseCorrelation");

  companion object {
    const val VEGA_SUFFIX = "Vol"
  }
}

enum class BucketType(val risk: RiskType, val bucket: String?, val description: String) {
  FX(RiskType.SIMM_FX, "1", "The single FX bucket"),
  IR_REG(RiskType.SIMM_INTEREST_RATES, "1", "Regular Volatility Currency"),
  IR_LOW(RiskType.SIMM_INTEREST_RATES, "2", "Low Volatility Currency"),
  IR_HIGH(RiskType.SIMM_INTEREST_RATES, "3", "High Volatility Currency"),
  CM1(RiskType.SIMM_COMMODITY, "1", "Coal"),
  CM2(RiskType.SIMM_COMMODITY, "2", "Crude"),
  CM3(RiskType.SIMM_COMMODITY, "3", "Light Ends"),
  CM4(RiskType.SIMM_COMMODITY, "4", "Middle Distillates"),
  CM5(RiskType.SIMM_COMMODITY, "5", "Heavy Distillates"),
  CM6(RiskType.SIMM_COMMODITY, "6", "North America Natural Gas"),
  CM7(RiskType.SIMM_COMMODITY, "7", "European Natural Gas"),
  CM8(RiskType.SIMM_COMMODITY, "8", "North American Power"),
  CM9(RiskType.SIMM_COMMODITY, "9", "European Power"),
  CM10(RiskType.SIMM_COMMODITY, "10", "Freight"),
  CM11(RiskType.SIMM_COMMODITY, "11", "Base Metals"),
  CM12(RiskType.SIMM_COMMODITY, "12", "Precious Metals"),
  CM13(RiskType.SIMM_COMMODITY, "13", "Grains"),
  CM14(RiskType.SIMM_COMMODITY, "14", "Softs"),
  CM15(RiskType.SIMM_COMMODITY, "15", "Livestock"),
  CM16(RiskType.SIMM_COMMODITY, "16", "Other"),
  CM17(RiskType.SIMM_COMMODITY, "17", "Indexes"),
  CRNQ1(RiskType.SIMM_CREDIT_NON_QUALIFYING, "1", "Investment grade (IG) in RMBS/CMBS"),
  CRNQ2(RiskType.SIMM_CREDIT_NON_QUALIFYING, "2", "High yield (HY) & non-rated (NR) in RMBS/CMBS"),
  CRNQ_RESIDUAL(RiskType.SIMM_CREDIT_NON_QUALIFYING, null, "CRNQ Residual Bucket"),
  CRQ1(RiskType.SIMM_CREDIT_QUALIFYING, "1", "Investment grade (IG) in Sovereigns including central banks"),
  CRQ2(RiskType.SIMM_CREDIT_QUALIFYING, "2", "Investment grade (IG) in Financials including government-backed financials"),
  CRQ3(RiskType.SIMM_CREDIT_QUALIFYING, "3", "Investment grade (IG) in Basic materials, energy, industrials"),
  CRQ4(RiskType.SIMM_CREDIT_QUALIFYING, "4", "Investment grade (IG) in Consumer"),
  CRQ5(RiskType.SIMM_CREDIT_QUALIFYING, "5", "Investment grade (IG) in Technology, telecommunications"),
  CRQ6(RiskType.SIMM_CREDIT_QUALIFYING, "6", "Investment grade (IG) in Health care, utilities, local government, government-backed corporates (non- financial)"),
  CRQ7(RiskType.SIMM_CREDIT_QUALIFYING, "7", "High yield (HY) & non-rated (NR) in Sovereigns including central banks"),
  CRQ8(RiskType.SIMM_CREDIT_QUALIFYING, "8", "High yield (HY) & non-rated (NR) in Financials including government-backed financials"),
  CRQ9(RiskType.SIMM_CREDIT_QUALIFYING, "9", "High yield (HY) & non-rated (NR) in Basic materials, energy, industrials"),
  CRQ10(RiskType.SIMM_CREDIT_QUALIFYING, "10", "High yield (HY) & non-rated (NR) in Consumer"),
  CRQ11(RiskType.SIMM_CREDIT_QUALIFYING, "11", "High yield (HY) & non-rated (NR) in Technology, telecommunications"),
  CRQ12(RiskType.SIMM_CREDIT_QUALIFYING, "12", "High yield (HY) & non-rated (NR) in Health care, utilities, local government, government-backed corporates (non- financial)"),
  CRQ_RESIDUAL(RiskType.SIMM_CREDIT_QUALIFYING, null, "CRQ Residual Bucket"),
  EQ1(RiskType.SIMM_EQUITY, "1", "${EquitySize.LARGE} ${EquityRegion.EMERGING} Consumer goods and services, transportation and storage, administrative and support service activities, utilities"),
  EQ2(RiskType.SIMM_EQUITY, "2", "${EquitySize.LARGE} ${EquityRegion.EMERGING} Telecommunications, industrials"),
  EQ3(RiskType.SIMM_EQUITY, "3", "${EquitySize.LARGE} ${EquityRegion.EMERGING} Basic materials, energy, agriculture, manufacturing, mining and quarrying "),
  EQ4(RiskType.SIMM_EQUITY, "4", "${EquitySize.LARGE} ${EquityRegion.EMERGING} Financials including gov't-backed financials, real estate activities, technology"),
  EQ5(RiskType.SIMM_EQUITY, "5", "${EquitySize.LARGE} ${EquityRegion.DEVELOPED} Consumer goods and services, transportation and storage, administrative and support service activities, utilities "),
  EQ6(RiskType.SIMM_EQUITY, "6", "${EquitySize.LARGE} ${EquityRegion.DEVELOPED} Telecommunications, industrials"),
  EQ7(RiskType.SIMM_EQUITY, "7", "${EquitySize.LARGE} ${EquityRegion.DEVELOPED} Basic materials, energy, agriculture, manufacturing, mining and quarrying "),
  EQ8(RiskType.SIMM_EQUITY, "8", "${EquitySize.LARGE} ${EquityRegion.DEVELOPED} Financials including gov't-backed financials, real estate activities, technology "),
  EQ9(RiskType.SIMM_EQUITY, "9", "${EquitySize.SMALL} ${EquityRegion.EMERGING} All sectors"),
  EQ10(RiskType.SIMM_EQUITY, "10", "${EquitySize.SMALL} ${EquityRegion.DEVELOPED} All sectors"),
  EQ11(RiskType.SIMM_EQUITY, "11", "${EquitySize.ALL} ${EquityRegion.ALL} Indexes, Funds, ETFs"),
  EQ12(RiskType.SIMM_EQUITY, "12", "${EquitySize.ALL} ${EquityRegion.ALL} Volatility Indexes"),
  EQ_RESIDUAL(RiskType.SIMM_EQUITY, null, "EQ Residual Bucket");
  
  enum class InterestRateVolatility(val bucket: String, val currencies: List<String>) {
    REGULAR("1", listOf("USD", "EUR", "GBP", "CHF", "AUD", "NZD", "CAD", "SEK", "NOK", "DKK", "HKD", "KRW", "SGD", "TWD")),
    LOW_VOLATILITY("2", listOf("JPY")),
    HIGH_VOLATILITY("3", emptyList());
  }

  enum class EquityRegion {
    EMERGING,
    DEVELOPED,
    ALL;
  }

  enum class EquitySize {
    LARGE,
    SMALL,
    ALL;
  }
}