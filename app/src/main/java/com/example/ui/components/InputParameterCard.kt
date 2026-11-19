package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.MaterialPreset
import com.example.model.SlurryCalculator
import com.example.ui.SlurryUiState
import com.example.ui.theme.Amber200
import com.example.ui.theme.Amber300
import com.example.ui.theme.Amber400
import com.example.ui.theme.Amber50
import com.example.ui.theme.Amber500
import com.example.ui.theme.Amber600
import com.example.ui.theme.Amber800
import com.example.ui.theme.Amber900
import com.example.ui.theme.Amber950
import com.example.ui.theme.Blue200
import com.example.ui.theme.Blue50
import com.example.ui.theme.Blue600
import com.example.ui.theme.Blue700
import com.example.ui.theme.Blue900
import com.example.ui.theme.Red300
import com.example.ui.theme.Red50
import com.example.ui.theme.Red600
import com.example.ui.theme.Red800
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate50
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950

@Composable
fun InputParameterCard(
    uiState: SlurryUiState,
    onAdjustSg: (Double) -> Unit,
    onSgTextChanged: (String) -> Unit,
    onSgSliderChanged: (Float) -> Unit,
    onAdjustRhoSedimen: (Double) -> Unit,
    onRhoSedimenTextChanged: (String) -> Unit,
    onSelectPreset: (Double) -> Unit,
    onCalculateClick: () -> Unit,
    onResetClick: () -> Unit,
    showInlineActionRow: Boolean = true,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Slate200),
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
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
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(Amber500),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "1",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = Slate950,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 12.sp
                                )
                            )
                        }
                        Text(
                            text = "Input Parameter Pengukuran",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Slate800,
                                fontWeight = FontWeight.ExtraBold
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(Slate100)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Data Lapangan",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = Slate500,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
                HorizontalDivider(color = Slate100)
            }

            // 1. Input SG Slurry Box (Amber)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Amber50.copy(alpha = 0.8f))
                    .border(1.dp, Amber200, RoundedCornerShape(14.dp))
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "SG Slurry (Campuran)",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Slate900,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 14.sp
                        )
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Amber200.copy(alpha = 0.8f))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "gr/cm³ (kg/L)",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = Amber900,
                                fontSize = 11.sp
                            )
                        )
                    }
                }

                Text(
                    text = "Berat jenis campuran lumpur (air + padatan) dari hasil pengukuran.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Amber900.copy(alpha = 0.8f)
                    )
                )

                // Stepper + Numeric Input Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    AmberStepperButton(
                        label = "-0.05",
                        onClick = {
                            focusManager.clearFocus()
                            onAdjustSg(-0.05)
                        },
                        modifier = Modifier.testTag("sg_minus_005")
                    )
                    AmberStepperButton(
                        label = "-0.01",
                        onClick = {
                            focusManager.clearFocus()
                            onAdjustSg(-0.01)
                        },
                        modifier = Modifier.testTag("sg_minus_001")
                    )

                    NumericParameterInput(
                        value = uiState.sgInputText,
                        onValueChange = onSgTextChanged,
                        borderColor = Amber500,
                        onDone = {
                            focusManager.clearFocus()
                            onCalculateClick()
                        },
                        inputTestTag = "sg_input",
                        modifier = Modifier.weight(1f)
                    )

                    AmberStepperButton(
                        label = "+0.01",
                        onClick = {
                            focusManager.clearFocus()
                            onAdjustSg(0.01)
                        },
                        modifier = Modifier.testTag("sg_plus_001")
                    )
                    AmberStepperButton(
                        label = "+0.05",
                        onClick = {
                            focusManager.clearFocus()
                            onAdjustSg(0.05)
                        },
                        modifier = Modifier.testTag("sg_plus_005")
                    )
                }

                // Slider SG (1.00 .. 2.20)
                Column {
                    Slider(
                        value = uiState.sgSliderValue,
                        onValueChange = onSgSliderChanged,
                        valueRange = SlurryCalculator.SLIDER_MIN_SG.toFloat()..SlurryCalculator.SLIDER_MAX_SG.toFloat(),
                        colors = SliderDefaults.colors(
                            thumbColor = Amber600,
                            activeTrackColor = Amber600,
                            inactiveTrackColor = Amber200
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("sg_slider")
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        listOf("1.00 (Air)", "1.30", "1.60", "1.90", "2.20").forEach { tick ->
                            Text(
                                text = tick,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Amber800.copy(alpha = 0.7f),
                                    fontSize = 10.sp
                                )
                            )
                        }
                    }
                }
            }

            // 2. Input Density Sedimen / Solid Box (Slate)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Slate50)
                    .border(1.dp, Slate200, RoundedCornerShape(14.dp))
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Density Sedimen / Material Padat (ρ)",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Slate900,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 14.sp
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Slate200)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "gr/cm³ (ton/m³)",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = Slate600,
                                fontSize = 11.sp
                            )
                        )
                    }
                }

                Text(
                    text = "Berat jenis partikel padatan kering murni (pilih standar material atau ketik manual).",
                    style = MaterialTheme.typography.bodySmall.copy(color = Slate500)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    SlateStepperButton(
                        label = "-0.05",
                        onClick = {
                            focusManager.clearFocus()
                            onAdjustRhoSedimen(-0.05)
                        },
                        modifier = Modifier.testTag("rho_minus_005")
                    )

                    NumericParameterInput(
                        value = uiState.rhoSedimenInputText,
                        onValueChange = onRhoSedimenTextChanged,
                        borderColor = Slate300,
                        onDone = {
                            focusManager.clearFocus()
                            onCalculateClick()
                        },
                        inputTestTag = "rho_input",
                        modifier = Modifier.weight(1f)
                    )

                    SlateStepperButton(
                        label = "+0.05",
                        onClick = {
                            focusManager.clearFocus()
                            onAdjustRhoSedimen(0.05)
                        },
                        modifier = Modifier.testTag("rho_plus_005")
                    )
                }

                // Preset Material Grid
                Column(
                    modifier = Modifier.padding(top = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Layers,
                            contentDescription = null,
                            tint = Amber600,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "PILIH STANDAR DENSITY MATERIAL:",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = Slate600,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 0.5.sp
                            )
                        )
                    }

                    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                        val useTwoColumns = maxWidth >= 280.dp
                        val presets = SlurryCalculator.materialPresets
                        if (useTwoColumns) {
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                presets.chunked(2).forEach { pair ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(IntrinsicSize.Max),
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        pair.forEach { preset ->
                                            PresetMaterialButton(
                                                preset = preset,
                                                isSelected = uiState.isPresetSelected(preset.density),
                                                onClick = {
                                                    focusManager.clearFocus()
                                                    onSelectPreset(preset.density)
                                                },
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .fillMaxHeight()
                                            )
                                        }
                                        if (pair.size == 1) {
                                            Spacer(modifier = Modifier.weight(1f))
                                        }
                                    }
                                }
                            }
                        } else {
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                presets.forEach { preset ->
                                    PresetMaterialButton(
                                        preset = preset,
                                        isSelected = uiState.isPresetSelected(preset.density),
                                        onClick = {
                                            focusManager.clearFocus()
                                            onSelectPreset(preset.density)
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Warning Banner (when SG > Density Material)
            AnimatedVisibility(
                visible = uiState.warningMessage != null,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Red50)
                        .border(1.dp, Red300, RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = "Peringatan",
                        tint = Red600,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = uiState.warningMessage.orEmpty(),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Red800,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }

            // Primary Action Row: HITUNG + Reset (inline on wide layout)
            if (showInlineActionRow) {
                CalculateActionRow(
                    needsRecalculation = uiState.needsRecalculation,
                    onCalculateClick = onCalculateClick,
                    onResetClick = onResetClick,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}

@Composable
fun CalculateActionRow(
    needsRecalculation: Boolean,
    onCalculateClick: () -> Unit,
    onResetClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val ringModifier = if (needsRecalculation) {
            Modifier.border(
                width = 3.dp,
                color = Amber400.copy(alpha = 0.65f),
                shape = RoundedCornerShape(16.dp)
            )
        } else {
            Modifier
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .then(ringModifier)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Blue600, Blue700)
                    )
                )
                .clickable {
                    focusManager.clearFocus()
                    onCalculateClick()
                }
                .heightIn(min = 52.dp)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .testTag("calculate_button"),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Calculate,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
                Text(
                    text = stringResource(R.string.action_calculate),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp,
                        letterSpacing = 0.5.sp
                    )
                )
            }
        }

        OutlinedButton(
            onClick = {
                focusManager.clearFocus()
                onResetClick()
            },
            modifier = Modifier
                .height(52.dp)
                .testTag("reset_bottom_button"),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Slate300),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Slate100,
                contentColor = Slate700
            ),
            contentPadding = PaddingValues(horizontal = 14.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = stringResource(R.string.action_reset),
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = stringResource(R.string.action_reset),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
private fun AmberStepperButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(48.dp)
            .widthIn(min = 42.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Amber300),
        colors = ButtonDefaults.buttonColors(
            containerColor = Amber200,
            contentColor = Amber950
        ),
        contentPadding = PaddingValues(horizontal = 6.dp, vertical = 0.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 11.sp
            ),
            maxLines = 1
        )
    }
}

