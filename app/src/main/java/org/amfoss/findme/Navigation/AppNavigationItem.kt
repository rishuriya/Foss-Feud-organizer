package org.amfoss.findme.Navigation

import org.amfoss.findme.R

sealed class AppNavigationItem(var route: String,) {
    object Home : AppNavigationItem("home", )
    object Rounds: AppNavigationItem("rounds/{name}",){
        fun getRoute(name:String):String{
            return "rounds/$name"
        }
    }
    object QRScanner: AppNavigationItem("qrscanner/{name}",){
        fun getRoute(name:String):String{
            return "qrscanner/$name"
        }
    }
    object Login: AppNavigationItem("login",)

}