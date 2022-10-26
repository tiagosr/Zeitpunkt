package esque.ma.zeitpunkt.utils

import android.content.Context
import android.graphics.RectF
import androidx.wear.watchface.CanvasComplicationFactory
import androidx.wear.watchface.ComplicationSlot
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.complications.ComplicationSlotBounds
import androidx.wear.watchface.complications.DefaultComplicationDataSourcePolicy
import androidx.wear.watchface.complications.SystemDataSources
import androidx.wear.watchface.complications.data.ComplicationType
import androidx.wear.watchface.complications.rendering.CanvasComplicationDrawable
import androidx.wear.watchface.complications.rendering.ComplicationDrawable
import androidx.wear.watchface.style.CurrentUserStyleRepository
import esque.ma.zeitpunkt.R

private const val DEFAULT_COMPLICATION_STYLE_DRAWABLE_ID: Int = R.drawable.complication_red_style

internal const val TOP_COMPLICATION_ID: Int = 100

sealed class ComplicationConfig(val id:Int, val supportedTypes: List<ComplicationType>, val rectSlot:RectF) {

    object Top: ComplicationConfig(
        TOP_COMPLICATION_ID,
        listOf(
            ComplicationType.RANGED_VALUE,
            ComplicationType.MONOCHROMATIC_IMAGE,
            ComplicationType.SHORT_TEXT,
            ComplicationType.SMALL_IMAGE
        ),
        RectF(0.4f, 0.1f, 0.6f, 0.2f)
    )
}

fun createComplicationSlotManager(
    context: Context,
    currentUserStyleRepository: CurrentUserStyleRepository,
    drawableId:Int = DEFAULT_COMPLICATION_STYLE_DRAWABLE_ID
): ComplicationSlotsManager {
    val defaultCanvasComplicationFactory = CanvasComplicationFactory {
        watchState, listener ->
        CanvasComplicationDrawable(
            ComplicationDrawable.getDrawable(context, drawableId)!!,
            watchState,
            listener
        )
    }

    val topComplication = ComplicationSlot.createRoundRectComplicationSlotBuilder(
        id = ComplicationConfig.Top.id,
        canvasComplicationFactory = defaultCanvasComplicationFactory,
        supportedTypes = ComplicationConfig.Top.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(
            SystemDataSources.DATA_SOURCE_DAY_AND_DATE,
            ComplicationType.SHORT_TEXT
        ),
        bounds = ComplicationSlotBounds(
            ComplicationConfig.Top.rectSlot
        )
    )
        .build()
    return ComplicationSlotsManager(
        listOf(topComplication),
        currentUserStyleRepository
    )
}