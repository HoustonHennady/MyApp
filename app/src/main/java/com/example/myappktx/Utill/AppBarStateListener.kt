package com.example.myappktx.Utill

import com.example.myappktx.Utill.AppBarStateListener.AppBarState.COLLAPSED
import com.example.myappktx.Utill.AppBarStateListener.AppBarState.IDLE
import com.google.android.material.appbar.AppBarLayout

abstract class AppBarStateListener: AppBarLayout.OnOffsetChangedListener {

    sealed class AppBarState{
        object EXPENDED : AppBarState()
        object COLLAPSED : AppBarState()
        object IDLE : AppBarState()
    }

    private var currentState:AppBarState = IDLE

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, p1: Int) {
        if (p1 == 0){
            if (currentState!= AppBarState.EXPENDED){
                appBarLayout?.let { onStateChanged(appBarLayout = it,state = AppBarState.EXPENDED)}
            }
            currentState =
                AppBarState.EXPENDED
        }else if (Math.abs(p1) >= appBarLayout!!.totalScrollRange) {
            if (currentState != COLLAPSED){
                onStateChanged(appBarLayout = appBarLayout, state = COLLAPSED)
            }
            currentState =
                COLLAPSED
        }else{
            if (currentState != IDLE){
                onStateChanged(appBarLayout = appBarLayout, state = IDLE)
            }
            currentState =
                IDLE
        }
    }

    abstract fun onStateChanged(appBarLayout: AppBarLayout, state: AppBarState)

}