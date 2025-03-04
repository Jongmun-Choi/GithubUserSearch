package com.dave.github.util

import android.text.Editable

object Utils {

    fun String?.toEditable(removeBlank: Boolean = true): Editable =
        if(removeBlank && this.isNullOrBlank()) {
            Editable.Factory.getInstance().newEditable("")
        }else {
            Editable.Factory.getInstance().newEditable(this)
        }

}