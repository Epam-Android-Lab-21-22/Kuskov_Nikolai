package com.nkuskov.epam_hw.presentation.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.concurrent.thread

class SplashViewModel : ViewModel(), ISplashViewModel{

    override val initProgress: MutableLiveData<Int> = MutableLiveData(INITIAL_PROGRESS)
    override val initFinished: MutableLiveData<Boolean> = MutableLiveData(false)

    override fun init() {
        thread {
            for (progress in 0..MAX_PROGRESS) {
                Thread.sleep(PROGRESS_DELAY)
                initProgress.postValue(progress)
            }
            initFinished.postValue(true)
        }
    }

    companion object {
        private const val INITIAL_PROGRESS = 0
        private const val MAX_PROGRESS = 100
        private const val PROGRESS_DELAY: Long = 20
    }
}