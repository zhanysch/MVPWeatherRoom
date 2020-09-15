package com.example.asynctascretrofit.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.asynctascretrofit.R
import com.example.asynctascretrofit.model.ForecastDays.ForCastDailyFourth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_forcast.view.*

class RvAdapter : RecyclerView.Adapter<RvAdapter.RvVholder>() {
    private val list = arrayListOf<ForCastDailyFourth>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvVholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forcast,  parent,false)
        return RvVholder(view)
    }

    fun update(list: List<ForCastDailyFourth>?) {
        if (list!= null){
            this.list.clear()
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: RvVholder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    class RvVholder(v : View) : RecyclerView.ViewHolder(v){
        fun bind(data: ForCastDailyFourth) {
            itemView.gradOne.text =  itemView.context.getString(R.string._18,data.temp.day.toInt().toString())
            itemView.gradOnepoint.text=itemView.context.getString(R.string._18,data.temp.max.toInt().toString())
            itemView.gradTWopoint.text=itemView.context.getString(R.string._18,data.temp.min.toInt().toString())
           val image = data.weather.first().icon
           Picasso.get().load("http://openweathermap.org/img/w/$image.png").into(itemView.cloudy)
            //http://openweathermap.org/img/w/10d.png

        }
    }
}