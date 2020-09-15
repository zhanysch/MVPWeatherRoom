package com.example.asynctascretrofit.ui.main

import android.location.Location
import androidx.lifecycle.LiveData
import com.example.asynctascretrofit.model.Current.CurrentWeather
import com.example.asynctascretrofit.model.ForecastDays.ForcastModelOne
import com.example.asynctascretrofit.ui.LiveCycle

interface MainContract {
    interface View{
        fun fillViews(result: CurrentWeather?)

    }
    interface Presenter : LiveCycle<View>{
        fun loadLocal(location: Location, string: String)
        fun loadOneCall(location: Location, string: String)
        fun getSavedData() :  LiveData<List<ForcastModelOne>>?

    }
}