@Composable
private fun SlateStepperButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(48.dp)
            .widthIn(min = 52.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Slate300),
        colors = ButtonDefaults.buttonColors(
            containerColor = Slate200,
            contentColor = Slate800
        ),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 12.sp
            ),
            maxLines = 1
        )
    }
}

@Composable
private fun NumericParameterInput(
    value: String,
    onValueChange: (String) -> Unit,
    borderColor: Color,
    onDone: () -> Unit,
    inputTestTag: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .border(2.dp, borderColor, RoundedCornerShape(12.dp))
            .padding(horizontal = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = MaterialTheme.typography.headlineMedium.copy(
                color = Slate900,
                fontWeight = FontWeight.Black,
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            ),
            cursorBrush = SolidColor(Blue600),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { onDone() }),
            modifier = Modifier
                .fillMaxWidth()
                .testTag(inputTestTag)
        )
    }
}

@Composable
private fun PresetMaterialButton(
    preset: MaterialPreset,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor = if (isSelected) Blue50 else Color.White
    val borderColor = if (isSelected) Blue600 else Slate300
    val borderWidth = if (isSelected) 2.dp else 1.dp
    val titleColor = if (isSelected) Blue900 else Slate700
    val badgeBg = if (isSelected) Color.White else Slate100
    val badgeTextColor = if (isSelected) Blue700 else Slate800
    val tagValue = SlurryCalculator.formatInputDecimal(preset.density).replace('.', '_')

    Surface(
        modifier = modifier
            .heightIn(min = 48.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .testTag("preset_$tagValue"),
        shape = RoundedCornerShape(12.dp),
        color = bgColor,
        border = BorderStroke(borderWidth, borderColor),
        shadowElevation = if (isSelected) 1.dp else 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = preset.name,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = titleColor,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                    fontSize = 11.5.sp,
                    lineHeight = 14.sp
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 6.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(badgeBg)
                    .then(
                        if (isSelected) {
                            Modifier.border(1.dp, Blue200, RoundedCornerShape(6.dp))
                        } else {
                            Modifier
                        }
                    )
                    .padding(horizontal = 6.dp, vertical = 3.dp)
            ) {
                Text(
                    text = SlurryCalculator.formatInputDecimal(preset.density),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = badgeTextColor,
                        fontWeight = if (isSelected) FontWeight.Black else FontWeight.ExtraBold,
                        fontSize = 11.sp
                    )
                )
            }
        }
    }
}
