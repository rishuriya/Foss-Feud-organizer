package org.amfoss.findme.UI

import android.telecom.Call.Details
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import org.amfoss.findme.Dialog.roundDialog
import org.amfoss.findme.R
import org.amfoss.findme.viewModel.FossViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow
import org.amfoss.findme.Dialog.roundDialogInfo
import org.amfoss.findme.Model.*
import org.amfoss.findme.application.App
import org.amfoss.findme.util.CacheService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rounds(navController: NavController, page: String, viewModel: FossViewModel = hiltViewModel<FossViewModel>()) {
    val showDialog =  remember { mutableStateOf(false) }
    var value = remember { mutableStateOf("") }
    var confirm= remember { mutableStateOf(false) }
    var gameround= remember { mutableStateOf( GameRound(0,"","", emptyList(), emptyList(),""))}
    val showDialogInfo =  remember { mutableStateOf(false) }
    if(showDialog.value)
        roundDialog(value = "",game=page, setShowDialog = {
            showDialog.value = it
        }, navController = navController) {
            value.value=it
            confirm.value=true

        }
    val cache= App.context?.let { CacheService(it, "barCode") }
    val cacheGame= App.context?.let { CacheService(it, "game") }
    val data= cache?.getData()
    val idData= cacheGame?.getData()
    println(data)
    if(showDialogInfo.value)
        roundDialogInfo(value = value.value,game=gameround.value, setShowDialogInfo = {
            showDialogInfo.value = it
        }, navController = navController)
    Log.i("HomePage","HomePage : ${value.value}")
    if(confirm.value) {
        LaunchedEffect(Unit) {
            viewModel.addRound(registerRound(value.value, 3))
            value.value=""
        }
    }
    LaunchedEffect(Unit){
        viewModel.fetchRounds()
    }
    val RoundState by viewModel.totalRound.collectAsState()
    val UserState by viewModel.user.collectAsState()
    val totalRoundState = RoundState.filter { it.Game[0].Name == page }
    val res= when (page) {
        "Bug Hunt" -> {
            R.drawable.ic_bug_hunt
        }
        "Trivia Quiz" -> {
            R.drawable.ic_quiz
        }
        "Blind Coding" -> {
            R.drawable.ic_blind_coding
        }
        "Type Racing" -> {
            R.drawable.ic_speedracer
        }
        else -> {
            R.drawable.ic_quiz
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .background(Color.White)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.Cyan,
                        Color.Cyan,
                        Color.White,
                    ),
                    radius = 1200f,
                    center = Offset(500f, 450f)
                )
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color.White)
                .shadow(20.dp, RectangleShape)
                .background(Color.White)
                .border(BorderStroke(1.dp, Color.Black), shape = RectangleShape)
        ) {
            IconButton(
                onClick = {
                    navController.navigateUp()
                },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Text(
                text = page,
                modifier = Modifier.align(Alignment.Center),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        if(data!=""){
            LaunchedEffect(Unit){
                viewModel.fetchUser()
            }
        }
        if(data!="" && totalRoundState.size!=0){
            val rjnud=UserState.filter { it.Qrid==data }.toMutableList()
            val round=totalRoundState.filter { it.id==idData?.toInt() }.toMutableList()
            value.value = round[0].Name
            var land=round[0].Participants.toMutableList()
            land.addAll(rjnud)
            val participants=land.toSet().toList()
            round[0].Participants=participants
            val participantIds = round[0].Participants.map { it.id }
            Log.i("HomePage","CheckId : ${participantIds}")
            LaunchedEffect(Unit){
                viewModel.updateRound(round[0].id,
                    updateRound(round[0].Name,round[0].Game[0].id,participantIds))
            }
            gameround.value=round[0]
            showDialogInfo.value=true
        }
        Box(
            modifier=Modifier
                .fillMaxWidth()
                .fillMaxHeight(1f)
        ){
            LazyColumn {
                totalRoundState.size.let {
                    items(it) { i->
                        val round= totalRoundState[i]
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
                                .border(2.dp, Color.Black, RectangleShape)
                                .clickable {
                                    value.value = round.Name
                                    gameround.value=round
                                    println(round)
                                    cacheGame?.saveData(round.id.toString())
                                    showDialogInfo.value = true
                                 },
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            ),
                        )
                        {
                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start=5.dp,end=15.dp,top=5.dp,bottom=5.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ){
                                Row {
                                    Image(
                                        painter = painterResource(id = res),
                                        contentDescription = "Image",
                                        modifier = Modifier
                                            .size(100.dp)
                                            .padding(10.dp)
                                            .border(2.dp, Color.Black, RectangleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                    Column(
                                        modifier = Modifier
                                            .padding(10.dp)
                                    ) {
                                        Text(
                                            text = round.Name,
                                            style = TextStyle(
                                                color = Color.Black,
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                        Text(
                                            text = "${round.Participants.size} Players",
                                            style = TextStyle(
                                                color = Color.Black,
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                    }
                                }
                                Canvas(
                                    modifier = Modifier
                                        .size(size = 20.dp)
                                        .border(2.dp, Color.Black, CircleShape)
                                        .align(Alignment.CenterVertically)
                                ) {
                                    when (round.status) {
                                        "Completed" -> drawCircle(
                                            color = Color.Red
                                        )
                                        "Started" -> drawCircle(
                                            color = Color.Yellow
                                        )
                                        "Pending" -> drawCircle(
                                            color = Color.Green
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            FloatingActionButton(
                onClick = { showDialog.value = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.Transparent)
                    .padding(bottom = 18.dp, end = 18.dp)
                    .border(2.dp, Color.Black, RoundedCornerShape(15.dp)),
                elevation = FloatingActionButtonDefaults.elevation(10.dp, 5.dp)
            ) {
                Icon(Icons.Filled.Add, "Localized description")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    rounds(navController = rememberNavController(),
    page = "Bug Hunt")
}
