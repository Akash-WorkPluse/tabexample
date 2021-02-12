package com.it.tabexample.views

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.it.tabexample.R
import com.it.tabexample.dto.Person
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity()
{
    val TAG="Main Activity"
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(FilterListFragment(), R.id.frame_filter_list, "FilterListFragment")
        if (frame_sub_filter_list != null)
        {
            val fragment = SubFilterListFragment()
            val bundle = Bundle()
            bundle.putString("TYPE", "")
            fragment.arguments = bundle
            replaceFragment(fragment,
                    R.id.frame_sub_filter_list, "SubFilterListFragment")
        }
        val job = GlobalScope.launch(Dispatchers.Default)
        {
            repeat(5)
            {
                Log.d(TAG, "Coroutines is still working")
                // delay the coroutine by 1sec
                delay(1000)
            }
        }
/*
        runBlocking {
            // waiting for the coroutine to finish it's work
            job.join()
            Log.d(TAG, "Main Thread is Running")
        }*/
        /* delay(3000)
         GlobalScope.launch {
             Log.d("===", Thread.currentThread().name.toString())
         }
         runBlocking {
             Log.d(TAG,"just entered into the runBlocking ")
             delay(5000)

             Log.d(TAG,"start of the run-blocking")
             Log.d(TAG,"End of the runBlocking")
         }
         Log.d(TAG,"after the run blocking")*/
        performLetOperation()
        setOrientation()
    }
    private fun setOrientation()
    {
        if (resources.getBoolean(R.bool.isMobile))
        {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
        }
        else
        {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        }
//        someMethodWithvvvCcallback({ s:Int.(Int)-> print("")})
    }
    fun someMethodWithCallback(callback: (Int) -> Unit) {
        callback(0)
        someMethodWithCallback{
        }
    }
    fun someMethodWithvvvCcallback(callback: Int.() -> Unit) {
        callback(0)
        someMethodWithCallback{

        }
    }
    private fun performLetOperation()
    {
        val person = Person().apply {
           this.address="hhh"
//            return@apply "The name of the Person is: ${this}"
        }
        Log.d("========A=", ""+person.address)
    }
    /*load fragment to activity on container*/
    private fun replaceFragment(fragment: Fragment, container: Int, tag: String)
    {
        try
        {
            supportFragmentManager
                    .beginTransaction()
                    .replace(container, fragment, tag)
                    .commitAllowingStateLoss()
        }
        catch (e: Exception)
        {
            e.printStackTrace()
        }
        catch (e: Error)
        {
            e.printStackTrace()
        }
    }
}