package com.example.asynctascretrofit.utilites

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionUtils {
    const val LOCATION_REQUEST_CODE =111

   private val permission = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    fun checkLocationPermission(activity:AppCompatActivity) : Boolean {  // если есть разрешени оно будет выводить диалог разрешено ли геолакац или нет
        if (isCheckedPermission(activity))
            return true
        else {
            ActivityCompat.requestPermissions(activity, permission,LOCATION_REQUEST_CODE)
            return false
        }
    }


    //проверка на разрешение
   private fun isCheckedPermission(context : Context) : Boolean{
        val permissionFine = ContextCompat.checkSelfPermission(
            context,
            permission[0]
        ) == PackageManager.PERMISSION_GRANTED

        val permissionCoarse = ContextCompat.checkSelfPermission(
            context,
            permission[1]
        ) == PackageManager.PERMISSION_GRANTED

        return permissionFine &&  permissionCoarse
    }
}