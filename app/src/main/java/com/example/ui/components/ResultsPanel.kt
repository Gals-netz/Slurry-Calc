package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.SlurryCalculationResult
import com.example.model.SlurryCalculator.formatIndonesian
import com.example.ui.theme.Amber100
import com.example.ui.theme.Amber200
import com.example.ui.theme.Amber300
import com.example.ui.theme.Amber400
import com.example.ui.theme.Amber50
import com.example.ui.theme.Amber500
import com.example.ui.theme.Amber600
import com.example.ui.theme.Amber700
import com.example.ui.theme.Amber800
import com.example.ui.theme.Amber900
import com.example.ui.theme.Amber950
import com.example.ui.theme.Blue200
import com.example.ui.theme.Blue300
import com.example.ui.theme.Blue400
import com.example.ui.theme.Blue500
import com.example.ui.theme.Emerald200
import com.example.ui.theme.Emerald300
import com.example.ui.theme.Emerald400
import com.example.ui.theme.Emerald50
import com.example.ui.theme.Emerald500
import com.example.ui.theme.Emerald600
import com.example.ui.theme.Emerald700
import com.example.ui.theme.Emerald800
import com.example.ui.theme.Emerald900
import com.example.ui.theme.Emerald950
import com.example.ui.theme.JetBrainsMonoFontFamily
import com.example.ui.theme.Sky200
import com.example.ui.theme.Sky300
import com.example.ui.theme.Sky50
import com.example.ui.theme.Sky500
import com.example.ui.theme.Sky600
import com.example.ui.theme.Sky700
import com.example.ui.theme.Sky800
import com.example.ui.theme.Sky900
import com.example.ui.theme.Sky950
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate50
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950

@Composable
fun PlaceholderBeforeCalculateCard(
    isWideScreen: Boolean,
    onCalculateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .drawBehind {
                val stroke = Stroke(
                    width = 2.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(14f, 10f), 0f)
                )
                drawRoundRect(
                    color = Slate300,
                    cornerRadius = CornerRadius(16.dp.toPx(), 16.dp.toPx()),
                    style = stroke
                )
            }
            .padding(horizontal = 24.dp, vertical = 36.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Amber100),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Science,
                    contentDescription = null,
                    tint = Amber600,
                    modifier = Modifier.size(32.dp)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Siap Menghitung Komposisi Slurry",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Slate800,
                        fontWeight = FontWeight.ExtraBold
                    ),
                    textAlign = TextAlign.Center
                )
                val directionHint = if (isWideScreen) "di sebelah kiri" else "di atas"
                Text(
                    text = buildAnnotatedString {
                        append("Pastikan nilai ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = Slate700)) {
                            append("SG Slurry")
                        }
                        append(" dan ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = Slate700)) {
                            append("Density Material")
                        }
                        append(" $directionHint sudah sesuai, lalu tekan tombol ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = Slate700)) {
                            append("HITUNG")
                        }
                        append(" untuk menampilkan hasil analisis.")
                    },
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Slate500,
                        textAlign = TextAlign.Center
                    )
                )
            }

            Button(
                onClick = onCalculateClick,
                modifier = Modifier
                    .height(48.dp)
                    .testTag("calculate_now_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Amber500,
                    contentColor = Slate950
                ),
                contentPadding = PaddingValues(horizontal = 28.dp, vertical = 12.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.action_calculate_now),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Black,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}

