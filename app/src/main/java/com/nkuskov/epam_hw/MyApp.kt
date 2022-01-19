package com.nkuskov.epam_hw

import android.app.Application
import com.nkuskov.epam_hw.presenter.GridRecyclerViewPresenter
import com.nkuskov.epam_hw.presenter.VerticalRecyclerViewPresenter

class MyApp : Application() {

    val gridViewPresenter = GridRecyclerViewPresenter()
    val verticalViewPresenter = VerticalRecyclerViewPresenter()
}