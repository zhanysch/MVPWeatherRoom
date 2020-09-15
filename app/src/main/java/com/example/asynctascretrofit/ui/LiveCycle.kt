package com.example.asynctascretrofit.ui

interface LiveCycle<V> {
    fun bind(view:V)
    fun unbind()
}