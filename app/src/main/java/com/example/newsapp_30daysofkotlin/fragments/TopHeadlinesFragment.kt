package com.example.newsapp_30daysofkotlin.fragments

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.newsapp_30daysofkotlin.data.NewsData
import com.example.newsapp_30daysofkotlin.adapter.THListAdapter
import com.example.newsapp_30daysofkotlin.R
import com.example.newsapp_30daysofkotlin.databinding.FragmentTopheadlinesBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry


class TopHeadlinesFragment : Fragment() {
    private lateinit var binding: FragmentTopheadlinesBinding
    private lateinit var piechart: PieChart
    var tc: Int = 0;
    var tr: Int = 0;
    var td: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_topheadlines, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = ArrayList<NewsData>()
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        val url =
            "https://newsapi.org/v2/top-headlines?country=in&apiKey=17ae04f50bfd4413811aeb69e0a062f9"
        val json = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                val jsonArray = response.getJSONArray("articles")
                for (i in 0 until jsonArray.length()) {
                    val accArr = jsonArray.getJSONObject(i)
                    val im = accArr.getString("urlToImage")
                    val ti = accArr.getString("title")
                    val pt = accArr.getString("publishedAt")
                    val nl = accArr.getString("url")
                    Log.d("fetch_success", "ti is $ti and pt is $pt")
                    var titleSS: String;
                    val final_time: String;
                    val hour: String;
                    val min: String;
                    val day: String;
                    val month: String;
                    val year: String
                    hour = pt.substring(11, 13); min = pt.substring(14, 16); day =
                        pt.substring(8, 10)
                    month = pt.substring(5, 7); year = pt.substring(0, 4);
                    var day_dec = "";
                    var month_dec = ""
                    when (day.toInt()) {
                        1, 21, 31 -> day_dec = day.toString() + "st, "
                        2, 22 -> day_dec = day.toString() + "nd, "
                        3, 23 -> day_dec = day.toString() + "rd, "
                        else -> day_dec = day.toString() + "th, "
                    }
                    when (month.toInt()) {
                        1 -> month_dec = "January"
                        2 -> month_dec = "February"
                        3 -> month_dec = "March"
                        4 -> month_dec = "April"
                        5 -> month_dec = "May"
                        6 -> month_dec = "June"
                        7 -> month_dec = "July"
                        8 -> month_dec = "August"
                        9 -> month_dec = "September"
                        10 -> month_dec = "October"
                        11 -> month_dec = "November"
                        12 -> month_dec = "December"
                    }
                    final_time = hour + ":" + min + "hrs" + " " + month_dec + " " + day_dec + year
                    if (ti.length > 70) {
                        titleSS = ti.substring(0, 70)
                        titleSS += "..."
                    } else titleSS = ti
                    list.add(
                        NewsData(
                            im,
                            titleSS,
                            final_time,
                            nl
                        )
                    )
                }
                binding.recyclerView.adapter = THListAdapter(requireContext(), list)
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.recyclerView.setHasFixedSize(true)
            }, Response.ErrorListener {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
            })
        queue.add(json)

        // Pie Data Network Request Code
        val pieQueue: RequestQueue = Volley.newRequestQueue(requireContext())
        val pieUrl = "https://api.covid19api.com/summary"
        val piejson = JsonObjectRequest(Request.Method.GET, pieUrl, null,
            Response.Listener { response ->
                val piejsonarray = response.getJSONArray("Countries")
                for (i in 0 until piejsonarray.length()) {
                    val accArr = piejsonarray.getJSONObject(i)
                    val cnt = accArr.getString("Country")
                    val tmp_tc = accArr.getString("TotalConfirmed")
                    val tmp_td = accArr.getString("TotalDeaths")
                    val tmp_tr = accArr.getString("TotalRecovered")
                    if (cnt == "India") {
                        tc = tmp_tc.toInt(); tr = tmp_tr.toInt(); td = tmp_td.toInt()
                        break
                    }
                    Log.d("fpd", "Pie data fetch successful")
                }
            }, Response.ErrorListener { response ->
                Log.d("ffpd", "Couldn't fetch pie data")
            })

        pieQueue.add(piejson)

        val r = Runnable {
            drawChartData()
            binding.tcValue.text = tc.toString()
            binding.tdValue.text = td.toString()
            binding.trValue.text = tr.toString()
        }
        Handler().postDelayed(r, 5000)

    }

    private fun drawChartData() {
        piechart = binding.pieChart
        piechart.setUsePercentValues(true)
        piechart.description.isEnabled = false
        piechart.setExtraOffsets(5f, 10f, 5f, 5f)
        piechart.dragDecelerationFrictionCoef = 0.95f
        piechart.isDrawHoleEnabled = true
        piechart.setHoleColor(Color.WHITE)
        piechart.transparentCircleRadius = 61f
        piechart.setEntryLabelColor(R.color.color5)
        val yValues: ArrayList<PieEntry> = ArrayList()
        val listColors = ArrayList<Int>()
        yValues.add(PieEntry(tr.toFloat(), "Recovered"))
        listColors.add(ContextCompat.getColor(requireContext(), R.color.color1))
        yValues.add(PieEntry(td.toFloat(), "Died"))
        listColors.add(ContextCompat.getColor(requireContext(), R.color.color2))
        yValues.add(PieEntry(tc.toFloat() - td.toFloat() - tr.toFloat(), "Active"))
        listColors.add(ContextCompat.getColor(requireContext(), R.color.color3))
        val dataSet = PieDataSet(yValues, "")
        dataSet.colors = listColors
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        val data = PieData(dataSet)
        data.setValueTextSize(10f)
        data.setValueTextColor(R.color.color4)
        piechart.invalidate()
        piechart.data = data
    }
}
