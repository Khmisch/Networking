package com.example.networking.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_advanced_kotlin.activity.network.retrofit.RetrofitHttp
import com.example.networking.network.volley.VolleyHttp
import com.example.networking.R
import com.example.networking.adapter.PosterAdapter
import com.example.networking.model.Poster
import com.example.networking.model.PosterResp
import com.example.networking.network.volley.VolleyHandler
import com.example.networking.utils.Logger
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var b_floating:FloatingActionButton
    lateinit var recyclerView:RecyclerView
    lateinit var progressBar:ProgressBar
    var posters = ArrayList<Poster>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        b_floating = findViewById(R.id.b_floating)
        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setLayoutManager(GridLayoutManager(this,1))
        apiPosterList()
//        workWithRetrofit()
    }


    private fun refreshAdapter(poster:ArrayList<Poster>) {
        val adapter = PosterAdapter(this, poster)
        recyclerView.adapter = adapter

    }

    fun dialogPoster(poster: Poster?) {
        AlertDialog.Builder(this)
            .setTitle("Delete Poster")
            .setMessage("Are you sure you want to delete this poster?")
            .setPositiveButton("Yes"){
                dialog, which -> apiPosterDelete(poster!!)
            }
            .setNegativeButton("No",null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()

    }

    private fun apiPosterList() {
        progressBar.visibility = View.VISIBLE
        VolleyHttp.get(VolleyHttp.API_LIST_POST, VolleyHttp.paramsEmpty(),object : VolleyHandler {
            override fun onSuccess(response: String?) {
                val postArray = Gson().fromJson(response, Array<Poster>::class.java)
                posters.clear()
                posters.addAll(postArray)
                refreshAdapter(posters)
                Log.d("@@@VolleyHttp",response!!)
                progressBar.visibility = View.GONE
            }
            override fun onError(error: String?) {
                Log.e("VolleyHttp",error!!)
            }
        })
    }

    private fun apiPosterDelete(poster: Poster) {
        progressBar.visibility = View.VISIBLE

        VolleyHttp.del(VolleyHttp.API_DELETE_POST + poster.id,object : VolleyHandler {
            override fun onSuccess(response: String?) {
                Log.d("@@@VolleyHttp",response!!)
                apiPosterList()
            }
            override fun onError(error: String?) {
                Log.e("VolleyHttp",error!!)
            }
        })
    }




    fun workWithVolley(){
        VolleyHttp.get(VolleyHttp.API_LIST_POST, VolleyHttp.paramsEmpty(),object : VolleyHandler {
            override fun onSuccess(response: String?) {
                Logger.d("VolleyHttp",response!!)

            }

            override fun onError(error: String?) {
                Logger.e("VolleyHttp",error!!)
            }
        })

        val poster = Poster(1, 1, "PDP", "Online")
        VolleyHttp.post(VolleyHttp.API_CREATE_POST, VolleyHttp.paramsCreate(poster),object :
            VolleyHandler {
            override fun onSuccess(response: String?) {
                Logger.d("@@@",response!!)
            }

            override fun onError(error: String?) {
                Logger.d("@@@",error!!)
            }
        })

        VolleyHttp.put(VolleyHttp.API_UPDATE_POST + poster.id, VolleyHttp.paramsUpdate(poster),object :
            VolleyHandler {
            override fun onSuccess(response: String?) {
                Log.d("@@@",response!!)
            }

            override fun onError(error: String?) {
                Log.d("@@@",error!!)
            }
        })

        VolleyHttp.del(VolleyHttp.API_DELETE_POST + poster.id,object : VolleyHandler {
            override fun onSuccess(response: String?) {
                Log.d("@@@",response!!)
            }

            override fun onError(error: String?) {
                Log.d("@@@",error!!)
            }
        })
    }

    fun workWithRetrofit(){
        RetrofitHttp.posterService.listPost().enqueue(object :
            retrofit2.Callback<ArrayList<PosterResp>> {
            override fun onResponse(call: Call<ArrayList<PosterResp>>, response: Response<ArrayList<PosterResp>>) {
                Log.d("@@@", response.body().toString())

            }

            override fun onFailure(call: Call<ArrayList<PosterResp>>, t: Throwable) {
                Log.d("@@@", t.message.toString())
            }
        })


        val poster = Poster(1, 1, "PDP", "Online")

        RetrofitHttp.posterService.createPost(poster).enqueue(object :
            retrofit2.Callback<PosterResp> {
            override fun onResponse(call: Call<PosterResp>, response: Response<PosterResp>) {
                Log.d("@@@", response.body().toString())
            }

            override fun onFailure(call: Call<PosterResp>, t: Throwable) {
                Log.d("@@@", t.message.toString())
            }
        })

        RetrofitHttp.posterService.updatePost(poster.id, poster).enqueue(object :
            retrofit2.Callback<PosterResp> {
            override fun onResponse(call: Call<PosterResp>, response: Response<PosterResp>) {
                Log.d("@@@", response.body().toString())
            }

            override fun onFailure(call: Call<PosterResp>, t: Throwable) {
                Log.d("@@@", t.message.toString())
            }
        })

        RetrofitHttp.posterService.deletePost(poster.id).enqueue(object :
            retrofit2.Callback<PosterResp> {
            override fun onResponse(call: Call<PosterResp>, response: Response<PosterResp>) {
                Log.d("@@@", "" + response.body())
            }

            override fun onFailure(call: Call<PosterResp>, t: Throwable) {
                Log.d("@@@", "" + t.message)
            }
        })
    }
}