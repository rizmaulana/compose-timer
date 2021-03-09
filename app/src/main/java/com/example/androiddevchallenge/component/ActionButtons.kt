package id.rizmaulana.composetimer.compose.component

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.theme.*

@ExperimentalAnimationApi
@Composable
fun ActionButtons(
    onStart: () -> Unit = {},
    onLap: () -> Unit = {},
    onStop: () -> Unit = {}
) {
    var startVisibility by remember { mutableStateOf(true) }
    var stopVisibility by remember { mutableStateOf(false) }
    var lapVisibility by remember { mutableStateOf(false) }
    val gradient = Brush.verticalGradient(listOf(background50, background, background))
    Box(
        modifier = Modifier
            .padding(bottom = 20.dp, top = 20.dp)
            .background(gradient)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {


            AnimatedVisibility(
                visible = stopVisibility,
                enter = getEnterStopAnimation(),
                exit = getExitStopAnimation()
            ) {
                TimerButton(label = "STOP", color = accent) {
                    stopVisibility = false
                    lapVisibility = false
                    startVisibility = true
                    onStop.invoke()
                }
            }

            AnimatedVisibility(
                visible = lapVisibility,
                enter = getEnterLapAnimation(),
                exit = getExitLapAnimation()
            ) {
                TimerButton(label = "LAP", color = secondary, action = onLap)

            }
        }
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
            AnimatedVisibility(
                visible = startVisibility,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                TimerButton(label = "START", color = primary) {
                    stopVisibility = true
                    lapVisibility = true
                    startVisibility = false
                    onStart.invoke()
                }
            }
        }

    }

}

@ExperimentalAnimationApi
internal fun getEnterStopAnimation(): EnterTransition =
    slideInHorizontally(initialOffsetX = { 120 })

@ExperimentalAnimationApi
internal fun getExitStopAnimation(): ExitTransition =
    slideOutHorizontally(targetOffsetX = { 120 }) + fadeOut()

@ExperimentalAnimationApi
internal fun getEnterLapAnimation(): EnterTransition =
    slideInHorizontally(initialOffsetX = { -120 })

@ExperimentalAnimationApi
internal fun getExitLapAnimation(): ExitTransition =
    slideOutHorizontally(targetOffsetX = { -120 }) + fadeOut()


@Composable
internal fun TimerButton(
    label: String,
    color: Color,
    action: () -> Unit = {}
) {
    Box(modifier = Modifier.padding(start = 32.dp, end = 32.dp)) {
        Box(
            Modifier
                .background(color = color, shape = CircleShape)
                .clip(CircleShape)
                .width(80.dp)
                .aspectRatio(1f)
                .clickable(
                    onClick = action
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = label, style = typography.button, color = Color.White, fontSize = 14.sp)
        }
    }

}

@ExperimentalAnimationApi
@Preview
@Composable
fun ActionButtonsPreview() {
    ActionButtons()
}
