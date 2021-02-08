package io.github.softwarecancer.im.simple

import java.math.BigDecimal

fun <T : MarginIdentifier, U> sumMargin(children: List<ImResult<T, U>>): BigDecimal =
  children.map { it.margin }.reduceOrNull(BigDecimal::add) ?: BigDecimal.ZERO

