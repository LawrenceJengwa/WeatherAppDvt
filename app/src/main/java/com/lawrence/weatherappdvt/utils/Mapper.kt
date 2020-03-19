package com.lawrence.weatherappdvt.utils


interface Mapper<R, D> {
    fun mapFrom(type: R): D
}
