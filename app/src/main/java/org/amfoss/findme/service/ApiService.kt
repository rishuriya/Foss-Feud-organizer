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

    @PUT("updateUser/{id}/")
    suspend fun updateStudent(@Path("id") id: Int, @Body user: Participant): Response<Participant>

    @GET("getGame")
    suspend fun getGame(): getGame

    @POST("postWinner/")
    suspend fun postWinner(@Body winner: Winner): Response<Winner>

    @POST("postUser/")
    suspend fun postUser(@Body user: RegisterUser): Response<Participant>
}