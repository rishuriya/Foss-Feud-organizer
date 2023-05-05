package org.amfoss.findme.Model

data class GameRound(
    val id: Int,
    val Name: String,
    val Winner: List<Winner>,
    var Participants: List<Participant>,
    val Game: List<Game>,
    var status: String,
    var deduction: Boolean=true
)

data class Participant(
    val id: Int,
    val Qrid: String,
    val Name: String,
    var Points: Int=0,
    var Credits: Int=300
)

data class Game(
    val id: Int,
    val Name: String,
    val deduction: Int=0,
    val gameAward: List<Award>
)

data class Winner(
    val id: Int,
    val gameID: Int,
    val roundID: Int,
    val firstPlace: String,
    val secondPlace: String="",
    val thirdPlace: String="",
)

data class Award(
    val id: Int,
    val gameID: Int,
    val firstPlace: Int,
    val secondPlace: Int,
    val thirdPlace: Int
)