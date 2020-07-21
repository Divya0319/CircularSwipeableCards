package com.assignment.infinitelyswipablecards.helpers

import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener

/**
 * Created by Divya Gupta.
 */

/**
 * This class is created separately for creating Circular ViewPager logic
 * It accepts the parameter @param mViewPager, and handles its scrollState, so that a circular scrolling effect is achieved
 */
class CircularViewPagerHandler(private val mViewPager: ViewPager) : OnPageChangeListener {
    private var mCurrentPosition = 0
    private var mIsEndOfCycle = false
    private var mPreviousPosition = -1
    override fun onPageSelected(position: Int) {
        mCurrentPosition = position
    }

    override fun onPageScrollStateChanged(state: Int) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            if (mPreviousPosition == mCurrentPosition && !mIsEndOfCycle) {
                if (mCurrentPosition == 0) {
                    mViewPager.currentItem = mViewPager.adapter?.count!! - 1
                } else {
                    mViewPager.currentItem = 0
                }
                mIsEndOfCycle = true
            } else {
                mIsEndOfCycle = false
            }
            mPreviousPosition = mCurrentPosition
        }
    }

    override fun onPageScrolled(
        position: Int,
        positionOffset: Float,
        positionOffsetPixels: Int
    ) {
    }

}