@Composable
fun CompositionResultsCard(
    result: SlurryCalculationResult,
    modifier: Modifier = Modifier
) {
    val animCvRatio by animateFloatAsState(
        targetValue = result.rasioVolCv.toFloat().coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 280),
        label = "cv_ratio_anim"
    )
    val animCwRatio by animateFloatAsState(
        targetValue = result.rasioMassaCw.toFloat().coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 280),
        label = "cw_ratio_anim"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(Slate900, Slate800, Slate900)
                )
            )
            .border(1.dp, Slate700, RoundedCornerShape(16.dp))
            .padding(18.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // Card Header
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PieChart,
                            contentDescription = null,
                            tint = Amber400,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "HASIL KOMPOSISI SEDIMEN & AIR",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = Amber400,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 12.sp,
                                letterSpacing = 0.6.sp
                            ),
                            maxLines = 2
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(Emerald500.copy(alpha = 0.2f))
                            .border(
                                1.dp,
                                Emerald400.copy(alpha = 0.3f),
                                RoundedCornerShape(50)
                            )
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "SG Slurry: ${formatIndonesian(result.sg, 2)} | Density: ${formatIndonesian(result.rhoSed, 2)}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Emerald300,
                                fontSize = 10.5.sp
                            )
                        )
                    }
                }
                HorizontalDivider(color = Slate700)
            }

            // Two Big Metric Cards (Cv and Cw)
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val twoCols = maxWidth >= 460.dp
                if (twoCols) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Max),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CvMetricCard(
                            result = result,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                        CwMetricCard(
                            result = result,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                    }
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        CvMetricCard(result = result, modifier = Modifier.fillMaxWidth())
                        CwMetricCard(result = result, modifier = Modifier.fillMaxWidth())
                    }
                }
            }

            // Visualisasi Beaker & Progress Bars Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Slate950.copy(alpha = 0.6f))
                    .border(1.dp, Slate700.copy(alpha = 0.8f), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                    val sideBySide = maxWidth >= 460.dp
                    if (sideBySide) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            BeakerIllustration(
                                cvRatio = animCvRatio,
                                persenCv = result.persenCv,
                                persenVolAir = result.persenVolAir,
                                modifier = Modifier.width(124.dp)
                            )
                            ProportionBarsColumn(
                                result = result,
                                animCvRatio = animCvRatio,
                                animCwRatio = animCwRatio,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    } else {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            BeakerIllustration(
                                cvRatio = animCvRatio,
                                persenCv = result.persenCv,
                                persenVolAir = result.persenVolAir
                            )
                            ProportionBarsColumn(
                                result = result,
                                animCvRatio = animCvRatio,
                                animCwRatio = animCwRatio,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CvMetricCard(
    result: SlurryCalculationResult,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Amber500.copy(alpha = 0.15f))
            .border(2.dp, Amber400.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = buildAnnotatedString {
                    append("VOLUME SEDIMEN : SLURRY (C")
                    withStyle(
                        SpanStyle(
                            baselineShift = BaselineShift.Subscript,
                            fontSize = 9.sp
                        )
                    ) {
                        append("v")
                    }
                    append(")")
                },
                style = MaterialTheme.typography.labelMedium.copy(
                    color = Amber300,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 11.sp
                ),
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Filled.ViewInAr,
                contentDescription = null,
                tint = Amber400,
                modifier = Modifier.size(16.dp)
            )
        }

        Column {
            Text(
                text = "${formatIndonesian(result.persenCv, 2)}%",
                style = MaterialTheme.typography.displayLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    fontSize = 36.sp
                ),
                modifier = Modifier.testTag("out_cv_persen")
            )
            Text(
                text = buildAnnotatedString {
                    append("Rasio Volume: ")
                    withStyle(SpanStyle(color = Color.White, fontWeight = FontWeight.Bold)) {
                        append(formatIndonesian(result.rasioVolCv, 4))
                    }
                },
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = JetBrainsMonoFontFamily,
                    color = Amber200
                ),
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        HorizontalDivider(color = Amber400.copy(alpha = 0.2f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Volume Air (Water):",
                style = MaterialTheme.typography.bodySmall.copy(color = Slate300)
            )
            Text(
                text = "${formatIndonesian(result.persenVolAir, 2)}%",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Sky300,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp
                )
            )
        }
    }
}

