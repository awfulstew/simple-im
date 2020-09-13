package io.github.softwarecancer.im.simple.model

import java.math.BigDecimal

interface ImTree<out MarginIdentifier, out ImTree> {
  val level: String
  val identifier: MarginIdentifier
  val margin: BigDecimal
  val children: List<ImTree>
}

interface MarginResult {
  val currency: String
  val regulator: String
}