package esque.ma.zeitpunkt.editor

import android.graphics.Rect
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.*
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.WearNavigator
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import esque.ma.zeitpunkt.ZeitpunktWatchCanvasRenderer
import esque.ma.zeitpunkt.wearColorPalette
import java.time.ZonedDateTime

class WatchFaceConfigActivity: ComponentActivity() {

    internal lateinit var navController: NavHostController
    internal lateinit var watchFaceConfigs: ZeitpunktWatchCanvasRenderer.Configs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            navController = rememberSwipeDismissableNavController()
            watchFaceConfigs = ZeitpunktWatchCanvasRenderer.Configs().apply { init() }

            WatchEditorTheme {
                Scaffold(

                ) {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Hey!")
                        Button(onClick = { /*TODO*/ }) {
                            Text("Theme")
                        }
                    }
//                    SwipeDismissableNavHost(
//                        navController = navController,
//                        startDestination = "theme_select" ) {
//                        composable("theme_select") {
//                            Column() {
//                                ThemeSelectCanvas(watchFaceConfigs)
//                            }
//                        }
//                    }
                }
            }
        }
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun WatchFaceConfigView(
    watchFaceConfigs:ZeitpunktWatchCanvasRenderer.Configs = ZeitpunktWatchCanvasRenderer.Configs().apply { init() }
) {
    WatchEditorTheme {
        Scaffold(

        ) {
            Box (modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawIntoCanvas { canvas ->
                        ZeitpunktWatchCanvasRenderer.renderWatchFace(
                            configs = watchFaceConfigs,
                            canvas = canvas.nativeCanvas,
                            bounds = Rect(0, drawContext.size.height.toInt(), drawContext.size.width.toInt(), 0),
                            zonedDateTime = ZonedDateTime.now(),
                            complicationsDraw = { canvas, zonedDateTime ->  }
                        )
                    }
                }
                Button(
                    modifier = Modifier.padding(horizontal = 32.dp),
                    onClick = { /*TODO*/ }
                ) {
                    Text("Theme")
                }

            }

        }
    }
}

@Composable
fun ThemeSelectCanvas(
    watchFaceConfigs: ZeitpunktWatchCanvasRenderer.Configs
) {
//    Canvas(modifier = Modifier.fillMaxSize()) {
//        drawIntoCanvas { canvas ->
//            ZeitpunktWatchCanvasRenderer.renderWatchFace(
//                configs = watchFaceConfigs,
//                canvas = canvas.nativeCanvas,
//                bounds = Rect(0, drawContext.size.height.toInt(), drawContext.size.width.toInt(), 0),
//                zonedDateTime = ZonedDateTime.now(),
//                complicationsDraw = { canvas, zonedDateTime ->  }
//            )
//        }
//    }
    Text("Hey hey")
    //Spacer(modifier = Modifier.border(3.dp))
    Button(onClick = { /*TODO*/ }) {
        Text("Hello")
    }
}