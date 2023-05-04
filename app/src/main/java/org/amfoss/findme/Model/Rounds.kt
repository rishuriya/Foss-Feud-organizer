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
    val Rounds: List<Game>
)