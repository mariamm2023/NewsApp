package com.example.newsapp

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.API.APImanager
import com.example.newsapp.model.NewsResponse
import com.example.newsapp.model.SourceResponse
import com.example.newsapp.model.SourcesItem
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initvies()
        getnewssources()
    }
    val adapter=NewsAdapter(null)
    fun  initvies() {
        tabLayout = findViewById(R.id.tab_layout)
        progressBar = findViewById(R.id.progrwssbar)
        recyclerView=findViewById(R.id.recyclerview)
        recyclerView.adapter=adapter
    }
    private fun getnewssources() {
        APImanager.getAPI().getsource(constance.apikey).enqueue(object : Callback<SourceResponse> {
            override fun onResponse(
                call: Call<SourceResponse>,
                response: Response<SourceResponse>
            ) {
                progressBar.isVisible = false
                addresourcestotablayout(response.body()?.sources)

            }

            override fun onFailure(call: Call<SourceResponse>, t: Throwable) {
                Log.e("error", t.localizedMessage)
            }

        })
    }
    fun addresourcestotablayout(sources: List<SourcesItem?>?) {
        sources?.forEach { source ->
            val tab = tabLayout.newTab()
            tab.setText(source?.name)
            tab.tag = source
            tabLayout.addTab(tab)

        }



    tabLayout.addOnTabSelectedListener(
    //حطلي ليسنر عند التاب علشان لما ادوس تكول api تانيه
    object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            val source = tab?.tag as? SourcesItem
            if (source != null) {
                getnewsbysource(source)
            } else {
                Log.e("MainActivity", "Source is null")
            }

           // getnewsbysource(source)
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
            Log.e("MainActivity", "unselected")
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {
            val source = tab?.tag as? SourcesItem
            if (source != null) {
                getnewsbysource(source)
            } else {
                Log.e("MainActivity", "Source is null")
            }        }

    }
    )
        tabLayout.getTabAt(0)?.select()
        }
     fun getnewsbysource(source: SourcesItem) {
         progressBar.isVisible=true
         APImanager.getAPI().getnews(constance.apikey,source.id?:" ").enqueue(object:Callback<NewsResponse>{
             override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                 progressBar.isVisible=false
                 adapter.changedata(response.body()?.articles)

             }
             override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                 progressBar.isVisible = false
                 Log.e("MainActivity", "Error fetching news: ${t.localizedMessage}")
             }
         })
    }
}