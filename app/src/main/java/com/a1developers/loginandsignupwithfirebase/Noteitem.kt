package com.a1developers.loginandsignupwithfirebase

import android.icu.text.CaseMap.Title

data class Noteitem(val title: String, val des: String,val noteId:String){
    constructor(): this("","","")
}
