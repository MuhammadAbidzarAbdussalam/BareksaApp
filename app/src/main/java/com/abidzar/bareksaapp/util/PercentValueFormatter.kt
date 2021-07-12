package com.abidzar.bareksaapp.util

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DecimalFormat
import java.text.NumberFormat
import kotlin.math.roundToInt

class PercentValueFormatter : ValueFormatter() {

    private val format = DecimalFormat("#%")

    override fun getPointLabel(entry: Entry?): String {
        println("entry?.y " + entry?.y)
        return format.format(entry?.y?.roundToInt())
    }

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        println("getAxisLabel " + format.format(value))
        println("getValue ${value.roundToInt()}")
        return value.roundToInt().toString() + "%"
    }

}