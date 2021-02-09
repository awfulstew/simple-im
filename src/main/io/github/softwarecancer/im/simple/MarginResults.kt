package io.github.softwarecancer.im.simple

import java.lang.IllegalStateException
import java.math.BigDecimal

interface ImResult< out T : MarginIdentifier > {
  val index: Int
  val type: String
  val identifier: T
  val margin: BigDecimal
  val currency: String
  val children: List<ImResult<MarginIdentifier>>
  val regulator: String
  fun getLevel() = index.toString().plus(".").plus(type)
  fun getLabel() = identifier.identifier
}

class TotalMargin(
  override val children: List<ImModelMargin>,
  override val currency: String,
  override val regulator: String,
) : ImResult<TotalIdentifier> {
  override val index = 0
  override val type = "Total"
  override val identifier = TotalIdentifier()
  override val margin: BigDecimal =
    children.map { it.margin }.reduceOrNull(BigDecimal::add) ?: BigDecimal.ZERO
}

class ImModelMargin(
  override val identifier: ImModel,
  override val margin: BigDecimal,
  override val children: List<SiloMargin>,
  override val currency: String,
  override val regulator: String,
) : ImResult<ImModel> {
  override val index = 1
  override val type = "Model"
}

sealed class SiloMargin(
  override val identifier: ProductType,
  override val margin: BigDecimal,
  override val children: List<Nothing>,
  override val currency: String,
  override val regulator: String,
  override val type: String,
) : ImResult<ProductType> {
  override val index: Int = 2
}

class ScheduleProductMargin(
  override val identifier: ProductType,
  override val margin: BigDecimal,
  override val currency: String,
  override val regulator: String,
) : SiloMargin(identifier, margin, emptyList(), currency, regulator, "Silo")

class SimmProductMargin(
  override val identifier: ProductType,
  override val margin: BigDecimal,
  override val children: List<Nothing>,
  override val currency: String,
  override val regulator: String,
) : SiloMargin(identifier, margin, children, currency, regulator, "Silo")

class SimmAddOnMargin(
  override val identifier: ProductType,
  override val margin: BigDecimal,
  override val children: List<Nothing>,
  override val currency: String,
  override val regulator: String,
) : SiloMargin(identifier, margin, children, currency, regulator, "AddOn")

class ScheduleSensitivity(
  config: CalculationConfig,
  crif: List<Crif>,
  val group: Crif.Identifier,
  override var margin: BigDecimal,
  override val index: Int,
  override val type: String,
) : ImResult<NettedIdentifier> {
  override val currency = config.resultCurrency
  override val regulator = config.regulator
  override val children = emptyList<Nothing>()
  override val identifier = NettedIdentifier(group)
  val notional: ScheduleSensitivity by lazy {
    ScheduleSensitivity(
      config,
      crif = emptyList(),
      group,
      margin = crif.firstOrNull { !it.notionalString.isNullOrBlank() }?.getValue(config) ?: BigDecimal.ZERO,
      index,
      type,
    )
  }
}