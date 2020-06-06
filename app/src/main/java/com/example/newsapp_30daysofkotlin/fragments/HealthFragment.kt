package com.example.newsapp_30daysofkotlin.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.newsapp_30daysofkotlin.data.NewsData
import com.example.newsapp_30daysofkotlin.R
import com.example.newsapp_30daysofkotlin.adapter.HLListAdapter
import com.example.newsapp_30daysofkotlin.databinding.FragmentHealthBinding


class HealthFragment : Fragment() {
    private lateinit var binding: FragmentHealthBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_health, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = ArrayList<NewsData>()
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        val url =
            "https://newsapi.org/v2/top-headlines?country=in&category=health&apiKey=17ae04f50bfd4413811aeb69e0a062f9"
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
                binding.recyclerView.adapter = HLListAdapter(requireContext(), list)
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.recyclerView.setHasFixedSize(true)
            }, Response.ErrorListener {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
            })
        queue.add(json)
    }
}
