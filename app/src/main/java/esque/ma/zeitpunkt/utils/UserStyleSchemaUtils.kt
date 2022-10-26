package esque.ma.zeitpunkt.utils

import android.content.Context
import androidx.wear.watchface.style.UserStyleSchema
import androidx.wear.watchface.style.UserStyleSetting
import androidx.wear.watchface.style.WatchFaceLayer
import esque.ma.zeitpunkt.R
import esque.ma.zeitpunkt.data.watchface.ColorStyleIdAndResourceIds

const val COLOR_STYLE_SETTING = "color_style_setting"
const val LANGUAGE_SETTING = "language_setting"


fun createUserStyleSchema(context: Context): UserStyleSchema {
    val colorStyleSetting =
        UserStyleSetting.ListUserStyleSetting(
            UserStyleSetting.Id(COLOR_STYLE_SETTING),
            context.resources,
            R.string.colors_style_setting,
            R.string.colors_style_setting_description,
            null,
            ColorStyleIdAndResourceIds.toOptionList(context),
            listOf(
                WatchFaceLayer.BASE,
                WatchFaceLayer.COMPLICATIONS,
                WatchFaceLayer.COMPLICATIONS_OVERLAY
            )
        )

    return UserStyleSchema(
        listOf(
            colorStyleSetting
        )
    )
}