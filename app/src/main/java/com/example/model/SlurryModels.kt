package com.example.model

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import kotlin.math.abs
import kotlin.math.roundToLong

data class MaterialPreset(
    val name: String,
    val density: Double
)

data class SlurryCalculationResult(
    val sg: Double,
    val rhoSed: Double,
    val massaSlurry: Double,
    val massaWater: Double,
    val massaSedimen: Double,
    val volSlurry: Double,
    val volWater: Double,
    val volSedimen: Double,
    val rasioMassaCw: Double,
    val rasioVolCv: Double,
    val persenCv: Double,
    val persenVolAir: Double,
    val persenCw: Double,
    val persenMassaAir: Double,
    val volSedM3: Double,
    val volSedLiter: Double,
    val volAirM3: Double,
    val volAirLiter: Double,
    val tonSed: Double,
    val kgSed: Double,
    val tonSlurry: Double
)

data class CalculationOutcome(
    val normalizedSg: Double,
    val normalizedRhoSed: Double,
    val warningMessage: String?,
    val result: SlurryCalculationResult
)

object SlurryCalculator {
    const val RHO_WATER = 1.00
    const val BASIS_VOL_SLURRY = 1000.0
    const val DEFAULT_SG = 1.30
    const val DEFAULT_RHO_SED = 2.65
    const val MIN_RHO_SED = 1.05
    const val MAX_RHO_SED = 5.00
    const val SLIDER_MIN_SG = 1.00
    const val SLIDER_MAX_SG = 2.20

    val materialPresets: List<MaterialPreset> = listOf(
        MaterialPreset("Pasir Silika / Kuarsa", 2.65),
        MaterialPreset("Lempung / Clay OB", 2.60),
        MaterialPreset("Batu Lanau (Siltstone)", 2.55),
        MaterialPreset("Batu Lumpur (Mudstone)", 2.45),
        MaterialPreset("Pasir Lumpur Campuran", 2.20),
        MaterialPreset("Lumpur Topsoil / Humus", 2.10),
        MaterialPreset("Tanah Liat Lunak", 2.00),
        MaterialPreset("Batubara Kotor (Reject)", 1.50),
        MaterialPreset("Batubara Halus (Fine Coal)", 1.40),
        MaterialPreset("Batubara Bersih (Coal)", 1.35),
        MaterialPreset("Bijih Besi / Limonite / Tailings Berat", 3.20)
    )

    private val indonesianSymbols = DecimalFormatSymbols(Locale.forLanguageTag("id-ID")).apply {
        decimalSeparator = ','
        groupingSeparator = '.'
    }

    fun formatIndonesian(value: Double, decimals: Int = 2): String {
        val safeValue = if (value.isNaN() || value.isInfinite()) 0.0 else value
        val pattern = buildString {
            append("#,##0")
            if (decimals > 0) {
                append('.')
                repeat(decimals) { append('0') }
            }
        }
        val formatter = DecimalFormat(pattern, indonesianSymbols)
        // Avoid "-0,00"
        val roundedZero = if (abs(safeValue) < 1e-12) 0.0 else safeValue
        return formatter.format(roundedZero)
    }

    fun formatInputDecimal(value: Double): String {
        return String.format(Locale.US, "%.2f", value)
    }

    fun parseNumericInput(raw: String): Double? {
        val normalized = raw.trim().replace(',', '.')
        return normalized.toDoubleOrNull()
    }

    fun roundToTwoDecimals(value: Double): Double {
        return (value * 100.0).roundToLong() / 100.0
    }

