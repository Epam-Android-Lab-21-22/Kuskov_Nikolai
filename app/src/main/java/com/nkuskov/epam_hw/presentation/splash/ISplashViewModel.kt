package com.nkuskov.epam_hw.presentation.splash

import androidx.lifecycle.LiveData

interface ISplashViewModel {

    val initProgress: LiveData<Int>
    val initFinished: LiveData<Boolean>

    fun init()
}