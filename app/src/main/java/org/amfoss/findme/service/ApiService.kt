package org.amfoss.findme.service
import org.amfoss.findme.Model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("game/")
    suspend fun getGameRound(): GamesRounds
    @GET("register/")
    suspend fun getUser(): RegisterRound

    @POST("postRound/")
    suspend fun createGameRound(@Body gameRound: registerRound): Response<GameRound>

    @PUT("updateRound/{id}/")
    suspend fun updateGameRound(@Path("id") id: Int, @Body gameRound: updateRound): Response<registerRound>
}