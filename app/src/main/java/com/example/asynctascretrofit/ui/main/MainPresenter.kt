package com.example.asynctascretrofit.ui.main

import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.example.asynctascretrofit.WeatherApp
import com.example.asynctascretrofit.model.ForecastDays.ForcastModelOne
import com.example.asynctascretrofit.model.data.RetrofitBuilder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainPresenter : MainContract.Presenter {

    private var view: MainContract.View? = null
   private var service = RetrofitBuilder.getService()

    override fun loadLocal(location: Location, key: String) {
        GlobalScope.launch {
            kotlin.runCatching {
                val result = service
                    ?.getWeatherbycoordianatesCoruntines(location.latitude.toString(),
                        location.longitude.toString(), key, "metric")
                view?.fillViews(result)

                Log.d("dgdgfdg","dgdgdfgd")
            }.onFailure {
                Log.d("dgdgfdg","dgdgdfgd")
            }
        }
    }

    override fun loadOneCall(location: Location, key: String) {
        GlobalScope.launch {
            kotlin.runCatching {
                val res = RetrofitBuilder.getService()
                    ?.onecallGeo( location.latitude.toString(),
                        location.longitude.toString(),
                        "hourly,current,minutely",
                        key,
                        "metric")
                updateDB(res)
                Log.d("blabla", "blabla")
            }.onFailure {
                Log.d("blabla", "blabla")
            }
        }

    }

    override fun getSavedData(): LiveData<List<ForcastModelOne>>? {
      return WeatherApp.getApp()?.getDB()?.getDao()?.getAll()
    }

    private fun updateDB(res: ForcastModelOne?) {
        res?.let {
                WeatherApp.getApp()?.getDB()?.getDao()?.addForcast(it)
                Log.d("blabla", "blabla")
        }
    }

    override fun bind(view: MainContract.View) {
        this.view = view
    }

    override fun unbind() {
        this.view = null
    }
}