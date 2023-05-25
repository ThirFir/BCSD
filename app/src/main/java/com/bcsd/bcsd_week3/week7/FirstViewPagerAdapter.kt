package com.bcsd.bcsd_week3.week7

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bcsd.bcsd_week3.R


class ViewPagerAdapter(fragmentActivity: FragmentActivity, bottomID: Long) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragments = arrayListOf<Fragment>()

    init {
        when (bottomID) {
            0L -> {
                val colors = arrayListOf<Int>().apply {
                    add(R.color.red)
                    add(R.color.orange)
                    add(R.color.yellow)
                    add(R.color.green)
                    add(R.color.blue)
                    add(R.color.indigo)
                    add(R.color.violet)
                }
                for (i in 0..6) {
                    fragments.add(FirstAFragment.newInstance(colors[i]))
                }
            }
            1L -> for (i in 0..10) {
                fragments.add(SecondFragment.newInstance('0' + i))
            }
            2L -> for (i in 0 .. 'Z'-'A') {
                fragments.add(SecondFragment.newInstance('A' + i))
            }
        }
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int = fragments.size

}