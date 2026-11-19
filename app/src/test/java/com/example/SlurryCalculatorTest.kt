package com.example

import com.example.model.SlurryCalculator
import com.example.ui.SlurryViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class SlurryCalculatorTest {

    @Test
    fun calculate_sampleValues_matchesExactFormulas() {
        val outcome = SlurryCalculator.calculate(rawSg = 1.24, rawRhoSed = 2.20)
        assertNull(outcome.warningMessage)

        val r = outcome.result
        assertEquals(1.24, r.sg, 1e-6)
        assertEquals(2.20, r.rhoSed, 1e-6)
        assertEquals(1240.0, r.massaSlurry, 1e-6)
        assertEquals(800.0, r.massaWater, 1e-6)
        assertEquals(440.0, r.massaSedimen, 1e-6)
        assertEquals(1000.0, r.volSlurry, 1e-6)
        assertEquals(800.0, r.volWater, 1e-6)
        assertEquals(200.0, r.volSedimen, 1e-6)
        assertEquals(0.20, r.rasioVolCv, 1e-6)
        assertEquals(20.0, r.persenCv, 1e-6)
        assertEquals(80.0, r.persenVolAir, 1e-6)
        assertEquals(440.0 / 1240.0, r.rasioMassaCw, 1e-6)
        assertEquals((440.0 / 1240.0) * 100.0, r.persenCw, 1e-6)
        assertEquals(0.200, r.volSedM3, 1e-6)
        assertEquals(200.0, r.volSedLiter, 1e-6)
        assertEquals(0.800, r.volAirM3, 1e-6)
        assertEquals(800.0, r.volAirLiter, 1e-6)
        assertEquals(0.440, r.tonSed, 1e-6)
        assertEquals(440.0, r.kgSed, 1e-6)
        assertEquals(1.24, r.tonSlurry, 1e-6)
    }

    @Test
    fun calculate_sgExceedsRhoSed_clampsAndWarns() {
        val outcome = SlurryCalculator.calculate(rawSg = 1.80, rawRhoSed = 1.35)
        assertNotNull(outcome.warningMessage)
        assertEquals(1.35, outcome.normalizedSg, 1e-6)
        assertEquals(100.0, outcome.result.persenCv, 1e-6)
        assertEquals(100.0, outcome.result.persenCw, 1e-6)
        assertEquals(0.0, outcome.result.volWater, 1e-6)
    }

    @Test
    fun calculate_sgBelowWater_clampsToOne() {
        val outcome = SlurryCalculator.calculate(rawSg = 0.85, rawRhoSed = 2.65)
        assertNull(outcome.warningMessage)
        assertEquals(1.00, outcome.normalizedSg, 1e-6)
        assertEquals(0.0, outcome.result.persenCv, 1e-6)
        assertEquals(0.0, outcome.result.persenCw, 1e-6)
        assertEquals(1000.0, outcome.result.volWater, 1e-6)
    }

    @Test
    fun formatIndonesian_formatsGroupingAndDecimals() {
        assertEquals("1.240,00", SlurryCalculator.formatIndonesian(1240.0, 2))
        assertEquals("20,00", SlurryCalculator.formatIndonesian(20.0, 2))
        assertEquals("0,3548", SlurryCalculator.formatIndonesian(0.3548387, 4))
        assertEquals("0,440", SlurryCalculator.formatIndonesian(0.44, 3))
    }

    @Test
    fun presets_containsAllElevenMaterials() {
        assertEquals(11, SlurryCalculator.materialPresets.size)
        assertEquals(2.65, SlurryCalculator.materialPresets.first().density, 1e-6)
        assertEquals(3.20, SlurryCalculator.materialPresets.last().density, 1e-6)
    }

    @Test
    fun viewModel_workflow_calculatesAndResetsProperly() {
        val vm = SlurryViewModel()
        assertFalse(vm.uiState.value.hasCalculated)
        assertTrue(vm.uiState.value.needsRecalculation)
        assertTrue(vm.uiState.value.isPresetSelected(2.65))

        vm.selectPreset(2.20)
        assertTrue(vm.uiState.value.isPresetSelected(2.20))

        vm.onSgTextChanged("1.24")
        val result = vm.calculate()
        assertTrue(vm.uiState.value.hasCalculated)
        assertFalse(vm.uiState.value.needsRecalculation)
        assertEquals(20.0, result.persenCv, 1e-6)

        val report = vm.getOrCalculateReport()
        assertTrue(report.contains("Volume Sedimen : Slurry (Cv) = 20,00%"))
        assertTrue(report.contains("Massa Sedimen : Slurry (Cw) = 35,48%"))

        vm.reset()
        assertFalse(vm.uiState.value.hasCalculated)
        assertEquals("1.30", vm.uiState.value.sgInputText)
        assertEquals("2.65", vm.uiState.value.rhoSedimenInputText)
    }
}
