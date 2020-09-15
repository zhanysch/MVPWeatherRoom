package com.example.asynctascretrofit.ui.main

import android.content.pm.PackageManager
import android.location.Location
import com.example.asynctascretrofit.model.Current.CurrentWeather
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.asynctascretrofit.R
import com.example.asynctascretrofit.WeatherApp
import com.example.asynctascretrofit.model.ForecastDays.ForcastModelOne
import com.example.asynctascretrofit.utilites.ConnectionUtils
import com.example.asynctascretrofit.utilites.DateTimeUtils
import com.example.asynctascretrofit.utilites.PermissionUtils
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cloud.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), MainContract.View {

    private var presenter: MainPresenter? = null

    private val adapter = RvAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cloud)
        presenter = MainPresenter()
        presenter?.bind(this)

        formatDate()
        MainSnackbarfirst()
        receycler.adapter = adapter

        if ( PermissionUtils.checkLocationPermission(this) ){
            LoadLocattion()
        }
        presenter?.getSavedData()?.observe(this, androidx.lifecycle.Observer {
            Toast.makeText(this, "${it.isNotEmpty()}" , Toast.LENGTH_LONG).show()
            if (it.isNotEmpty()) {
                val item = it.first()
                adapter.update(item.daily)
            } })

    }

    private fun  LocalGeo(location: Location){
        presenter?.loadOneCall(location,getString(R.string.api_key))
    }



    private fun Local(location: Location){  //!!!  1.555     // 1) для работы coruntine
        presenter?.loadLocal(location,  getString(R.string.api_key))
    }


    private fun formatDate(){   // с помошью этой функции задаем число дня  //(000000000)
        DateSecond.text = DateTimeUtils.getDay()
        Date.text=DateTimeUtils.getMonth()
    }

    private fun showSnackbar(){  /// (№ 1111111111 snackbar)
        Snackbar.make(parentlayout, "нет соединения", Snackbar.LENGTH_INDEFINITE) // parentlayout это id xml верстки cloud layout, иначе snackbar не работает
            .setAction("обновить") {                                       // чтоб работал snackbar необходимо давать id layout
                if (!ConnectionUtils.isNetworkAvialable(this)) {
                    showSnackbar()
                }
            }.show()
    }

    private fun MainSnackbarfirst(){   // (№ 1111111111 snackbar)
        val isHasNetwork  = ConnectionUtils.isNetworkAvialable(this)
        if (!isHasNetwork){
            showSnackbar()
        }
    }


    override fun onRequestPermissionsResult(  // (№4444 location)
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults) // (№4444 location)
        if (requestCode == PermissionUtils.LOCATION_REQUEST_CODE ){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults [1] == PackageManager.PERMISSION_GRANTED)
                LoadLocattion()
        }
    }


    private fun LoadLocattion(){  // (№4444 location)
        val fpc = LocationServices.getFusedLocationProviderClient(applicationContext)  // для геолокации
        //чтоб код получае разрешения на использование геолокации создаем object PermissionUtils в папке utilities
        fpc.lastLocation.addOnSuccessListener {
            if(it != null){
                Local(it)      //!!!  2.555
                LocalGeo(it)
            }
            else {
                Log.d("gdgdgdgfd","dfgdgdfgdgdfg")
            }

        } .addOnFailureListener{

        }
    }


    override fun fillViews(result: CurrentWeather?) {
        runOnUiThread {
            LocationSecond.text = result?.name.toString()
            numberOne.text = getString(R.string._18,result?.main?.temp?.toInt().toString())
            numberThird.text = getString(R.string._18,result?.main?.temp_min?.toInt().toString())
            numberTWo.text = getString(R.string._18,result?.main?.temp_max?.toInt().toString())
            mb.text = getString(R.string._1010mb,result?.main?.pressure.toString())
            Percent.text = getString(R.string._81,result?.main?.humidity)
            PercentSecond.text= getString(R.string._81,result?.clouds?.all)
            Sw.text = getString(R.string.sw_4m_s,result?.wind?.speed?.toInt().toString())

            hour.text = DateTimeUtils.formatDate(result?.sys?.sunrise)
            hourSecond.text=DateTimeUtils.formatDate(result?.sys?.sunset)

            LittleCloud.text=result?.weather?.first()?.description
            val image = result?.weather?.first()?.icon
            Picasso.get().load("http://openweathermap.org/img/w/$image.png").into(cloudmain)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.unbind()
    }

}
