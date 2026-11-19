package com.example.ui

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.BalanceTableCard
import com.example.ui.components.CalculateActionRow
import com.example.ui.components.CompositionResultsCard
import com.example.ui.components.HeaderBar
import com.example.ui.components.InputParameterCard
import com.example.ui.components.PlaceholderBeforeCalculateCard
import com.example.ui.components.PracticalConversionCard
import com.example.ui.theme.Emerald400
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate900
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SlurryCalculatorScreen(
    viewModel: SlurryViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    var resultsSectionTopPx by remember { mutableIntStateOf(0) }
    var toastMessage by remember { mutableStateOf<String?>(null) }
    var toastTriggerId by remember { mutableLongStateOf(0L) }

    fun showToast(message: String) {
        toastMessage = message
        toastTriggerId = System.currentTimeMillis()
    }

    LaunchedEffect(toastTriggerId) {
        if (toastMessage != null) {
            delay(3000)
            toastMessage = null
        }
    }

    fun handleCopyReport() {
        val report = viewModel.getOrCalculateReport()
        clipboardManager.setText(AnnotatedString(report))
        showToast("Laporan hasil perhitungan berhasil disalin!")
    }

    fun handleShareReport() {
        val report = viewModel.getOrCalculateReport()
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Hasil Perhitungan Komposisi Sedimen & SG Slurry")
            putExtra(Intent.EXTRA_TEXT, report)
        }
        context.startActivity(Intent.createChooser(intent, "Bagikan Laporan Slurry"))
    }

    fun handleReset(isExpandedLayout: Boolean) {
        viewModel.reset()
        showToast("Form dikembalikan ke pengaturan awal.")
        if (!isExpandedLayout) {
            coroutineScope.launch {
                scrollState.animateScrollTo(0)
            }
        }
    }

    fun handleCalculate(isExpandedLayout: Boolean) {
        viewModel.calculate()
        if (!isExpandedLayout && resultsSectionTopPx > 0) {
            coroutineScope.launch {
                delay(60)
                scrollState.animateScrollTo(resultsSectionTopPx)
            }
        }
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isWideScreen = maxWidth >= 600.dp
        val isExpandedLayout = maxWidth >= 840.dp

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Slate100,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            topBar = {
                HeaderBar(
                    hasCalculated = uiState.hasCalculated,
                    isWideScreen = isWideScreen,
                    onResetClick = { handleReset(isExpandedLayout) },
                    onCopyClick = ::handleCopyReport,
                    onShareClick = ::handleShareReport
                )
            },
            bottomBar = {
                if (!isExpandedLayout) {
                    Surface(
                        color = Color.White,
                        shadowElevation = 10.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .navigationBarsPadding()
                        ) {
                            HorizontalDivider(color = Slate200)
                            CalculateActionRow(
                                needsRecalculation = uiState.needsRecalculation,
                                onCalculateClick = { handleCalculate(isExpandedLayout = false) },
                                onResetClick = { handleReset(isExpandedLayout = false) },
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .let { mod ->
                            if (isExpandedLayout) mod.navigationBarsPadding() else mod
                        }
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .widthIn(max = 1152.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        if (isExpandedLayout) {
                            // 2-Column Top Grid (5 : 7) on Tablets / Wide Landscape
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(20.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                InputParameterCard(
                                    uiState = uiState,
                                    onAdjustSg = viewModel::adjustSg,
                                    onSgTextChanged = viewModel::onSgTextChanged,
                                    onSgSliderChanged = viewModel::onSgSliderChanged,
                                    onAdjustRhoSedimen = viewModel::adjustRhoSedimen,
                                    onRhoSedimenTextChanged = viewModel::onRhoSedimenTextChanged,
                                    onSelectPreset = viewModel::selectPreset,
                                    onCalculateClick = { handleCalculate(isExpandedLayout = true) },
                                    onResetClick = { handleReset(isExpandedLayout = true) },
                                    showInlineActionRow = true,
                                    modifier = Modifier.weight(5f)
                                )

                                Box(modifier = Modifier.weight(7f)) {
                                    val result = uiState.result
                                    if (!uiState.hasCalculated || result == null) {
                                        PlaceholderBeforeCalculateCard(
                                            isWideScreen = true,
                                            onCalculateClick = { handleCalculate(isExpandedLayout = true) }
                                        )
                                    } else {
                                        CompositionResultsCard(result = result)
                                    }
                                }
                            }
                        } else {
                            // Single-Column Mobile Stack (HITUNG is docked in bottomBar!)
                            InputParameterCard(
                                uiState = uiState,
                                onAdjustSg = viewModel::adjustSg,
                                onSgTextChanged = viewModel::onSgTextChanged,
                                onSgSliderChanged = viewModel::onSgSliderChanged,
                                onAdjustRhoSedimen = viewModel::adjustRhoSedimen,
                                onRhoSedimenTextChanged = viewModel::onRhoSedimenTextChanged,
                                onSelectPreset = viewModel::selectPreset,
                                onCalculateClick = { handleCalculate(isExpandedLayout = false) },
                                onResetClick = { handleReset(isExpandedLayout = false) },
                                showInlineActionRow = false
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .onGloballyPositioned { coords ->
                                        resultsSectionTopPx = coords.positionInParent().y.toInt()
                                    }
                            ) {
                                val result = uiState.result
                                if (!uiState.hasCalculated || result == null) {
                                    PlaceholderBeforeCalculateCard(
                                        isWideScreen = false,
                                        onCalculateClick = { handleCalculate(isExpandedLayout = false) }
                                    )
                                } else {
                                    CompositionResultsCard(result = result)
                                }
                            }
                        }

                        // Bottom Full-Width Section (Table + Practical Conversion)
                        val result = uiState.result
                        AnimatedVisibility(
                            visible = uiState.hasCalculated && result != null,
                            enter = fadeIn(tween(240)) + expandVertically(tween(240)),
                            exit = fadeOut(tween(180)) + shrinkVertically(tween(180))
                        ) {
                            if (result != null) {
                                Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                                    BalanceTableCard(result = result)
                                    PracticalConversionCard(
                                        result = result,
                                        onCopySummaryClick = ::handleCopyReport
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                // Floating Toast Notification (matching #toastNotif)
                AnimatedVisibility(
                    visible = toastMessage != null,
                    enter = fadeIn(tween(220)) + slideInVertically(tween(220)) { it },
                    exit = fadeOut(tween(220)) + slideOutVertically(tween(220)) { it },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Slate900,
                        border = BorderStroke(1.dp, Slate700),
                        shadowElevation = 10.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = null,
                                tint = Emerald400,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = toastMessage.orEmpty(),
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
