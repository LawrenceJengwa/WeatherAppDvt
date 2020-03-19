package com.lawrence.weatherappdvt.core

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.lawrence.weatherappdvt.R
import com.lawrence.weatherappdvt.utils.extensions.hide
import com.lawrence.weatherappdvt.utils.extensions.show
import com.squareup.picasso.Picasso


@BindingAdapter("app:visibility")
fun setVisibilty(view: View, isVisible: Boolean) {
    if (isVisible) {
        view.show()
    } else {
        view.hide()
    }
}

@BindingAdapter("app:setWeatherIcon")
fun setWeatherIcon(view: ImageView, iconPath: String?) {
    if (iconPath.isNullOrEmpty())
        return
    Picasso.get().cancelRequest(view)
    val newPath = iconPath.replace(iconPath, "a$iconPath")
    val imageid = view.context.resources.getIdentifier(newPath + "_svg", "drawable", view.context.packageName)
    val imageDrawable = view.context.resources.getDrawable(imageid, null)
    view.setImageDrawable(imageDrawable)
}

@BindingAdapter("app:setErrorView")
fun setErrorView(view: View, viewState: BaseViewState?) {
    if (viewState?.shouldShowErrorMessage() == true)
        view.show()
    else
        view.hide()

    view.setOnClickListener { view.hide() }
}

@BindingAdapter("app:setErrorText")
fun setErrorText(view: TextView, viewState: BaseViewState?) {
    if (viewState?.shouldShowErrorMessage() == true)
        view.text = viewState.getErrorMessage()
    else
        view.text = view.context.getString(R.string.unexpected_exception)
}
