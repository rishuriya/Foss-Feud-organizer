package org.amfoss.findme.Repository

import androidx.annotation.WorkerThread
import org.amfoss.findme.Model.GameRound
import org.amfoss.findme.Model.Participant
import org.amfoss.findme.Model.registerRound
import org.amfoss.findme.Model.updateRound
import org.amfoss.findme.service.ApiService
import org.amfoss.findme.util.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FossRepositoryImpl @Inject constructor(val service: ApiService) : FossRepository {

    @WorkerThread
    override suspend fun fetchGameRounds(): Resource<List<GameRound>> {
        return try {
            val response = service.getGameRound()
            Resource(Resource.Status.SUCCESS, response.rounds)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource(Resource.Status.FAILED, null)
        }
    }

    @WorkerThread
    override suspend fun fetchUser(): Resource<List<Participant>> {
        return try {
            val response = service.getUser()
            Resource(Resource.Status.SUCCESS, response.rounds)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource(Resource.Status.FAILED, null)
        }
    }

    @WorkerThread
    override suspend fun addRound(round: registerRound): Resource<Boolean> {
        return try {
            val response = service.createGameRound(round)
            Resource(Resource.Status.SUCCESS, response.isSuccessful)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource(Resource.Status.FAILED, null)
        }
    }

    @WorkerThread
    override suspend fun updateRound(id:Int,round: updateRound): Resource<Boolean> {
        return try {
            val response = service.updateGameRound(id,round)
            Resource(Resource.Status.SUCCESS, response.isSuccessful)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource(Resource.Status.FAILED, null)
        }
    }
}