    fun calculate(rawSg: Double?, rawRhoSed: Double?): CalculationOutcome {
        var rhoSed = if (rawRhoSed == null || rawRhoSed.isNaN() || rawRhoSed <= RHO_WATER) {
            DEFAULT_RHO_SED
        } else {
            rawRhoSed
        }

        var warning: String? = null
        var sg: Double
        if (rawSg == null || rawSg.isNaN() || rawSg < RHO_WATER) {
            sg = RHO_WATER
        } else if (rawSg > rhoSed) {
            warning = "SG Slurry (${formatIndonesian(rawSg, 2)}) tidak boleh melebihi Density Material (${formatIndonesian(rhoSed, 2)}). Nilai SG disesuaikan ke ${formatIndonesian(rhoSed, 2)}."
            sg = rhoSed
        } else {
            sg = rawSg
        }

        // Exact balance equations from the web calculator:
        val massaSlurry = BASIS_VOL_SLURRY * sg
        val volWater = BASIS_VOL_SLURRY * ((rhoSed - sg) / (rhoSed - RHO_WATER))
        val volSedimen = BASIS_VOL_SLURRY - volWater

        val massaWater = volWater * RHO_WATER
        val massaSedimen = volSedimen * rhoSed

        val rasioMassaCw = if (massaSlurry > 0.0) massaSedimen / massaSlurry else 0.0
        val rasioVolCv = if (BASIS_VOL_SLURRY > 0.0) volSedimen / BASIS_VOL_SLURRY else 0.0

        val persenCv = rasioVolCv * 100.0
        val persenVolAir = 100.0 - persenCv
        val persenCw = rasioMassaCw * 100.0
        val persenMassaAir = 100.0 - persenCw

        // Practical field conversions per 1 m3 (1,000 Liters) Slurry:
        val volSedM3 = rasioVolCv * 1.0
        val volAirM3 = (1.0 - rasioVolCv) * 1.0
        val tonSed = volSedM3 * rhoSed

        val result = SlurryCalculationResult(
            sg = sg,
            rhoSed = rhoSed,
            massaSlurry = massaSlurry,
            massaWater = massaWater,
            massaSedimen = massaSedimen,
            volSlurry = BASIS_VOL_SLURRY,
            volWater = volWater,
            volSedimen = volSedimen,
            rasioMassaCw = rasioMassaCw,
            rasioVolCv = rasioVolCv,
            persenCv = persenCv,
            persenVolAir = persenVolAir,
            persenCw = persenCw,
            persenMassaAir = persenMassaAir,
            volSedM3 = volSedM3,
            volSedLiter = volSedM3 * 1000.0,
            volAirM3 = volAirM3,
            volAirLiter = volAirM3 * 1000.0,
            tonSed = tonSed,
            kgSed = tonSed * 1000.0,
            tonSlurry = sg
        )

        return CalculationOutcome(
            normalizedSg = sg,
            normalizedRhoSed = rhoSed,
            warningMessage = warning,
            result = result
        )
    }

    fun buildWhatsAppReport(s: SlurryCalculationResult): String {
        return "*HASIL PERHITUNGAN KOMPOSISI SEDIMEN & SG SLURRY*\n" +
            "----------------------------------------\n" +
            "• SG Slurry: *${formatIndonesian(s.sg, 2)}*\n" +
            "• Density Material Padat (ρ): *${formatIndonesian(s.rhoSed, 2)} gr/cm³*\n" +
            "----------------------------------------\n" +
            "*HASIL RASIO KOMPOSISI:*\n" +
            "• *Volume Sedimen : Slurry (Cv) = ${formatIndonesian(s.persenCv, 2)}%* (Air: ${formatIndonesian(100.0 - s.persenCv, 2)}%)\n" +
            "• *Massa Sedimen : Slurry (Cw) = ${formatIndonesian(s.persenCw, 2)}%* (Air: ${formatIndonesian(100.0 - s.persenCw, 2)}%)\n" +
            "----------------------------------------\n" +
            "*RINCIAN PER 1 LITER (1.000 cm³) SLURRY:*\n" +
            "• Slurry: Massa *${formatIndonesian(s.massaSlurry, 2)} gr* | Volume *1.000,00 cm³*\n" +
            "• Water: Massa *${formatIndonesian(s.massaWater, 2)} gr* | Volume *${formatIndonesian(s.volWater, 2)} cm³*\n" +
            "• Sedimen: Massa *${formatIndonesian(s.massaSedimen, 2)} gr* | Volume *${formatIndonesian(s.volSedimen, 2)} cm³*"
    }
}
