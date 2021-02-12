package com.it.tabexample.views

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.transition.Slide
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.it.tabexample.R
import com.it.tabexample.adapter.FilterAdapter
import com.it.tabexample.dto.FilterDto
import com.it.tabexample.dto.SubFilterDto
import com.it.tabexample.util.OnClickListenerApp
import kotlinx.android.synthetic.main.fragment_filter_list.*
import java.util.*

class FilterListFragment : Fragment(), View.OnClickListener {
    private var adapter: FilterAdapter? = null
    private val LOG_TAG = FilterListFragment::class.java.name

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filter_list, container, false)
    }

    companion object {
        var listDate = ArrayList<SubFilterDto>()
        var listlocation = ArrayList<SubFilterDto>()
        var listPerson = ArrayList<SubFilterDto>()
        var listaudit = ArrayList<SubFilterDto>()
        var listType = ArrayList<FilterDto>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onResume() {
        super.onResume()
        adapter!!.notifyDataSetChanged()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(LOG_TAG, "Orientation Changed.")

    }

    private fun initView() {
        tvClearAllFilter.setOnClickListener(this)
        initData()
        setRecyclerView()
    }

    fun updateCount(count: Int, type: String) {
        for (i in 0 until listType.size) {
            if (listType[i].title.equals(type)) {
                listType[i].count = count
                if (adapter != null)
                    adapter!!.notifyDataSetChanged()
            }

        }
    }
    fun updateFilterList(title: String, type: String) {
        for (i in 0 until listType.size) {
            if (listType[i].title.equals(type)) {
                listType[i].subTitle = title
                if (adapter != null)
                    adapter!!.notifyDataSetChanged()
            }

        }
    }
    fun updateOnClear(type: String) {
        for (i in 0 until listType.size) {
            if (listType[i].title.equals(type)) {
                listType[i].count = 0
                listType[i].subTitle =""
                if (adapter != null)
                    adapter!!.notifyDataSetChanged()
            }
        }
    }
    private fun initData() {
        //init Datelist
        listDate.clear()
        var filterDto = SubFilterDto()
        filterDto.title = "Last 7 days"
        filterDto.isSelected = false
        listDate.add(filterDto)
        var filterDto1 = SubFilterDto()
        filterDto1.title = "Last 15 days"
        filterDto1.isSelected = false
        listDate.add(filterDto1)
        var filterDto2 = SubFilterDto()
        filterDto2.title = "Last 30 days"
        filterDto2.isSelected = false
        listDate.add(filterDto2)
        var filterDto3 = SubFilterDto()
        filterDto3.title = "Last 60 days"
        filterDto3.isSelected = false
        listDate.add(filterDto3)
        //init persons
        listPerson.clear()
        var dtoperson = SubFilterDto()
        dtoperson.title = "Akash"
        dtoperson.isSelected = false
        listPerson.add(dtoperson)
        var dtoperson1 = SubFilterDto()
        dtoperson1.title = "Shubham"
        dtoperson1.isSelected = false
        listPerson.add(dtoperson1)
        var dtoperson2 = SubFilterDto()
        dtoperson2.title = "Amber"
        dtoperson2.isSelected = false
        listPerson.add(dtoperson2)
        //init locations
        listlocation.clear()
        var locationDto = SubFilterDto()
        locationDto.title = "Plasia"
        locationDto.isSelected = false
        listlocation.add(locationDto)
        var locationDto1 = SubFilterDto()
        locationDto1.title = "Airtport"
        locationDto1.isSelected = false
        listlocation.add(locationDto1)
        var locationDto2 = SubFilterDto()
        locationDto2.title = "Vijay Nager"
        locationDto2.isSelected = false
        listlocation.add(locationDto2)
        var locationDto3 = SubFilterDto()
        locationDto3.title = "Vijay Nager"
        locationDto3.isSelected = false
        listlocation.add(locationDto3)
        var locationDto4 = SubFilterDto()
        locationDto4.title = "Bhawarkua"
        locationDto4.isSelected = false
        listlocation.add(locationDto4)
        //inti listtype
        listType.clear()
        var typeDto = FilterDto()
        typeDto.title = "Created Date"
        typeDto.count = 0
        listType.add(typeDto)
        var typeDto1 = FilterDto()
        typeDto1.title = "Responsible Person"
        typeDto1.count = 0
        listType.add(typeDto1)
        var typeDto4 = FilterDto()
        typeDto4.title = "Location"
        typeDto4.count = 0
        listType.add(typeDto4)
    }
    private fun setRecyclerView() {
        var layoutManager = LinearLayoutManager(requireContext())
        rv_type_List.layoutManager = layoutManager
        adapter = FilterAdapter(listType)
        adapter!!.onClickCallback = onItemClickCallback
        rv_type_List.adapter = adapter
    }
    override fun onClick(view: View) {
        when (view) {
            tvClearAllFilter ->
            {
                initData()
                adapter!!.notifyDataSetChanged()
                try {
                    var fragm =
                        fragmentManager!!.findFragmentById(R.id.frame_sub_filter_list) as SubFilterListFragment
                    fragm.clearSelection()
                } catch (e: NullPointerException)
                {

                }
            }
            tvApplyFilter -> {

            }
        }
    }

    private var onItemClickCallback: OnClickListenerApp.OnClickCallback =
        object : OnClickListenerApp.OnClickCallback {
            override fun onClicked(view: View, position: Int, type: String) {
                Log.d("====", ""+view.id)
                when(view.id){
                     R.id.isSelected ->   Log.d("====", ""+view.id)
                     R.id.tvtitle ->   Log.d("====", ""+view.id)

                }
                /*  if (!requireActivity().resources.getBoolean(R.bool.isMobile)) {
                      val fragment =
                          SubFilterListFragment()
                      val bundle = Bundle()
                      bundle.putString("TYPE", type)
                      fragment.arguments = bundle
                      fragment.setTargetFragment(this@FilterListFragment, 9099)
                      replaceFragment(
                          fragment,
                          R.id.frame_sub_filter_list, "TaskList"
                      )
                  } else {
  //                      initData()
                      val fragment =
                          SubFilterListFragment()
                      val bundle = Bundle()
                      bundle.putString("TYPE", type)
                      fragment.arguments = bundle
                      fragment.setTargetFragment(this@FilterListFragment, 9099)
                      addFragmentWithBack(
                          fragment,
                          R.id.frame_filter_list, "TaskList"
                      )
                  }*/
            }
     }
    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 9099) {
            adapter!!.notifyDataSetChanged()
            Log.d("aya", "onActivityResultExtra")
        }
    }
    fun addFragmentWithBack(fragment: Fragment, container: Int, fragmentTag: String) {
        try {
            if (activity == null) return
            fragment.enterTransition = Slide(Gravity.END)
            fragment.exitTransition = Slide(Gravity.START)
            requireActivity().supportFragmentManager.beginTransaction()
                .add(container, fragment, fragmentTag)
                .addToBackStack(null)
                .commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (e: Error) {
            e.printStackTrace()
        }
    }
    private fun replaceFragment(fragment: Fragment, container: Int, tag: String) {
        try {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(container, fragment, tag)
                .commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (e: Error) {
            e.printStackTrace()
        }
    }
}




