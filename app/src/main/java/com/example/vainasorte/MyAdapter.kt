package com.example.vainasorte

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.text.FieldPosition

class MyAdapter(private val myContext : Context, fm : FragmentManager, internal  var totalTabs : Int)
    : FragmentPagerAdapter(fm, totalTabs)
{
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> NumerosFragment()

            else -> LetrasFragment()
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}