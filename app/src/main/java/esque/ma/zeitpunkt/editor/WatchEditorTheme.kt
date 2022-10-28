package esque.ma.zeitpunkt.editor

import androidx.compose.runtime.Composable
import androidx.wear.compose.material.MaterialTheme
import esque.ma.zeitpunkt.data.Typography
import esque.ma.zeitpunkt.wearColorPalette

@Composable
fun WatchEditorTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme (
        colors = wearColorPalette,
        typography = Typography,
        content = content
    )
}