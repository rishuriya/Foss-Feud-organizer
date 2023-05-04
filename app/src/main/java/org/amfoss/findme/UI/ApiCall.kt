package org.amfoss.findme.UI

import androidx.navigation.NavController
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import okio.IOException
import org.amfoss.findme.Model.GameRound
import org.amfoss.findme.Navigation.AppNavigationItem

class ApiCall {
    private val client = OkHttpClient()

    fun getRounds(game: String, callback: (String) -> Unit) {
        val request = Request.Builder()
            .url("http://192.168.182.140:5500/game/?gameName=$game")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e)
            }
            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (responseBody != null) {
                    callback(responseBody)
                } // invoke the callback with the game rounds list
            }
        })
    }
}