@Composable
private fun CwMetricCard(
    result: SlurryCalculationResult,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Blue500.copy(alpha = 0.15f))
            .border(2.dp, Blue400.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = buildAnnotatedString {
                    append("MASSA SEDIMEN : SLURRY (C")
                    withStyle(
                        SpanStyle(
                            baselineShift = BaselineShift.Subscript,
                            fontSize = 9.sp
                        )
                    ) {
                        append("w")
                    }
                    append(")")
                },
                style = MaterialTheme.typography.labelMedium.copy(
                    color = Blue300,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 11.sp
                ),
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Filled.Scale,
                contentDescription = null,
                tint = Blue400,
                modifier = Modifier.size(16.dp)
            )
        }

        Column {
            Text(
                text = "${formatIndonesian(result.persenCw, 2)}%",
                style = MaterialTheme.typography.displayLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    fontSize = 36.sp
                ),
                modifier = Modifier.testTag("out_cw_persen")
            )
            Text(
                text = buildAnnotatedString {
                    append("Rasio Massa: ")
                    withStyle(SpanStyle(color = Color.White, fontWeight = FontWeight.Bold)) {
                        append(formatIndonesian(result.rasioMassaCw, 4))
                    }
                },
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = JetBrainsMonoFontFamily,
                    color = Blue200
                ),
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        HorizontalDivider(color = Blue400.copy(alpha = 0.2f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Massa Air (Water):",
                style = MaterialTheme.typography.bodySmall.copy(color = Slate300)
            )
            Text(
                text = "${formatIndonesian(result.persenMassaAir, 2)}%",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Sky300,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp
                )
            )
        }
    }
}

@Composable
private fun BeakerIllustration(
    cvRatio: Float,
    persenCv: Double,
    persenVolAir: Double,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Ilustrasi Campuran",
            style = MaterialTheme.typography.labelMedium.copy(
                color = Slate300,
                fontSize = 11.sp
            )
        )

        Box(
            modifier = Modifier
                .width(112.dp)
                .height(144.dp)
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .background(Slate900)
        ) {
            // Water & Sediment layers inside the beaker
            Canvas(modifier = Modifier.fillMaxSize()) {
                val sedHeight = size.height * cvRatio
                val waterHeight = size.height - sedHeight

                // Water layer (top)
                if (waterHeight > 0f) {
                    drawRect(
                        color = Sky500.copy(alpha = 0.85f),
                        topLeft = Offset(0f, 0f),
                        size = Size(size.width, waterHeight)
                    )
                }

                // Sediment layer (bottom)
                if (sedHeight > 0f) {
                    drawRect(
                        color = Amber600,
                        topLeft = Offset(0f, waterHeight),
                        size = Size(size.width, sedHeight)
                    )
                    // Subtle dividing line at top of sediment
                    drawLine(
                        color = Amber300.copy(alpha = 0.55f),
                        start = Offset(0f, waterHeight),
                        end = Offset(size.width, waterHeight),
                        strokeWidth = 1.5.dp.toPx()
                    )
                }

                // Right-edge graduation tick marks
                val tickTop = 10.dp.toPx()
                val tickBottom = size.height - 10.dp.toPx()
                val tickStep = (tickBottom - tickTop) / 4f
                for (i in 0..4) {
                    val y = tickTop + i * tickStep
                    val tickWidth = if (i % 2 == 0) 12.dp.toPx() else 8.dp.toPx()
                    drawLine(
                        color = Color.White.copy(alpha = 0.6f),
                        start = Offset(size.width - tickWidth, y),
                        end = Offset(size.width, y),
                        strokeWidth = 1.2.dp.toPx()
                    )
                }

                // U-shaped beaker glass border (left, bottom, right — open top)
                val borderWidth = 4.dp.toPx()
                val half = borderWidth / 2f
                // Left wall
                drawLine(
                    color = Slate300,
                    start = Offset(half, 0f),
                    end = Offset(half, size.height - half),
                    strokeWidth = borderWidth
                )
                // Right wall
                drawLine(
                    color = Slate300,
                    start = Offset(size.width - half, 0f),
                    end = Offset(size.width - half, size.height - half),
                    strokeWidth = borderWidth
                )
                // Bottom wall
                drawLine(
                    color = Slate300,
                    start = Offset(0f, size.height - half),
                    end = Offset(size.width, size.height - half),
                    strokeWidth = borderWidth
                )
            }

            // Percentage labels overlay centered in their respective bands
            Column(modifier = Modifier.fillMaxSize()) {
                val waterWeight = (1f - cvRatio).coerceAtLeast(0.0001f)
                val sedWeight = cvRatio.coerceAtLeast(0.0001f)

                if (cvRatio < 0.999f) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(waterWeight),
                        contentAlignment = Alignment.Center
                    ) {
                        if (persenVolAir >= 18.0) {
                            Text(
                                text = "Air ${formatIndonesian(persenVolAir, 1)}%",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }
                if (cvRatio > 0.001f) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(sedWeight),
                        contentAlignment = Alignment.Center
                    ) {
                        if (persenCv >= 10.0) {
                            Text(
                                text = "${formatIndonesian(persenCv, 1)}%",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Amber100,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProportionBarsColumn(
    result: SlurryCalculationResult,
    animCvRatio: Float,
    animCwRatio: Float,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Bar 1: Proporsi Volume
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Sedimen: ${formatIndonesian(result.volSedimen, 2)} cm³ (${formatIndonesian(result.persenCv, 2)}%)",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Amber400,
                        fontSize = 11.sp
                    ),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Air: ${formatIndonesian(result.volWater, 2)} cm³ (${formatIndonesian(result.persenVolAir, 2)}%)",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Sky300,
                        fontSize = 11.sp
                    ),
                    textAlign = TextAlign.End
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Sky600)
                    .border(1.dp, Slate600, RoundedCornerShape(8.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(fraction = animCvRatio)
                        .background(Amber500)
                )
            }
            Text(
                text = buildAnnotatedString {
                    append("Proporsi berdasarkan ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = Slate300)) {
                        append("Volume")
                    }
                },
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Slate400,
                    fontWeight = FontWeight.Normal,
                    fontSize = 11.sp
                )
            )
        }

        // Bar 2: Proporsi Massa
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Sedimen: ${formatIndonesian(result.massaSedimen, 2)} gr (${formatIndonesian(result.persenCw, 2)}%)",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Amber400,
                        fontSize = 11.sp
                    ),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Air: ${formatIndonesian(result.massaWater, 2)} gr (${formatIndonesian(result.persenMassaAir, 2)}%)",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Sky300,
                        fontSize = 11.sp
                    ),
                    textAlign = TextAlign.End
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Sky600)
                    .border(1.dp, Slate600, RoundedCornerShape(8.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(fraction = animCwRatio)
                        .background(Amber500)
                )
            }
            Text(
                text = buildAnnotatedString {
                    append("Proporsi berdasarkan ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = Slate300)) {
                        append("Massa (Berat)")
                    }
                },
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Slate400,
                    fontWeight = FontWeight.Normal,
                    fontSize = 11.sp
                )
            )
        }
    }
}

