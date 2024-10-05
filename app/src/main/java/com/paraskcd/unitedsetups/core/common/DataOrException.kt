package com.paraskcd.unitedsetups.core.common

data class DataOrException<T, E: Exception>(
    var data: T? = null,
    var ex: E? = null
)
