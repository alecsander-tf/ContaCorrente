package br.com.contacorrente.jetpack.ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeProgress
import androidx.compose.material.SwipeableDefaults
import androidx.compose.material.SwipeableState
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import br.com.contacorrente.jetpack.ui.ContaCorrenteMainTheme
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SlideToDismiss(
    allFilled: Boolean,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    val swipeState = rememberSwipeableState(
        initialValue = if (allFilled) Anchor.End else Anchor.Start,
        confirmStateChange = { anchor ->
            if (anchor == Anchor.End) {
                onDismiss()
            }
            true
        }
    )

    val swipeFraction by remember {
        derivedStateOf { calculateSwipeFraction(swipeState.progress) }
    }

    LaunchedEffect(allFilled) {
        swipeState.animateTo(if (allFilled) Anchor.End else Anchor.Start)
    }

    Track(
        swipeState = swipeState,
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.surface),
    ) {

        Thumb(
            isLoading = allFilled,
            modifier = Modifier.offset {
                IntOffset(0, swipeState.offset.value.roundToInt())
            },
            content = content
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
fun calculateSwipeFraction(progress: SwipeProgress<Anchor>): Float {
    val atAnchor = progress.from == progress.to
    val fromStart = progress.from == Anchor.Start
    return if (atAnchor) {
        if (fromStart) 0f else 1f
    } else {
        if (fromStart) progress.fraction else 1f - progress.fraction
    }
}

enum class Anchor { Start, End }

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Track(
    swipeState: SwipeableState<Anchor>,
    modifier: Modifier = Modifier,
    content: @Composable (BoxScope.() -> Unit),
) {
    val density = LocalDensity.current
    var fullHeight by remember { mutableIntStateOf(0) }

    val startOfTrackPx = 0f
    val endOfTrackPx = remember(fullHeight) {
        with(density) {
            fullHeight - (Thumb.Size).toPx()
        }
    }

    val snapThreshold = 0.8f
    val thresholds = { from: Anchor, _: Anchor ->
        if (from == Anchor.Start) {
            FractionalThreshold(snapThreshold)
        } else {
            FractionalThreshold(1f - snapThreshold)
        }
    }

    Box(
        modifier = modifier
            .onSizeChanged { fullHeight = it.height }
            .fillMaxWidth()
            .fillMaxHeight()
            .swipeable(
                state = swipeState,
                orientation = Orientation.Vertical,
                anchors = mapOf(
                    startOfTrackPx to Anchor.Start,
                    endOfTrackPx to Anchor.End,
                ),
                thresholds = thresholds,
                velocityThreshold = Track.VelocityThreshold,
            )
            .padding(
                PaddingValues(
                    vertical = 8.dp,
                )
            ),
        content = content,
    )
}

@Composable
fun Thumb(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .height(Thumb.Size)
                .fillMaxWidth()
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
                .padding(8.dp),
        ) {

            Spacer(
                modifier = Modifier
                    .size(
                        width = 40.dp,
                        height = 4.dp,
                    )
                    .align(Alignment.Center)
                    .background(
                        color = MaterialTheme.colorScheme.onSurface,
                        shape = CircleShape,
                    )
            )
        }
        Spacer(
            modifier = Modifier
                .height(0.2.dp)
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.onSurface,
                )
        )
        content()
    }
}

private object Thumb {
    val Size = 40.dp
}

private object Track {
    val VelocityThreshold = SwipeableDefaults.VelocityThreshold * 10
}

@Preview
@Composable
private fun Preview() {
    val previewBackgroundColor = Color(0xFFEDEDED)
    var isLoading by remember { mutableStateOf(false) }
    ContaCorrenteMainTheme {
        val spacing = 88.dp
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(previewBackgroundColor)
                .padding(horizontal = 24.dp),
        ) {
            Spacer(modifier = Modifier.height(spacing))

            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Thumb(isLoading = false) {

                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Thumb(isLoading = true) {

                    }
                }


            }

            Spacer(modifier = Modifier.height(spacing))

            SlideToDismiss(
                allFilled = isLoading,
                onDismiss = { isLoading = true },
                {}
            )
        }
    }
}