@Composable
fun BalanceTableCard(
    result: SlurryCalculationResult,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Slate200),
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.TableChart,
                                contentDescription = null,
                                tint = Emerald600,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Rincian Kesetimbangan Massa & Volume (Per 1 Liter / 1.000 cm³ Slurry)",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = Slate800,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            )
                        }
                        Text(
                            text = "Perbandingan komponen campuran slurry, air, dan padatan sedimen.",
                            style = MaterialTheme.typography.bodySmall.copy(color = Slate500),
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Emerald50)
                        .border(1.dp, Emerald200, RoundedCornerShape(50))
                        .padding(horizontal = 12.dp, vertical = 5.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.DoneAll,
                            contentDescription = null,
                            tint = Emerald700,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "Slurry = Water + Sedimen",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = Emerald700,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 11.5.sp
                            )
                        )
                    }
                }
                HorizontalDivider(color = Slate100)
            }

            // Table with horizontal scroll on narrow devices
            val scrollState = rememberScrollState()
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val minTableWidth = maxOf(maxWidth, 620.dp)
                Column(
                    modifier = Modifier
                        .horizontalScroll(scrollState)
                        .width(minTableWidth)
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, Slate200, RoundedCornerShape(12.dp))
                ) {
                    // Table Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Max)
                    ) {
                        TableHeaderCell(
                            text = "Parameter",
                            bg = Slate800,
                            align = TextAlign.Start,
                            width = 120.dp
                        )
                        TableHeaderCell(
                            text = "Satuan",
                            bg = Slate800,
                            align = TextAlign.Center,
                            width = 84.dp
                        )
                        TableHeaderCell(
                            text = "Slurry (Campuran)",
                            bg = Slate700,
                            align = TextAlign.End,
                            modifier = Modifier.weight(1f)
                        )
                        TableHeaderCell(
                            text = "Water (Air)",
                            bg = Sky900,
                            align = TextAlign.End,
                            modifier = Modifier.weight(1f)
                        )
                        TableHeaderCell(
                            text = "Sedimen (Padatan)",
                            bg = Amber800,
                            align = TextAlign.End,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Row 1: Massa (gr)
                    TableDataRow(
                        parameter = "Massa",
                        unit = "gr",
                        slurryVal = formatIndonesian(result.massaSlurry, 2),
                        waterVal = formatIndonesian(result.massaWater, 2),
                        sedimenVal = formatIndonesian(result.massaSedimen, 2),
                        isLargeNumbers = true
                    )
                    HorizontalDivider(color = Slate200)

                    // Row 2: Volume (cm3)
                    TableDataRow(
                        parameter = "Volume",
                        unit = "cm³",
                        slurryVal = formatIndonesian(result.volSlurry, 2),
                        waterVal = formatIndonesian(result.volWater, 2),
                        sedimenVal = formatIndonesian(result.volSedimen, 2),
                        isLargeNumbers = true
                    )
                    HorizontalDivider(color = Slate200)

                    // Row 3: SG / Density (gr/cm3)
                    TableDataRow(
                        parameter = "SG / Density",
                        unit = "gr/cm³",
                        slurryVal = formatIndonesian(result.sg, 2),
                        waterVal = formatIndonesian(1.0, 2),
                        sedimenVal = formatIndonesian(result.rhoSed, 2),
                        isLargeNumbers = false
                    )
                }
            }
        }
    }
}

