package com.example.ui

import androidx.lifecycle.ViewModel
import com.example.model.SlurryCalculationResult
import com.example.model.SlurryCalculator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.abs

data class SlurryUiState(
    val sgInputText: String = SlurryCalculator.formatInputDecimal(SlurryCalculator.DEFAULT_SG),
    val sgSliderValue: Float = SlurryCalculator.DEFAULT_SG.toFloat(),
    val rhoSedimenInputText: String = SlurryCalculator.formatInputDecimal(SlurryCalculator.DEFAULT_RHO_SED),
    val hasCalculated: Boolean = false,
    val needsRecalculation: Boolean = true,
    val warningMessage: String? = null,
    val result: SlurryCalculationResult? = null
) {
    val parsedRhoSedimen: Double?
        get() = SlurryCalculator.parseNumericInput(rhoSedimenInputText)

    fun isPresetSelected(density: Double): Boolean {
        val current = parsedRhoSedimen ?: return false
        return abs(current - density) < 0.001
    }
}

class SlurryViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SlurryUiState())
    val uiState: StateFlow<SlurryUiState> = _uiState.asStateFlow()

    fun adjustSg(delta: Double) {
        _uiState.update { state ->
            val rhoS = (SlurryCalculator.parseNumericInput(state.rhoSedimenInputText)
                ?: SlurryCalculator.DEFAULT_RHO_SED)
                .coerceAtLeast(SlurryCalculator.RHO_WATER)
            val current = SlurryCalculator.parseNumericInput(state.sgInputText) ?: SlurryCalculator.RHO_WATER
            val next = SlurryCalculator.roundToTwoDecimals(current + delta)
                .coerceIn(SlurryCalculator.RHO_WATER, rhoS)
            state.copy(
                sgInputText = SlurryCalculator.formatInputDecimal(next),
                sgSliderValue = next.toFloat().coerceIn(
                    SlurryCalculator.SLIDER_MIN_SG.toFloat(),
                    SlurryCalculator.SLIDER_MAX_SG.toFloat()
                ),
                hasCalculated = false,
                needsRecalculation = true
            )
        }
    }

    fun onSgTextChanged(newText: String) {
        val sanitized = sanitizeNumericField(newText)
        _uiState.update { state ->
            val parsed = SlurryCalculator.parseNumericInput(sanitized)
            val sliderVal = if (parsed != null && !parsed.isNaN()) {
                parsed.toFloat().coerceIn(
                    SlurryCalculator.SLIDER_MIN_SG.toFloat(),
                    SlurryCalculator.SLIDER_MAX_SG.toFloat()
                )
            } else {
                SlurryCalculator.SLIDER_MIN_SG.toFloat()
            }
            state.copy(
                sgInputText = sanitized,
                sgSliderValue = sliderVal,
                hasCalculated = false,
                needsRecalculation = true
            )
        }
    }

    fun onSgSliderChanged(value: Float) {
        val rounded = SlurryCalculator.roundToTwoDecimals(value.toDouble())
        _uiState.update { state ->
            state.copy(
                sgSliderValue = rounded.toFloat().coerceIn(
                    SlurryCalculator.SLIDER_MIN_SG.toFloat(),
                    SlurryCalculator.SLIDER_MAX_SG.toFloat()
                ),
                sgInputText = SlurryCalculator.formatInputDecimal(rounded),
                hasCalculated = false,
                needsRecalculation = true
            )
        }
    }

    fun adjustRhoSedimen(delta: Double) {
        _uiState.update { state ->
            val current = SlurryCalculator.parseNumericInput(state.rhoSedimenInputText)
                ?: SlurryCalculator.DEFAULT_RHO_SED
            val next = SlurryCalculator.roundToTwoDecimals(current + delta)
                .coerceIn(SlurryCalculator.MIN_RHO_SED, SlurryCalculator.MAX_RHO_SED)
            state.copy(
                rhoSedimenInputText = SlurryCalculator.formatInputDecimal(next),
                hasCalculated = false,
                needsRecalculation = true
            )
        }
    }

    fun onRhoSedimenTextChanged(newText: String) {
        val sanitized = sanitizeNumericField(newText)
        _uiState.update { state ->
            state.copy(
                rhoSedimenInputText = sanitized,
                hasCalculated = false,
                needsRecalculation = true
            )
        }
    }

    fun selectPreset(density: Double) {
        _uiState.update { state ->
            state.copy(
                rhoSedimenInputText = SlurryCalculator.formatInputDecimal(density),
                hasCalculated = false,
                needsRecalculation = true
            )
        }
    }

    fun calculate(): SlurryCalculationResult {
        val currentState = _uiState.value
        val outcome = SlurryCalculator.calculate(
            rawSg = SlurryCalculator.parseNumericInput(currentState.sgInputText),
            rawRhoSed = SlurryCalculator.parseNumericInput(currentState.rhoSedimenInputText)
        )
        _uiState.update {
            it.copy(
                sgInputText = SlurryCalculator.formatInputDecimal(outcome.normalizedSg),
                sgSliderValue = outcome.normalizedSg.toFloat().coerceIn(
                    SlurryCalculator.SLIDER_MIN_SG.toFloat(),
                    SlurryCalculator.SLIDER_MAX_SG.toFloat()
                ),
                rhoSedimenInputText = SlurryCalculator.formatInputDecimal(outcome.normalizedRhoSed),
                hasCalculated = true,
                needsRecalculation = false,
                warningMessage = outcome.warningMessage,
                result = outcome.result
            )
        }
        return outcome.result
    }

    fun reset() {
        _uiState.value = SlurryUiState()
    }

    fun getOrCalculateReport(): String {
        val currentResult = _uiState.value.result
        val finalResult = if (_uiState.value.hasCalculated && currentResult != null) {
            currentResult
        } else {
            calculate()
        }
        return SlurryCalculator.buildWhatsAppReport(finalResult)
    }

    private fun sanitizeNumericField(input: String): String {
        val sb = StringBuilder()
        var hasSeparator = false
        for (ch in input) {
            when {
                ch.isDigit() -> sb.append(ch)
                (ch == '.' || ch == ',') && !hasSeparator -> {
                    sb.append(ch)
                    hasSeparator = true
                }
            }
        }
        return sb.toString()
    }
}
