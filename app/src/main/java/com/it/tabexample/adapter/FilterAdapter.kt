package com.it.tabexample.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.it.tabexample.util.OnClickListenerApp
import com.it.tabexample.R
import com.it.tabexample.dto.FilterDto

/**
 * created by Akash on 22/01/2021
 */

class FilterAdapter(list: List<FilterDto>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var list = ArrayList<FilterDto>()
    var onClickCallback: OnClickListenerApp.OnClickCallback? = null

    /**
     * init block for initializing the list
     */
    init {
        this.list = list as ArrayList<FilterDto>
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var tvTitle: TextView = itemView.findViewById(R.id.tvtitle)
        internal var tvCount: TextView = itemView.findViewById(R.id.tvCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_type_list, parent, false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder)
        {
            val dto = list[position]
            holder.tvTitle.text = dto.title
            if(dto.title.equals("Created Date"))
            {
                holder.tvCount.text=dto.subTitle
            }
            else if (dto.count > 0)
            {
                holder.tvCount.text = "" + dto.count
            }
            else
            {
                holder.tvCount.text = ""
            }

            Log.d("====", ""+holder.tvTitle.id+"   "+holder.tvCount.id)

            holder.itemView.setOnClickListener { view ->
                OnClickListenerApp(
                    position,
                    dto.title,
                    onClickCallback!!
                ).onClick(view)
            }

            holder.tvTitle.setOnClickListener { view ->
                OnClickListenerApp(
                    position,
                    dto.title,
                    onClickCallback!!
                ).onClick(view)
            }

            holder.tvCount.setOnClickListener { view ->
                OnClickListenerApp(
                    position,
                    dto.title,
                    onClickCallback!!
                ).onClick(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
