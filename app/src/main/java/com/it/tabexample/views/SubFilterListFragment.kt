package com.it.tabexample.views
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.InflateException
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.it.tabexample.R
import com.it.tabexample.views.FilterListFragment.Companion.listDate
import com.it.tabexample.views.FilterListFragment.Companion.listPerson
import com.it.tabexample.views.FilterListFragment.Companion.listaudit
import com.it.tabexample.views.FilterListFragment.Companion.listlocation
import com.it.tabexample.adapter.MultipleSubFilterAdapter
import com.it.tabexample.adapter.SingleSubFilterAdapter
import com.it.tabexample.dto.SubFilterDto
import com.it.tabexample.util.OnClickListenerApp
import kotlinx.android.synthetic.main.fragment_filter_list.btn_header_back
import kotlinx.android.synthetic.main.fragment_sub_filter_list.*
import kotlinx.android.synthetic.main.layout_filter_category_list.*
import java.util.*
class SubFilterListFragment : Fragment(), View.OnClickListener
{

    private var adapter: SingleSubFilterAdapter? = null
    private var multipleFilterAdapter: MultipleSubFilterAdapter? = null
    private var type = ""
    var list = ArrayList<SubFilterDto>()
    lateinit var  fragm: FilterListFragment

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View?
    {
        var view: View? = null
        try {
            type = requireArguments().getString("TYPE")!!
            view = inflater.inflate(R.layout.fragment_sub_filter_list, container, false)
        }
        catch (e: InflateException)
        {
            e.printStackTrace()
        }
        catch (e: IllegalStateException)
        {
            e.printStackTrace()
        }
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView()
    {
        sv_filter_item.setOnQueryTextListener(SearchFilterItemListener())
        tv_clear.setOnClickListener(this)
        btn_header_back.setOnClickListener(this)
        initData()
    }

    private fun setRecyclerView()
    {
        var layoutManager = LinearLayoutManager(requireContext())
        rv_task_List.layoutManager = layoutManager
//        list.sortedBy { it.title.toUpperCase() }
        adapter = SingleSubFilterAdapter(list)
        adapter!!.onClickCallback = onItemClickCallback
        rv_task_List.adapter = adapter
    }

    private fun setRecyclerViewMultiple()
    {
        var layoutManager = LinearLayoutManager(requireContext())
        rv_task_List.layoutManager = layoutManager
        list.sortedBy { it.title.toUpperCase() }
        multipleFilterAdapter = MultipleSubFilterAdapter(list)
        multipleFilterAdapter!!.onClickCallback = onItemClickCallbackMultiple
        rv_task_List.adapter = multipleFilterAdapter
    }

    private fun initData()
    {
        list.clear()
        if (type.equals("Created Date"))
        {
            list = listDate
            setRecyclerView()
        }
        else if (type.equals("Location"))
        {
            list = listlocation
            setRecyclerViewMultiple()
        } else if (type.equals("Created By"))
        {
            list = listaudit
            setRecyclerViewMultiple()
        }
        else if (type.equals("Responsible Person"))
        {
            list = listPerson
            setRecyclerViewMultiple()
        }
        else
        {
            list = listDate
            setRecyclerView()
        }
    }

    private fun setTitle()
    {
        tv_filter_title.setText("filter")
        tv_clear.setText(resources.getString(R.string.clear))
        sv_filter_item.setQueryHint(resources.getString(R.string.txt_search_hint, "title"))
    }

    override fun onClick(view: View)
    {
        when (view)
        {
            tv_clear->
            {
                clearSelection()
                updateFilterListFrag()
            }
            btn_header_back->
            {
                activity!!.onBackPressed()
            }
        }

    }

    fun clearSelection()
    {
        for (i in 0 until list.size)
        {
            list[i].isSelected = false
        }
        list.sortBy { it.title.toUpperCase() }
        adapter?.let {
            adapter!!.notifyDataSetChanged()
        } ?: run {
            multipleFilterAdapter!!.notifyDataSetChanged()

        }

    }

    fun updateFilterListFrag()
    {
        try {
            fragm =
                fragmentManager!!.findFragmentById(R.id.frame_filter_list) as FilterListFragment
            fragm.updateOnClear(type)
        }
        catch (e: ClassCastException)
        {
            val fragm: FilterListFragment =
                FilterListFragment()
            fragm.updateOnClear(type)

        }
    }

    private var onItemClickCallback: OnClickListenerApp.OnClickCallback =
            object : OnClickListenerApp.OnClickCallback {
                override fun onClicked(view: View, position: Int, url: String) {
                    for (i in 0 until list.size) {
                        if (i != position)
                            list[i].isSelected = false
                    }
                    list[position].isSelected = !list[position].isSelected
                    adapter!!.notifyDataSetChanged()
                    try
                    {
                        fragm =fragmentManager!!.findFragmentById(R.id.frame_filter_list) as FilterListFragment
                        fragm.updateFilterList( list[position].title, type)
                    }
                    catch (e: ClassCastException)
                    {
                        val fragm: FilterListFragment =
                            FilterListFragment()
                        fragm.updateFilterList(list[position].title, type)
                    }
                }
    }

    private var onItemClickCallbackMultiple: OnClickListenerApp.OnClickCallback =
            object : OnClickListenerApp.OnClickCallback {
                override fun onClicked(view: View, position: Int, url: String) {
                    list[position].isSelected = !list[position].isSelected
                    var count = 0
                    for (i in 0 until list.size)
                    {
                        if (list[i].isSelected)
                        {
                            count++
                        }
                    }
                    multipleFilterAdapter!!.notifyDataSetChanged()
                    try
                    {
                        fragm =fragmentManager!!.findFragmentById(R.id.frame_filter_list) as FilterListFragment
                        fragm.updateCount(count, type)
                    }
                    catch (e: ClassCastException)
                    {
                        val fragm: FilterListFragment =
                            FilterListFragment()
                        fragm.updateCount(count, type)
                    }
                }
   }
   override fun onDestroy()
   {
          super.onDestroy()
           if (targetFragment != null && targetRequestCode != 0){
            targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, Intent())
        }
    }
    inner class SearchFilterItemListener :
        SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            return false
        }
        override fun onQueryTextChange(newText: String): Boolean {
            if (multipleFilterAdapter != null) multipleFilterAdapter!!.getFilter().filter(newText)
            return false
        }
    }
}




