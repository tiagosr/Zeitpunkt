package esque.ma.zeitpunkt

import android.content.Context
import android.content.res.Configuration
import android.os.*
import android.view.SurfaceHolder
import androidx.wear.watchface.*
import androidx.wear.watchface.style.CurrentUserStyleRepository
import androidx.wear.watchface.style.UserStyleSchema
import esque.ma.zeitpunkt.utils.createComplicationSlotManager
import esque.ma.zeitpunkt.utils.createUserStyleSchema


data class FaceText(
    val line_0: String,
    val highlight_0: Boolean = true,
    val line_1: String = "",
    val highlight_1: Boolean = false,
    val line_2: String = "",
    val highlight_2: Boolean = false
) {
}

class ZeitpunktWatchFace : WatchFaceService() {

    override fun createUserStyleSchema(): UserStyleSchema =
        createUserStyleSchema(applicationContext)

    override fun createComplicationSlotsManager(
        currentUserStyleRepository: CurrentUserStyleRepository
    ): ComplicationSlotsManager = createComplicationSlotManager(
        context = applicationContext,
        currentUserStyleRepository = currentUserStyleRepository
    )

    override suspend fun createWatchFace(
        surfaceHolder: SurfaceHolder,
        watchState: WatchState,
        complicationSlotsManager: ComplicationSlotsManager,
        currentUserStyleRepository: CurrentUserStyleRepository
    ): WatchFace {
        val renderer = ZeitpunktWatchCanvasRenderer(
            context = applicationContext,
            surfaceHolder,
            watchState,
            complicationSlotsManager,
            currentUserStyleRepository,
            canvasType = CanvasType.HARDWARE
        )

        return WatchFace(
            watchFaceType = WatchFaceType.DIGITAL,
            renderer = renderer
        )
    }

}