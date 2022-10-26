package esque.ma.zeitpunkt.editor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController

class WatchFaceConfigActivity: ComponentActivity() {

    //internal lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            //navController = rememberSwipeDismissableNavController()

            Scaffold() {
                ScalingLazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item { Text("Well hello you!") }
                }
            }
        }
    }
}