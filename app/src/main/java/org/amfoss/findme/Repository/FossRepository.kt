package org.amfoss.findme.Repository

import org.amfoss.findme.Model.GameRound
import org.amfoss.findme.Model.Participant
import org.amfoss.findme.Model.registerRound
import org.amfoss.findme.Model.updateRound
import org.amfoss.findme.util.Resource

interface FossRepository {
    suspend fun fetchGameRounds(): Resource<List<GameRound>>

    suspend fun fetchUser(): Resource<List<Participant>>

    suspend fun addRound(round: registerRound): Resource<Boolean>

    suspend fun updateRound(id:Int,round: updateRound): Resource<Boolean>
}
