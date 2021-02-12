package com.it.tabexample.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.it.tabexample.dto.SubFilterDto
import com.it.tabexample.util.OnClickListenerApp
import com.it.tabexample.R
import com.it.tabexample.dto.FilterDto
import java.lang.Boolean
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList
/**
 * created by Akash on 22/01/2021
 */

class MultipleSubFilterAdapter(list: List<SubFilterDto>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    var list = ArrayList<SubFilterDto>()
    var onClickCallback: OnClickListenerApp.OnClickCallback? = null
    var mFilterList:ArrayList<SubFilterDto>
    var mSearchedFilterList:ArrayList<SubFilterDto>
    var mValueFilter: ValueFilter? = null

    /**
     * init block for initializing the list
     */
    init {
        Collections.sort(
                list,
                Comparator { o1, o2 ->
                    o1.title.toUpperCase()
                            .compareTo(o2.title.toUpperCase())
                })
        Collections.sort(
            list,
            Comparator { o1, o2 -> Boolean.compare(o2.isSelected, o1.isSelected
            ) })
        mFilterList = list as ArrayList<SubFilterDto>
        mSearchedFilterList = mFilterList
        this.list = list as ArrayList<SubFilterDto>
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var tvTitle: TextView = itemView.findViewById(R.id.tvtitle)
        internal var cbIsSelected: CheckBox = itemView.findViewById(R.id.cbIsSelected)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_multiple_selection, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            val dto = mFilterList[position]
            holder.tvTitle.text = dto.title
            holder.cbIsSelected.isChecked = dto.isSelected
            holder.cbIsSelected.setOnClickListener { view ->
                OnClickListenerApp(
                    position,
                    "" + dto.isSelected,
                    onClickCallback!!
                ).onClick(view)
            }

        }
    }
    override fun getItemCount(): Int {
        return mFilterList.size
    }
    inner class ValueFilter : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val results = FilterResults()
            if (constraint != null && constraint.length > 0) {
                val filterList = ArrayList<SubFilterDto>()
                for (i in mSearchedFilterList.indices) {
                    if (mSearchedFilterList.get(i).title.toUpperCase().contains(constraint.toString().toUpperCase())) {
                        filterList.add(mSearchedFilterList.get(i))
                    }
                }
                results.count = filterList.size
                results.values = filterList
            } else {
                results.count = mSearchedFilterList.size
                results.values = mSearchedFilterList
            }
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            mFilterList = results.values as ArrayList<SubFilterDto>
            notifyDataSetChanged()
        }
    }

    override fun getFilter(): Filter {
        if (mValueFilter == null) {
            mValueFilter = ValueFilter()
        }
        return mValueFilter as ValueFilter
    }

}
