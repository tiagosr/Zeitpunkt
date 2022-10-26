package esque.ma.zeitpunkt.data.watchface

import android.content.Context
import android.graphics.drawable.Icon
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.wear.watchface.style.UserStyleSetting.*
import esque.ma.zeitpunkt.R

private const val RED_COLOR_STYLE_ID = "red_style_id"

enum class ColorStyleIdAndResourceIds (
    val id: String,
    @StringRes val nameResourceId: Int,
    @DrawableRes val iconResourceId: Int,
    @DrawableRes val complicationStyleDrawableId: Int,
    @ColorRes val primaryColorId: Int,
    @ColorRes val secondaryColorId: Int,
    @ColorRes val backgroundColorId: Int,
    @ColorRes val outerElementColorId: Int
) {

    RED(
        id = RED_COLOR_STYLE_ID,
        nameResourceId = R.string.red_style_name,
        iconResourceId = R.drawable.red_style,
        complicationStyleDrawableId = R.drawable.complication_red_style,
        primaryColorId = R.color.red_primary_color,
        secondaryColorId = R.color.red_secondary_color,
        backgroundColorId = R.color.red_background_color,
        outerElementColorId = R.color.red_outer_element_color
    );

    companion object {
      fun getColorStyleConfig(id: String): ColorStyleIdAndResourceIds {
          return when (id) {
              RED.id -> RED
              else -> RED
          }
      }

      fun toOptionList(context: Context): List<ListUserStyleSetting.ListOption> {
          val colorStyleIdAndResourceIdsList = enumValues<ColorStyleIdAndResourceIds>()

          return colorStyleIdAndResourceIdsList.map {
              item ->
              ListUserStyleSetting.ListOption(
                  Option.Id(item.id),
                  context.resources,
                  item.nameResourceId,
                  Icon.createWithResource(context, item.iconResourceId)
              )
          }
      }
    }
}