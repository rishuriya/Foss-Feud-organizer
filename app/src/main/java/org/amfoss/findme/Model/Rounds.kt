package org.amfoss.findme.Model

data class GamesRounds(
    val rounds: List<GameRound>
)
data class RegisterRound(
    val rounds: List<Participant>
)

data class registerRound(
    val Name: String,
    val Game:Int,
    val Participant: List<Int> = emptyList(),
    val Winner: String="",
    val deduction: Boolean=true,
    val status:String="Pending",
)
data class updateRound(
    val Name: String,
    val Game:Int,
    val Participant: List<Int> = emptyList(),
    val Winner: String="",
    val deduction: Boolean=true,
    val status:String="Pending",
)

data class getGame(
    val Rounds: List<getGameround>
)

data class RegisterUser(
    val Qrid: String,
    val Name: String,
    val Points: Int=0,
    val Credits: Int=300
)
data class getGameround(
    val id: Int,
    val Name: String,
    val deduction: Int=0,
    val gameAward: Int
)