package com.abidzar.bareksaapp.view.fragment

import android.graphics.Color
import android.icu.number.IntegerWidth
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.abidzar.bareksaapp.R
import com.abidzar.bareksaapp.databinding.FragmentMainBinding
import com.abidzar.bareksaapp.model.data.chart.ChartProduct
import com.abidzar.bareksaapp.model.data.chart.Data
import com.abidzar.bareksaapp.model.data.product.DetailProduct
import com.abidzar.bareksaapp.model.data.product.Details
import com.abidzar.bareksaapp.util.PercentValueFormatter
import com.abidzar.bareksaapp.viewmodel.PageViewModel
import com.abidzar.bareksaapp.viewmodel.ProductViewModel
import com.bumptech.glide.Glide
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.android.material.tabs.TabLayout
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * A placeholder fragment containing a simple view.
 */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private lateinit var productViewModel: ProductViewModel
    private var _binding: FragmentMainBinding? = null

    private lateinit var detailProduct: DetailProduct
    private lateinit var chartProduct: ChartProduct


    private lateinit var imageProduct: ImageView
    private lateinit var imageProduct2: ImageView
    private lateinit var imageProduct3: ImageView

    private lateinit var imageDeleteProduct: ImageView
    private lateinit var imageDeleteProduct2: ImageView
    private lateinit var imageDeleteProduct3: ImageView

    private lateinit var tvProductName: TextView
    private lateinit var tvProductName2: TextView
    private lateinit var tvProductName3: TextView

    private lateinit var reksaDanaType: TextView
    private lateinit var reksaDanaType2: TextView
    private lateinit var reksaDanaType3: TextView

    private lateinit var imbalHasil: TextView
    private lateinit var imbalHasil2: TextView
    private lateinit var imbalHasil3: TextView

    private lateinit var danaKelolaan: TextView
    private lateinit var danaKelolaan2: TextView
    private lateinit var danaKelolaan3: TextView

    private lateinit var minBuy: TextView
    private lateinit var minBuy2: TextView
    private lateinit var minBuy3: TextView

    private lateinit var timeExpired: TextView
    private lateinit var timeExpired2: TextView
    private lateinit var timeExpired3: TextView

    private lateinit var risk: TextView
    private lateinit var risk2: TextView
    private lateinit var risk3: TextView

    private lateinit var incetionDate: TextView
    private lateinit var incetionDate2: TextView
    private lateinit var incetionDate3: TextView

    private lateinit var lnrCostumLegend: LinearLayout
    private lateinit var txvLegendProductOne: TextView
    private lateinit var txvLegendProductTwo: TextView
    private lateinit var txvLegendProductThree: TextView
    private lateinit var txvLegendDate: TextView

    val THOUSAND = 1000L
    val MILLION = 1000000L
    val BILLION = 1000000000L
    val TRILLION = 1000000000000L

    val productCodes = mutableListOf("KI002MMCDANCAS00", "TP002EQCEQTCRS00", "NI002EQCDANSIE00")

    private lateinit var chartTabLayout: TabLayout

    private val PRODUCT_COLORS_PRIMARY = arrayOf(
        R.color.product_one_primary,
        R.color.product_two_primary,
        R.color.product_three_primary,
    )

    private val PRODUCT_COLORS_SECONDARY = arrayOf(
        R.color.product_one_secondary,
        R.color.product_two_secondary,
        R.color.product_three_secondary,
    )

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root = binding.root

        initView()
        setUpTabLayout()
        createData()

        chartTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (chartTabLayout.selectedTabPosition) {
                    0 -> setSelectedChartTab("1W")
                    1 -> setSelectedChartTab("1M")
                    2 -> setSelectedChartTab("1Y")
                    3 -> setSelectedChartTab("3Y")
                    4 -> setSelectedChartTab("5Y")
                    5 -> setSelectedChartTab("10Y")
                    6 -> setSelectedChartTab("All")
                    else -> {
                        setSelectedChartTab("1W")
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        imageDeleteProduct.setOnClickListener(View.OnClickListener {
            deleteAllFormProductAndReload(0)
        })

        imageDeleteProduct2.setOnClickListener(View.OnClickListener {
            deleteAllFormProductAndReload(1)
        })

        imageDeleteProduct3.setOnClickListener(View.OnClickListener {
            deleteAllFormProductAndReload(2)
        })

        return root
    }

    private fun deleteAllFormProductAndReload(position: Int) {

        println("MASA AKU BISA KEPANGGIl")

        imageProduct3.setImageResource(R.drawable.ic_baseline_add_circle_24)
        tvProductName3.text = "..."
        reksaDanaType3.text = "..."
        imbalHasil3.text = "..."
        danaKelolaan3.text = "..."
        minBuy3.text = "..."
        timeExpired3.text = "..."
        risk3.text = "..."
        incetionDate3.text = "..."
        imageDeleteProduct3.visibility = View.GONE

        imageProduct2.setImageResource(R.drawable.ic_baseline_add_circle_24)
        tvProductName2.text = "..."
        reksaDanaType2.text = "..."
        imbalHasil2.text = "..."
        danaKelolaan2.text = "..."
        minBuy2.text = "..."
        timeExpired2.text = "..."
        risk2.text = "..."
        incetionDate2.text = "..."
        imageDeleteProduct2.visibility = View.GONE

        imageProduct.setImageResource(R.drawable.ic_baseline_add_circle_24)
        tvProductName.text = "..."
        reksaDanaType.text = "..."
        imbalHasil.text = "..."
        danaKelolaan.text = "..."
        minBuy.text = "..."
        timeExpired.text = "..."
        risk.text = "..."
        incetionDate.text = "..."
        imageDeleteProduct.visibility = View.GONE

        productCodes.removeAt(position)

        createData()
    }

    private fun setSelectedChartTab(s: String) {
        when (s) {
            "1W" -> {
                setChartData(s)
            }
            "1M" -> {
                setChartData(s)
            }
            "1Y" -> {
                setChartData(s)
            }
            "3Y" -> {
                setChartData(s)
            }
            "5Y" -> {
                setChartData(s)
            }
            "10Y" -> {
                setChartData(s)
            }
            "All" -> {
                setChartData(s)
            }
        }
    }

    private fun setUpTabLayout() {
        chartTabLayout.addTab(chartTabLayout.newTab().setText(R.string.week))
        chartTabLayout.addTab(chartTabLayout.newTab().setText(R.string.month))
        chartTabLayout.addTab(chartTabLayout.newTab().setText(R.string.year))
        chartTabLayout.addTab(chartTabLayout.newTab().setText(R.string.three_years))
        chartTabLayout.addTab(chartTabLayout.newTab().setText(R.string.five_years))
        chartTabLayout.addTab(chartTabLayout.newTab().setText(R.string.ten_years))
        chartTabLayout.addTab(chartTabLayout.newTab().setText(R.string.all))
    }

    private fun initView() {
        imageProduct = binding.imageProduct
        imageProduct2 = binding.imageProduct2
        imageProduct3 = binding.imageProduct3

        imageDeleteProduct = binding.imageDeleteProduct
        imageDeleteProduct2 = binding.imageDeleteProduct2
        imageDeleteProduct3 = binding.imageDeleteProduct3

        tvProductName = binding.tvProductName
        tvProductName2 = binding.tvProductName2
        tvProductName3 = binding.tvProductName3

        reksaDanaType = binding.reksaDanaType
        reksaDanaType2 = binding.reksaDanaType2
        reksaDanaType3 = binding.reksaDanaType3

        imbalHasil = binding.imbalHasil
        imbalHasil2 = binding.imbalHasil2
        imbalHasil3 = binding.imbalHasil3

        danaKelolaan = binding.danaKelolaan
        danaKelolaan2 = binding.danaKelolaan2
        danaKelolaan3 = binding.danaKelolaan3

        minBuy = binding.minBuy
        minBuy2 = binding.minBuy2
        minBuy3 = binding.minBuy3

        timeExpired = binding.timeExpired
        timeExpired2 = binding.timeExpired2
        timeExpired3 = binding.timeExpired3

        risk = binding.risk
        risk2 = binding.risk2
        risk3 = binding.risk3

        incetionDate = binding.incetionDate
        incetionDate2 = binding.incetionDate2
        incetionDate3 = binding.incetionDate3

        lnrCostumLegend = binding.lnrCostumLegend
        txvLegendProductOne = binding.txvLegendProductOne
        txvLegendProductTwo = binding.txvLegendProductTwo
        txvLegendProductThree = binding.txvLegendProductThree
        txvLegendDate = binding.txvLegendDate

        chartTabLayout = binding.chartTabLayout
    }

    private fun createData() {

//        val code: MutableList<String> =
//            mutableListOf("KI002MMCDANCAS00", "TP002EQCEQTCRS00", "NI002EQCDANSIE00")
//        val productCodes: List<String> = code

        productViewModel.makeApiCallGetProduct(productCodes)
        productViewModel.makeApiCallChartProduct(productCodes)

        productViewModel.getDetailProductDataObserver()
            .observe(viewLifecycleOwner, Observer<DetailProduct> {
                if (it != null) {
                    println("getDetailProductDataObserver " + it.data.size)
                    detailProduct = it
                    setProductData()
                } else {
                    Toast.makeText(context, "Error getting data from API", Toast.LENGTH_LONG).show()
                }
            })

        productViewModel.getDetailChartDataObserver()
            .observe(viewLifecycleOwner, Observer<ChartProduct> {
                if (it != null) {
                    println("getDetailChartDataObserver " + it.data)
                    chartProduct = it
                    setChartData("1W")
                } else {
                    Toast.makeText(context, "Error getting data from API", Toast.LENGTH_LONG).show()
                }
            })

    }

    private fun setProductData() {


        for (i in detailProduct.data.indices) {


            val name = detailProduct.data[i].name
            val details: Details = detailProduct.data[i].details
            val format = DecimalFormat("#,###")

            val imageUrl = details.im_avatar
            val type = details.type
            val returnFiveYear = details.return_five_year
            val imbal = "$returnFiveYear %"
            val totalUnit = appendMillions(details.total_unit.toLong())
            val minimialBuy = appendMillions(details.min_subscription.toLong())

            val date = details.inception_date

            val format1: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val dt1: Date = format1.parse(date)

            val format2: SimpleDateFormat = SimpleDateFormat("dd MMM yyyy")
            val incepDate: String = format2.format(dt1)

            when (i) {
                2 -> {
                    Glide.with(this)
                        .load(imageUrl)
                        .into(imageProduct3)

                    tvProductName3.text = name

                    reksaDanaType3.text = type
                    imbalHasil3.text = imbal
                    danaKelolaan3.text = totalUnit
                    minBuy3.text = minimialBuy
                    timeExpired3.text = "-"
                    risk3.text = "-"
                    incetionDate3.text = incepDate
                    imageDeleteProduct3.visibility = View.VISIBLE
                }
                1 -> {
                    Glide.with(this)
                        .load(imageUrl)
                        .into(imageProduct2)

                    tvProductName2.text = name

                    reksaDanaType2.text = type
                    imbalHasil2.text = imbal
                    danaKelolaan2.text = totalUnit
                    minBuy2.text = minimialBuy
                    timeExpired2.text = "-"
                    risk2.text = "-"
                    incetionDate2.text = incepDate
                    imageDeleteProduct2.visibility = View.VISIBLE

                }
                0 -> {
                    Glide.with(this)
                        .load(imageUrl)
                        .into(imageProduct)

                    tvProductName.text = name

                    reksaDanaType.text = type
                    imbalHasil.text = imbal
                    danaKelolaan.text = totalUnit
                    minBuy.text = minimialBuy
                    timeExpired.text = "-"
                    risk.text = "-"
                    incetionDate.text = incepDate
                    imageDeleteProduct.visibility = View.VISIBLE

                }
            }

        }

    }

    private fun setChartData(type: String) {
        val lineChart: LineChart = binding.lineChart

        val iLineDataSet: ArrayList<ILineDataSet> = ArrayList()

        val valueDateLegend: ArrayList<String> = ArrayList();

        var position = 0

        for (dataChart in chartProduct.data) {

            val charts: ArrayList<Entry> = ArrayList();

            val valueDate: ArrayList<String> = ArrayList();

            val listData: List<Data> = dataChart.value.data

            println("productCodeKey " + dataChart.key)

            println("chartcountdata " + listData.size)

            for (i in listData.indices) {

                val date = listData[i].date

                val format1: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
                val dt1: Date = format1.parse(date)

                val format2: SimpleDateFormat = SimpleDateFormat("MMM yy")
                val dt2: String = format2.format(dt1)

                val format3: SimpleDateFormat = SimpleDateFormat("dd MMM yyyy")
                val dtLegend: String = format3.format(dt1)

                charts.add(Entry(i.toFloat(), listData[i].growth.toFloat()))
                valueDate.add(dt2)
                valueDateLegend.add(dtLegend)

            }

            val xAxis: XAxis = lineChart.xAxis
            xAxis.valueFormatter = IndexAxisValueFormatter(valueDate)
            xAxis.setLabelCount(5, false)

            val yAxis: YAxis = lineChart.axisRight
            yAxis.valueFormatter = PercentValueFormatter()

            val lineDataSet: LineDataSet = LineDataSet(charts, dataChart.key)
            lineDataSet.valueTextColor = Color.BLACK
            lineDataSet.valueTextSize = 12f
            lineDataSet.valueFormatter = DefaultValueFormatter(0)
            lineDataSet.setDrawValues(false)

            lineDataSet.color = resources.getColor(PRODUCT_COLORS_PRIMARY[position])
            lineDataSet.setCircleColor(resources.getColor(PRODUCT_COLORS_PRIMARY[position]))
            lineDataSet.circleHoleColor = resources.getColor(PRODUCT_COLORS_SECONDARY[position])

            if (type == "1W") {
                lineDataSet.lineWidth = 3f
                lineDataSet.circleRadius = 8f
                val circleHoleRadius: Float = lineDataSet.circleRadius / 2
                lineDataSet.circleHoleRadius = circleHoleRadius / 2
                lineDataSet.setDrawCircleHole(true)

                lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
            } else if (type == "1M") {
                lineDataSet.lineWidth = 2f
                lineDataSet.setDrawCircleHole(true)

                lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
            } else {
                lineDataSet.lineWidth = 1f
                lineDataSet.setDrawCircles(false)
            }

            iLineDataSet.add(lineDataSet)

            position++

        }

        val lineData = LineData(iLineDataSet)

        lineChart.data = lineData
        lineChart.animateXY(100, 500)
        lineChart.setDrawGridBackground(false)

        lineChart.axisRight.setDrawGridLines(true)
        lineChart.axisRight.spaceMin = 0.5f
        lineChart.axisRight.setDrawAxisLine(false)
        lineChart.axisLeft.setDrawGridLines(false)
        lineChart.xAxis.setDrawGridLines(false)
        lineChart.description.isEnabled = false
        lineChart.axisRight.isEnabled = true
        lineChart.setTouchEnabled(true)
        lineChart.axisLeft.isEnabled = false
        lineChart.legend.isEnabled = true
        lineChart.setScaleEnabled(false)

        val xl = lineChart.xAxis
        xl.position = XAxis.XAxisPosition.BOTTOM

        val legend = lineChart.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        legend.form = Legend.LegendForm.CIRCLE
        legend.xEntrySpace = 8f
        legend.formToTextSpace = 4f
        legend.isEnabled = false

        var selectedScale: Float = 0f
        // just as an example, could be any amount of days
        val xvalcount = lineChart.xChartMax

        lineChart.zoom(0f, 1f, 0f, 0f)

        when (type) {
            "1W" -> {
                // for weeks
                val scaleXweeks = xvalcount / 5f
                selectedScale = scaleXweeks
            }
            "1M" -> {
                // for months
                val scaleXmonths = xvalcount / 22f // assuming 22 days per month
                selectedScale = scaleXmonths
            }
            "1Y" -> {
                val scaleXyears = xvalcount / 242f // assuming 242 days per year
                selectedScale = scaleXyears
            }
            "3Y" -> {
                val scaleX3years = xvalcount / 726f // assuming 242 days per year
                selectedScale = scaleX3years
            }
            "5Y" -> {
                val scaleX5years = xvalcount / 1210f // assuming 242 days per year
                selectedScale = scaleX5years
            }
            "10Y" -> {
                val scaleX10years = xvalcount / 2420f // assuming 242 days per year
                selectedScale = scaleX10years
            }
            "All" -> {
                val scaleXAll = xvalcount / xvalcount // assuming 242 days per year
                selectedScale = scaleXAll
            }
        }



        lineChart.zoom(selectedScale, 1f, 0f, 0f)

        lineChart.moveViewToX(lineChart.xChartMax)
        //lineChart.setVisibleXRangeMaximum(242f)
        var dataSet: ILineDataSet
        var valueList: ArrayList<String> = ArrayList()

        //set default legend value get max of xAxix data
        for (i in iLineDataSet.indices) {
            dataSet = iLineDataSet[i]

            val entry: Entry = dataSet.getEntryForIndex(lineChart.xAxis.axisMaximum.toInt())
            valueList.add(entry.y.toString())

        }
        val legendDate = valueDateLegend[lineChart.xAxis.axisMaximum.toInt()]

        setCostumLegends(valueList[0], valueList[1], valueList[2], legendDate)


        lineChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                val x = e?.x.toString()
                val y = e?.y

                val selectedXAxisCount =
                    x.substringBefore(".") //this value is float so use substringbefore method
                // another method shown below
                val nonFloat =
                    e?.let { lineChart.getXAxis().getValueFormatter().getFormattedValue(it.x) }
                //if you are display any string in x axis you will get this

                println("XX $x")
                println("YY $y")
                println("selectedXAxisCount $selectedXAxisCount")
                println("nonFloat $nonFloat")

                val size = iLineDataSet.size
                var dataSet: ILineDataSet
                var valueList: ArrayList<String> = ArrayList()

                for (i in iLineDataSet.indices) {
                    dataSet = iLineDataSet[i]

                    val entry: Entry = dataSet.getEntryForIndex(e!!.x.toInt())
                    valueList.add(entry.y.toString())

                }

                setCostumLegends(
                    valueList[0],
                    valueList[1],
                    valueList[2],
                    valueDateLegend[e!!.x.toInt()]
                )


            }

            override fun onNothingSelected() {

            }
        })

    }

    fun appendMillions(x: Long): String? {
        return when {
            x < THOUSAND -> x.toString()
            x < MILLION -> "${x.times(100).div(THOUSAND).times(0.01)} Ribu"
            x < BILLION -> "${x.times(100).div(MILLION).times(0.01)} Juta"
            x < TRILLION -> "${x.times(100).div(BILLION).times(0.01)} Miliar"
            else -> "${x.times(100).div(TRILLION).times(0.01)} Triliun"
        }
    }

    private fun setLegends(value1: String, value2: String, value3: String) {
        val l: Legend = binding.lineChart.legend

        l.entries

        l.isWordWrapEnabled = true

        val l1: LegendEntry = LegendEntry(
            value1,
            Legend.LegendForm.CIRCLE,
            12f,
            2f,
            null,
            ResourcesCompat.getColor(resources, PRODUCT_COLORS_PRIMARY[0], null)
        )
        val l2: LegendEntry = LegendEntry(
            value2,
            Legend.LegendForm.CIRCLE,
            12f,
            2f,
            null,
            ResourcesCompat.getColor(resources, PRODUCT_COLORS_PRIMARY[1], null)
        )
        val l3: LegendEntry = LegendEntry(
            value3,
            Legend.LegendForm.CIRCLE,
            12f,
            2f,
            null,
            ResourcesCompat.getColor(resources, PRODUCT_COLORS_PRIMARY[2], null)
        )

        l.setCustom(arrayOf(l1, l2, l3))
        l.isWordWrapEnabled = true

        l.isEnabled = true
    }

    private fun setCostumLegends(value1: String, value2: String, value3: String, date: String) {

        txvLegendProductOne.text = "$value1%"
        txvLegendProductTwo.text = "$value2%"
        txvLegendProductThree.text = "$value3%"

        txvLegendDate.text = "($date)"

    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}