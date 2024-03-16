package com.example.grootclub.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPageAdapter(fm: FragmentManager, private var arrayView: ArrayList<Fragment>) : FragmentPagerAdapter(fm) {
    private var swipeEnabled = true

    override fun getCount(): Int {
        return arrayView.size
    }

    override fun getItem(position: Int): Fragment {
        return  arrayView[position]
    }


}