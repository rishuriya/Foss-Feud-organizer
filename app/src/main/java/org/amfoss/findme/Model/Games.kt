package org.amfoss.findme.Model

data class Games(
    val name:String,
    val image:Int,
    val rounds:Int=0,
    val player:Int=0,
    val time:String="rounds",
)
