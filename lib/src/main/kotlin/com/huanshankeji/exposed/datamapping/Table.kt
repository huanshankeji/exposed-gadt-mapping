package com.huanshankeji.exposed.datamapping

import org.jetbrains.exposed.v1.core.ColumnSet
import org.jetbrains.exposed.v1.core.Op
import org.jetbrains.exposed.v1.jdbc.select

fun <Data : Any> ColumnSet.selectWithMapper(mapper: NullableDataQueryMapper<Data>, where: Op<Boolean>? = null) =
    select(mapper.neededColumns)
        .run { where?.let { where(it) } ?: this }
        .asSequence().map { mapper.resultRowToData(it) }
