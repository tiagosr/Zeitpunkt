package esque.ma.zeitpunkt

import android.content.Context
import android.graphics.*
import android.view.SurfaceHolder
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.Renderer
import androidx.wear.watchface.WatchState
import androidx.wear.watchface.style.CurrentUserStyleRepository
import esque.ma.zeitpunkt.data.Languages
import java.time.ZonedDateTime

private const val FRAME_PERIOD_MS_DEFAULT: Long = 16L

class ZeitpunktWatchCanvasRenderer(
    private val context: Context,
    surfaceHolder: SurfaceHolder,
    watchState: WatchState,
    private val complicationSlotsManager: ComplicationSlotsManager,
    currentUserStyleRepository: CurrentUserStyleRepository,
    canvasType: Int
) : Renderer.CanvasRenderer2<ZeitpunktWatchCanvasRenderer.ZeitpunktSharedAssets>(
    surfaceHolder,
    currentUserStyleRepository,
    watchState,
    canvasType,
    FRAME_PERIOD_MS_DEFAULT,
    clearWithBackgroundTintBeforeRenderingHighlightLayer = false
) {
    class Configs {
        lateinit var normalPaint: Paint
        lateinit var highlightPaint: Paint
        lateinit var mTypeface: Typeface
        val backgroundColor: Int = Color.BLACK
        val language:Languages = Languages.GERMAN

        fun init() {
            mTypeface = Typeface.DEFAULT_BOLD
            highlightPaint = Paint().apply {
                color = Color.WHITE
                isAntiAlias = true
                strokeCap = Paint.Cap.ROUND
                textSize = 60f
                typeface = mTypeface
            }

            normalPaint = Paint().apply {
                color = Color.RED
                isAntiAlias = true
                strokeCap = Paint.Cap.ROUND
                textSize = 60f
                typeface = mTypeface
            }
        }
    }

    private var configs = Configs()

    class ZeitpunktSharedAssets: SharedAssets {
        override fun onDestroy() {
        }
    }

    override suspend fun createSharedAssets(): ZeitpunktSharedAssets {
        return ZeitpunktSharedAssets()
    }

    override suspend fun init() {
        super.init()

        configs.init()
    }

    private fun drawComplications(canvas: Canvas, zonedDateTime: ZonedDateTime) {
        for ((_, complication) in complicationSlotsManager.complicationSlots) {
            if (complication.enabled) {
                complication.render(canvas, zonedDateTime, renderParameters)
            }
        }
    }

    override fun render(
        canvas: Canvas,
        bounds: Rect,
        zonedDateTime: ZonedDateTime,
        sharedAssets: ZeitpunktSharedAssets
    ) {
        renderWatchFace(configs, canvas, bounds, zonedDateTime) {
                canvas, zonedDateTime -> drawComplications(canvas, zonedDateTime)
        }

    }

    override fun renderHighlightLayer(
        canvas: Canvas,
        bounds: Rect,
        zonedDateTime: ZonedDateTime,
        sharedAssets: ZeitpunktSharedAssets
    ) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val TAG = "ZeitpunktWatchCanvasRenderer"

        private fun nameForMinuteUnitsPT(minute: Int): String {
            return when (minute) {
                0 -> ""
                1 -> "Um"
                2 -> "Dois"
                3 -> "Tr??s"
                4 -> "Quatro"
                5 -> "Cinco"
                6 -> "Seis"
                7 -> "Sete"
                8 -> "Oito"
                9 -> "Nove"
                10 -> "Dez"
                11 -> "Onze"
                12 -> "Doze"
                13 -> "Treze"
                14 -> "Quatorze"
                15 -> "Quinze"
                16 -> "Dezesseis"
                17 -> "Dezessete"
                18 -> "Dezoito"
                19 -> "Dezenove"
                else -> "invalid"
            }
        }

        private fun nameForHourWithMinutePT(hour: Int): String {
            return when (hour) {
                0, 24 -> "Meia Noite"
                12 -> "Meio Dia"
                1, 13 -> "Uma"
                2, 14 -> "Duas"
                3, 15 -> "Tr??s"
                4, 16 -> "Quatro"
                5, 17 -> "Cinco"
                6, 18 -> "Seis"
                7, 19 -> "Sete"
                8, 20 -> "Oito"
                9, 21 -> "Nove"
                10, 22 -> "Dez"
                11, 23 -> "Onze"
                else -> "invalid"
            }
        }

        private fun untilForHourPT(hour: Int): String {
            return when (hour) {
                0, 1, 12, 13, 24 -> "pra"
                else -> "pras"
            }
        }

        private fun nameForTimePT(hour: Int, minute: Int): FaceText {
            return when (minute) {
                0 -> when (hour) {
                    0 -> FaceText("Meia Noite", true)
                    12 -> FaceText("Meio Dia", true)
                    1, 13 -> FaceText("Uma", true, "Hora", false)
                    in 2..11 -> FaceText(nameForHourWithMinutePT(hour), true, "Horas", false)
                    in 14..23 -> FaceText(nameForHourWithMinutePT(hour), true, "Horas", false)
                    else -> FaceText("Invalid", false, "invalid", false)
                }
                in 1..19 -> FaceText(nameForHourWithMinutePT(hour), true, "e " + nameForMinuteUnitsPT(minute))
                20 -> FaceText(nameForHourWithMinutePT(hour), true, "e Vinte", false)
                in 21..29 -> FaceText(nameForHourWithMinutePT(hour), true, "e Vinte", false, "e "+ nameForMinuteUnitsPT(minute-20))
                30 -> FaceText(nameForHourWithMinutePT(hour), true, "e Meia")
                in 31..39 -> FaceText(nameForHourWithMinutePT(hour), true, "e Trinta", false, "e "+ nameForMinuteUnitsPT(minute-30))
                40 -> FaceText("Vinte", false, untilForHourPT(hour+1), false, nameForHourWithMinutePT(hour + 1), true)
                in 41..44 -> FaceText(nameForHourWithMinutePT(hour), true,"e Quarenta", false, "e " + nameForMinuteUnitsPT(minute-40))
                in 45..59 -> FaceText(nameForMinuteUnitsPT(60-minute), false, untilForHourPT(hour+1), false, nameForHourWithMinutePT(hour + 1), true)
                else -> FaceText("invalid", false)
            }
        }

        private fun nameForMinuteUnitsDE(minute: Int): String {
            return when (minute) {
                0 -> ""
                1 -> "ein"
                2 -> "zwei"
                3 -> "drei"
                4 -> "vier"
                5 -> "f??nf"
                6 -> "sechs"
                7 -> "sieben"
                8 -> "acht"
                9 -> "neun"
                10 -> "zehn"
                11 -> "elf"
                12 -> "zw??lf"
                13 -> "dreizehn"
                14 -> "vierzehn"
                15 -> "Viertel"
                16 -> "sechszehn"
                17 -> "siebzehn"
                18 -> "achtzehn"
                19 -> "neunzehn"
                else -> "invalid"
            }
        }

        private fun nameForHourWithMinuteDE(hour: Int): String {
            return when (hour) {
                0, 12, 24 -> "Zw??lf"
                1, 13 -> "Eins"
                2, 14 -> "Zwei"
                3, 15 -> "Drei"
                4, 16 -> "Vier"
                5, 17 -> "F??nf"
                6, 18 -> "Sechs"
                7, 19 -> "Sieben"
                8, 20 -> "Acht"
                9, 21 -> "Neun"
                10, 22 -> "Zehn"
                11, 23 -> "Elf"
                else -> "invalid"
            }
        }

        private fun nameForTimeDE(hour: Int, minute: Int): FaceText {
            return when (minute) {
                0 -> when (hour) {
                    0 -> FaceText("Es ist", false, "Mitternacht", true)
                    12 -> FaceText("Es ist", false, "Mittag", true)
                    1, 13 -> FaceText("Es ist", false, "Ein", true, "Uhr", false)
                    in 2..11 -> FaceText("Es ist", false, nameForHourWithMinuteDE(hour), true, "Uhr", false)
                    in 14..23 -> FaceText("Es ist", false, nameForHourWithMinuteDE(hour), true, "Uhr", false)
                    else -> FaceText("Invalid", false, "invalid", false)
                }
                in 1..19 -> FaceText("Es ist ", false, nameForMinuteUnitsDE(minute), false, "nach " + nameForHourWithMinuteDE(hour), true)
                20 -> FaceText("Es ist", false, "zwanzig", false, "nach " + nameForHourWithMinuteDE(hour), true)
                in 21..29 -> FaceText("Es ist", false, nameForMinuteUnitsDE(30-minute) + " vor", false, "halb "+ nameForHourWithMinuteDE(hour + 1), true)
                30 -> FaceText("Es ist", false, "halb " + nameForHourWithMinuteDE(hour+1), true)
                in 31..39 -> FaceText("Es ist", false, nameForMinuteUnitsDE(minute-30) + " nach", false, "halb " + nameForHourWithMinuteDE(hour + 1), true)
                40 -> FaceText("Es ist", false, "zwanzig vor", false, nameForHourWithMinuteDE(hour + 1), true)
                in 41..59 -> FaceText("Es ist", false, nameForMinuteUnitsDE(60-minute), false, "vor " + nameForHourWithMinuteDE(hour + 1), true)
                else -> FaceText("invalid", false)
            }
        }
        fun renderWatchFace(
            configs: Configs, canvas: Canvas,
            bounds: Rect,
            zonedDateTime: ZonedDateTime,
            complicationsDraw: (canvas:Canvas, zonedDateTime:ZonedDateTime) -> Unit
        ) {
            val faceText = when (configs.language) {
                Languages.PORTUGUESE -> nameForTimePT(zonedDateTime.hour, zonedDateTime.minute)
                Languages.GERMAN -> nameForTimeDE(zonedDateTime.hour, zonedDateTime.minute)
            }

            val mCenterX = bounds.exactCenterX()
            val mCenterY = bounds.exactCenterY()

            canvas.drawColor(configs.backgroundColor)

            complicationsDraw(canvas, zonedDateTime)

            canvas.drawText(
                faceText.line_0,
                mCenterX - 180,
                mCenterY - 50,
                when (faceText.highlight_0) {
                    true -> configs.normalPaint; false -> configs.highlightPaint
                }
            )
            canvas.drawText(
                faceText.line_1,
                mCenterX - 180,
                mCenterY + 20,
                when (faceText.highlight_1) {
                    true -> configs.normalPaint; false -> configs.highlightPaint
                }
            )
            canvas.drawText(
                faceText.line_2,
                mCenterX - 180,
                mCenterY + 90,
                when (faceText.highlight_2) {
                    true -> configs.normalPaint; false -> configs.highlightPaint
                }
            )
        }
    }

}