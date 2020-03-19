package com.lawrence.weatherappdvt.core

import com.lawrence.weatherappdvt.utils.domain.Status

/**
 * Created by Lawrence on 2020/03/16
 */

open class BaseViewState(val baseStatus: Status, val baseError: String?) {
    fun isLoading() = baseStatus == Status.LOADING
    fun getErrorMessage() = baseError
    fun shouldShowErrorMessage() = baseError != null
}
