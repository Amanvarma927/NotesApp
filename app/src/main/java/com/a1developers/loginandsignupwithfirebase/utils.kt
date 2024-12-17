package com.a1developers.loginandsignupwithfirebase

import android.app.Activity
import android.content.Context
import android.widget.RelativeLayout
import android.widget.Toast
import com.unity3d.services.banners.*

fun showbannerads(activity: Activity,
                  adsbannercon : RelativeLayout,
                  adsbannerid : String,
                  adSize : UnityBannerSize){

    val adview = BannerView(activity,adsbannerid,adSize)
    val adListener = object : BannerView.IListener {
        override fun onBannerLoaded(bannerAdView: BannerView?) {

            activity.longToastShow("onBannerLoaded")


        }

        override fun onBannerClick(bannerAdView: BannerView?) {

            activity.longToastShow("onBannerClick")

        }

        override fun onBannerFailedToLoad(bannerAdView: BannerView?, errorInfo: BannerErrorInfo?) {

            activity.longToastShow("error"+ errorInfo?.errorMessage)

        }

        override fun onBannerLeftApplication(bannerView: BannerView?) {

            activity.longToastShow("onBannerLeftApplication")
        }


    }

    adview.listener = adListener
    adview.load()
    adsbannercon.addView(adview)


}

fun  Context.longToastShow(msg:String){

    Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
}









