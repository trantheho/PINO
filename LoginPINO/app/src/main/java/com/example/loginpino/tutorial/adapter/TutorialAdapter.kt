package com.example.loginpino.tutorial.adapter

import android.content.Context
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.loginpino.R
import com.example.loginpino.tutorial.TutorialItem
import kotlinx.android.synthetic.main.row_tutorial.view.*

class TutorialAdapter(val items : List<TutorialItem>) : RecyclerView.Adapter<TutorialAdapter.ViewHolder>() {

    private lateinit var listener: TutorialAdapterListener

    fun onClick(adapterListener: TutorialAdapterListener)
    {
        this.listener = adapterListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_tutorial, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tv_title_tutorial.text = items.get(position).title
        holder.itemView.tv_sub_tutorial.text = items.get(position).sub
        holder.itemView.iv_tutorial.setImageResource(items.get(position).image)
        holder.itemView.row_tutorial.setOnClickListener()
        {
            if (position == items.size -1)
                listener.onClickVideo(position)
            else
                listener.onClickNormal(position)
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }
}