package org.amfoss.findme.Model

data class GameRound(
    val id: Int,
    val Name: String,
    val Winner: String,
    var Participants: List<Participant>,
    val Game: List<Game>,
    val status: String
)

data class Participant(
    val id: Int,
    val Qrid: String,
    val Name: String,
    val Points: Int,
    val Credits: Int
)

data class Game(
    val id: Int,
    val Name: String,
    val deduction: Int=0,
    val award: Int =0
)