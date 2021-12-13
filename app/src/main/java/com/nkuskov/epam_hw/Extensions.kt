package com.nkuskov.epam_hw

import android.widget.Button

class Extensions {
    companion object {
        fun Button.setClickableAndEnabled(isEnable: Boolean) {
            this.isClickable = isEnable
            this.isEnabled = isEnable
        }
    }
}