package com.a1developers.loginandsignupwithfirebase

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.unity3d.ads.IUnityAdsInitializationListener
import com.unity3d.ads.UnityAds

class myapp : Application() {

    override fun onCreate() {
        super.onCreate()


        UnityAds.initialize(this, "5745063", false, object : IUnityAdsInitializationListener {


            override fun onInitializationComplete() {
            }

            override fun onInitializationFailed(
                error: UnityAds.UnityAdsInitializationError?,
                message: String?,
            ) {
            }

        })
    }}