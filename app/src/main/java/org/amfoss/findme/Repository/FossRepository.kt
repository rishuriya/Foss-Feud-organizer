package org.amfoss.findme.Repository

import org.amfoss.findme.Model.*
import org.amfoss.findme.util.Resource

interface FossRepository {
    suspend fun fetchGameRounds(): Resource<List<GameRound>>

    suspend fun fetchUser(): Resource<List<Participant>>

    suspend fun addRound(round: registerRound): Resource<Boolean>

    suspend fun updateRound(id:Int,round: updateRound): Resource<Boolean>

    suspend fun updateUser(id:Int,round: Participant): Resource<Boolean>

    suspend fun getGame(): Resource<List<getGameround>>

    suspend fun postWinner(winner: Winner): Resource<Boolean>

    suspend fun postUser(user: RegisterUser): Resource<Boolean>
}
