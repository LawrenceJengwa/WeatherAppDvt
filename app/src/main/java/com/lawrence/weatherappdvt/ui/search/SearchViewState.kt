package com.lawrence.weatherappdvt.ui.search

import com.lawrence.weatherappdvt.core.BaseViewState
import com.lawrence.weatherappdvt.db.entity.CitiesForSearchEntity
import com.lawrence.weatherappdvt.utils.domain.Status



class SearchViewState(
    val status: Status,
    val error: String? = null,
    val data: List<CitiesForSearchEntity>? = null
) : BaseViewState(status, error) {
    fun getSearchResult() = data
}
