package com.it.tabexample.adapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.it.tabexample.dto.SubFilterDto
import com.it.tabexample.util.OnClickListenerApp
import com.it.tabexample.R
import java.lang.Boolean
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

/**
 * created by Akash on 22/01/2021
 */
class SingleSubFilterAdapter(list: List<SubFilterDto>?) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var list = ArrayList<SubFilterDto>()
    var onClickCallback: OnClickListenerApp.OnClickCallback? = null
    /**
     * init block for initializing the list
     */
    init {
        list!!.sortedBy { it.title.toUpperCase() }
        list!!.sortedBy { it.isSelected }
     /*   Collections.sort(
            list,
            Comparator { o1, o2 ->
                o1.title.toUpperCase()
                    .compareTo(o2.title.toUpperCase())
            })

        Collections.sort(
            list,
            Comparator { o1, o2 -> Boolean.compare(o2.isSelected, o1.isSelected
            ) })*/
        this.list = list as ArrayList<SubFilterDto>
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var tvTitle: TextView = itemView.findViewById(R.id.tvtitle)
        internal var isSelected: ImageView = itemView.findViewById(R.id.isSelected)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MyViewHolder(view)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            val dto = list[position]
            holder.tvTitle.text = dto.title
            Log.d("====", ""+holder.tvTitle.id+"   "+holder.isSelected.id)
//            holder.isSelected.isActivated = dto.isSelected
            if (dto.isSelected) {
                holder.isSelected.setImageResource(R.drawable.rb_checked)
            } else {
                holder.isSelected.setImageResource(R.drawable.rb_unchecked)
            }
            holder.tvTitle.setOnClickListener { view ->
                OnClickListenerApp(
                    position,
                    "" + dto.isSelected,
                    onClickCallback!!
                ).onClick(view)
            }

            holder.isSelected.setOnClickListener { view ->
                OnClickListenerApp(
                    position,
                    "" + dto.isSelected,
                    onClickCallback!!
                ).onClick(view)
            }
            /* holder.isSelected.setOnClickListener(View.OnClickListener {
                 for (i in 0 until list.size) {
                     list[i].isSelectrd = i == position
                     notifyDataSetChanged()

                 }

             })*/
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
}