@Composable
private fun TableHeaderCell(
    text: String,
    bg: Color,
    align: TextAlign,
    modifier: Modifier = Modifier,
    width: Dp? = null
) {
    val sizeMod = if (width != null) modifier.width(width) else modifier
    Box(
        modifier = sizeMod
            .fillMaxHeight()
            .background(bg)
            .border(0.5.dp, Slate700)
            .padding(horizontal = 12.dp, vertical = 12.dp),
        contentAlignment = when (align) {
            TextAlign.Center -> Alignment.Center
            TextAlign.End -> Alignment.CenterEnd
            else -> Alignment.CenterStart
        }
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            ),
            textAlign = align
        )
    }
}

@Composable
private fun TableDataRow(
    parameter: String,
    unit: String,
    slurryVal: String,
    waterVal: String,
    sedimenVal: String,
    isLargeNumbers: Boolean
) {
    val numSize = if (isLargeNumbers) 15.sp else 13.sp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
    ) {
        Box(
            modifier = Modifier
                .width(120.dp)
                .fillMaxHeight()
                .background(Color.White)
                .border(0.5.dp, Slate200)
                .padding(horizontal = 12.dp, vertical = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = parameter,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Slate800,
                    fontWeight = FontWeight.ExtraBold
                )
            )
        }
        Box(
            modifier = Modifier
                .width(84.dp)
                .fillMaxHeight()
                .background(Color.White)
                .border(0.5.dp, Slate200)
                .padding(horizontal = 8.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = unit,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Slate600,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Slate50)
                .border(0.5.dp, Slate200)
                .padding(horizontal = 12.dp, vertical = 12.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = slurryVal,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = JetBrainsMonoFontFamily,
                    color = Slate900,
                    fontWeight = FontWeight.Black,
                    fontSize = numSize
                ),
                maxLines = 1
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Sky50.copy(alpha = 0.5f))
                .border(0.5.dp, Slate200)
                .padding(horizontal = 12.dp, vertical = 12.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = waterVal,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = JetBrainsMonoFontFamily,
                    color = Sky800,
                    fontWeight = FontWeight.Bold,
                    fontSize = numSize
                ),
                maxLines = 1
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Amber50.copy(alpha = 0.6f))
                .border(0.5.dp, Slate200)
                .padding(horizontal = 12.dp, vertical = 12.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = sedimenVal,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = JetBrainsMonoFontFamily,
                    color = Amber900,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = numSize
                ),
                maxLines = 1
            )
        }
    }
}

@Composable
fun PracticalConversionCard(
    result: SlurryCalculationResult,
    onCopySummaryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Slate200),
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header + Copy Summary Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ViewInAr,
                        contentDescription = null,
                        tint = Amber600,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "KONVERSI PRAKTIS LAPANGAN (DALAM SETIAP 1 M³ / 1.000 LITER SLURRY)",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = Slate700,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 11.5.sp,
                            letterSpacing = 0.4.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = onCopySummaryClick,
                    modifier = Modifier
                        .height(48.dp)
                        .testTag("copy_summary_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Emerald600,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(horizontal = 14.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ContentCopy,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = stringResource(R.string.action_copy_summary),
                        style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // 3 Conversion Cards
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val threeCols = maxWidth >= 680.dp
                if (threeCols) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Max),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        SedimenVolConversionBox(
                            result = result,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                        WaterVolConversionBox(
                            result = result,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                        SedimenMassConversionBox(
                            result = result,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                    }
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        SedimenVolConversionBox(
                            result = result,
                            modifier = Modifier.fillMaxWidth()
                        )
                        WaterVolConversionBox(
                            result = result,
                            modifier = Modifier.fillMaxWidth()
                        )
                        SedimenMassConversionBox(
                            result = result,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SedimenVolConversionBox(
    result: SlurryCalculationResult,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Amber50.copy(alpha = 0.8f))
            .border(1.dp, Amber200, RoundedCornerShape(12.dp))
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "Volume Sedimen Padat",
            style = MaterialTheme.typography.bodySmall.copy(
                color = Amber900,
                fontWeight = FontWeight.Bold
            )
        )
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = formatIndonesian(result.volSedM3, 3),
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Amber950,
                    fontWeight = FontWeight.Black,
                    fontSize = 26.sp
                )
            )
            Text(
                text = "m³",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Amber800,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 2.dp)
            )
            Text(
                text = "(${formatIndonesian(result.volSedLiter, 1)} Liter)",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Amber700,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 3.dp)
            )
        }
        Text(
            text = "Jumlah volume padatan murni di dalam 1 m³ slurry.",
            style = MaterialTheme.typography.labelSmall.copy(
                color = Amber800.copy(alpha = 0.8f),
                fontWeight = FontWeight.Normal,
                fontSize = 11.sp
            )
        )
    }
}

@Composable
private fun WaterVolConversionBox(
    result: SlurryCalculationResult,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Sky50.copy(alpha = 0.8f))
            .border(1.dp, Sky200, RoundedCornerShape(12.dp))
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "Volume Air (Water)",
            style = MaterialTheme.typography.bodySmall.copy(
                color = Sky900,
                fontWeight = FontWeight.Bold
            )
        )
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = formatIndonesian(result.volAirM3, 3),
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Sky950,
                    fontWeight = FontWeight.Black,
                    fontSize = 26.sp
                )
            )
            Text(
                text = "m³",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Sky800,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 2.dp)
            )
            Text(
                text = "(${formatIndonesian(result.volAirLiter, 1)} Liter)",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Sky700,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 3.dp)
            )
        }
        Text(
            text = "Jumlah volume air di dalam 1 m³ slurry.",
            style = MaterialTheme.typography.labelSmall.copy(
                color = Sky800.copy(alpha = 0.8f),
                fontWeight = FontWeight.Normal,
                fontSize = 11.sp
            )
        )
    }
}

@Composable
private fun SedimenMassConversionBox(
    result: SlurryCalculationResult,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Emerald50.copy(alpha = 0.8f))
            .border(1.dp, Emerald200, RoundedCornerShape(12.dp))
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "Massa / Tonase Sedimen",
            style = MaterialTheme.typography.bodySmall.copy(
                color = Emerald900,
                fontWeight = FontWeight.Bold
            )
        )
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = formatIndonesian(result.tonSed, 3),
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Emerald950,
                    fontWeight = FontWeight.Black,
                    fontSize = 26.sp
                )
            )
            Text(
                text = "Ton",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Emerald800,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 2.dp)
            )
            Text(
                text = "(${formatIndonesian(result.kgSed, 1)} kg)",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Emerald700,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 3.dp)
            )
        }
        Text(
            text = buildAnnotatedString {
                append("Total berat kering sedimen dalam 1 m³ slurry (total massa = ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = Emerald900)) {
                    append("${formatIndonesian(result.tonSlurry, 2)} Ton")
                }
                append(").")
            },
            style = MaterialTheme.typography.labelSmall.copy(
                color = Emerald800.copy(alpha = 0.8f),
                fontWeight = FontWeight.Normal,
                fontSize = 11.sp
            )
        )
    }
}
