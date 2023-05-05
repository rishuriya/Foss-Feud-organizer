package org.amfoss.findme.Dialog

import android.R
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.amfoss.findme.Model.GameRound
import org.amfoss.findme.Model.Participant
import org.amfoss.findme.Model.Winner
import org.amfoss.findme.Model.updateRound
import org.amfoss.findme.Navigation.AppNavigationItem
import org.amfoss.findme.application.App
import org.amfoss.findme.util.CacheService
import org.amfoss.findme.viewModel.FossViewModel
import java.util.stream.IntStream.range

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun roundDialogInfo(value: String,game:GameRound,navController: NavController, setShowDialogInfo: (Boolean) -> Unit,setValue: (String) -> Unit){
    val viewModel:FossViewModel= hiltViewModel()
    val coroutineScope = rememberCoroutineScope()
    val txtFieldError = remember { mutableStateOf("") }
    val txtField = remember { mutableStateOf(value) }
    val cache= App.context?.let { CacheService(it, "barCode") }
    val selectedCard = remember { mutableStateListOf<Participant?>() }
    var check= remember { mutableStateOf(game.deduction)}
    val UserState by viewModel.user.collectAsState()
    val getLocationOnClick: () -> Unit = {
        val participantIds = game.Participants.map { it.id }
        game.deduction=check.value
        coroutineScope.launch{
            viewModel.updateRound(game.id,
                updateRound(game.Name,game.Game[0].id,participantIds, deduction = game.deduction)
            )
        }
        if (cache != null) {
            cache.deleteData()
        }
        setShowDialogInfo(false)
    }

    val startRound: () -> Unit = {
        val participantIds = game.Participants.map { it.id }
        game.deduction = check.value
        game.status = "Started"
        coroutineScope.launch {
            viewModel.updateRound(
                game.id,
                updateRound(
                    game.Name,
                    game.Game[0].id,
                    participantIds,
                    deduction = game.deduction,
                    status = game.status
                )
            )
            for (participant in game.Participants) {
                if (game.deduction)
                    participant.Credits = participant.Credits - game.Game[0].deduction
                viewModel.updateUser(participant.id, participant)
            }
        }

        if (cache != null) {
            cache.deleteData()
        }
        setShowDialogInfo(false)
    }

    val completeRound: () -> Unit = {
        coroutineScope.launch{
            viewModel.fetchUser()
            delay(1000)
        }
        val participantIds = game.Participants.map { it.id }
        game.deduction = check.value
        game.status = "Completed"
        coroutineScope.launch {
            viewModel.updateRound(
                game.id,
                updateRound(
                    game.Name,
                    game.Game[0].id,
                    participantIds,
                    deduction = game.deduction,
                    status = game.status
                )
            )
            val winner=Winner(id=game.id,gameID = game.Game[0].id, roundID = game.id, firstPlace = selectedCard[0]!!.Qrid, secondPlace = selectedCard[1]!!.Qrid, thirdPlace = selectedCard[2]!!.Qrid)
            viewModel.addWinner(Winner(id=game.id,gameID = game.Game[0].id, roundID = game.id, firstPlace = selectedCard[0]!!.Qrid, secondPlace = selectedCard[1]!!.Qrid, thirdPlace = selectedCard[2]!!.Qrid))
            for(i in 0..selectedCard.size-1){
                val participant= UserState.filter { it.Qrid==selectedCard[i]?.Qrid }
                println(selectedCard[i])
                if(i==0)
                    selectedCard[i]?.Points=selectedCard[i]?.Points!!+game.Game[0].gameAward[0].firstPlace
                else if(i==1)
                    selectedCard[i]?.Points=selectedCard[i]?.Points!!+game.Game[0].gameAward[0].secondPlace
                else if(i==2)
                    selectedCard[i]?.Points=selectedCard[i]?.Points!!+game.Game[0].gameAward[0].thirdPlace
                viewModel.updateUser(selectedCard[i]!!.id,selectedCard[i]!!)
            }
        }
        if (cache != null) {
            cache.deleteData()
        }
        setShowDialogInfo(false)
    }
    Dialog(onDismissRequest = {
        cache?.deleteData()
        setShowDialogInfo(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            tonalElevation = 10.dp,
            modifier = Modifier
                .border(2.dp,Color.Black,RoundedCornerShape(16.dp))
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Set Rounds",
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Row(

                        ) {
                            Checkbox(
                                checked = check.value,
                                onCheckedChange = { Check -> check.value = Check },
                                modifier = Modifier
                                    .size(45.dp)
                                    .align(Alignment.CenterVertically),
                                colors = CheckboxDefaults.colors(
                                    checkmarkColor = Color.Cyan,
                                    uncheckedColor = MaterialTheme.colorScheme.onSurface,
                                )
                            )
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "",
                                tint = colorResource(R.color.black),
                                modifier = Modifier
                                    .width(30.dp)
                                    .height(30.dp)
                                    .clickable { setShowDialogInfo(false) }
                                    .align(Alignment.CenterVertically)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Row(horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()){
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .shadow(5.dp, RectangleShape)
                                .background(Color.White)
                                .height(60.dp)
                                .border(
                                    BorderStroke(
                                        width = 2.dp,
                                        color = colorResource(id = if (txtFieldError.value.isEmpty()) R.color.black else R.color.holo_red_dark)
                                    ),
                                    shape = RectangleShape
                                ),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.MoreVert,
                                    contentDescription = "",
                                    tint = colorResource(R.color.holo_green_light),
                                    modifier = Modifier
                                        .width(20.dp)
                                        .height(20.dp)
                                )
                            },
                            placeholder = { Text(text = "Enter value") },
                            value = txtField.value,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            onValueChange = {
                                txtField.value = it.take(10)
                            })

                        Box(modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 0.dp)) {
                            Button(
                                contentPadding =PaddingValues(1.dp),
                                onClick = {
//                                    navController.navigate(AppNavigationItem.QRScanner.getRoute(game.Game[0].Name),navOptions = navController.currentDestination?.id?.let {
//                                        NavOptions.Builder()
//                                            .setPopUpTo(it, inclusive = true)
//                                            .build()
//                                    })
                                          setValue("True")
                                    setShowDialogInfo(false)
                                },
                                enabled = game.status=="Pending",
                                elevation = ButtonDefaults.buttonElevation(10.dp, 5.dp),
                                shape = RectangleShape,
                                modifier = Modifier
                                    .height(60.dp)
                                    .background(Color.Transparent)
                                    .padding(0.dp,0.dp),
                                border = BorderStroke(2.dp, Color.Black),
                                colors = ButtonDefaults.buttonColors(Color.White)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "",
                                    tint = colorResource(R.color.black),
                                    modifier = Modifier
                                        .width(20.dp)
                                        .height(20.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    if (game.Participants.isEmpty().not()) {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .border(2.dp,Color.Black, RectangleShape)
                            .padding(5.dp)) {
                            LazyColumn {
                                items(game.Participants ?: emptyList()) {
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Card(modifier = Modifier
                                        .background(Color.White)
                                        .fillMaxWidth()
                                        .border(2.dp,if (selectedCard.indexOf(it)==0) Color.Green else if (selectedCard.indexOf(it)==1) Color.Yellow else if(selectedCard.indexOf(it)==2) Color.Red else Color.Transparent, RectangleShape)
                                        .clickable { if(selectedCard.size<3) selectedCard.add(it) else if (selectedCard.contains(it)) selectedCard.remove(it)},
                                        shape = RectangleShape) {
                                        Row {
                                            Icon(
                                                imageVector = Icons.Filled.CheckCircle,
                                                contentDescription = "",
                                                tint = colorResource(R.color.black),
                                                modifier = Modifier
                                                    .width(20.dp)
                                                    .height(20.dp)
                                                    .clickable {
                                                        val listMut=game.Participants.toMutableList()
                                                        listMut.remove(it)
                                                        game.Participants=listMut
                                                    }
                                            )
                                            Text(
                                                it.Name,
                                                fontSize = 15.sp,
                                                color = Color.Black,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier
                                                    .padding(start = 5.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Row(horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()) {
                        if (game.status == "Pending" ) {
                            Box(modifier = Modifier.padding(5.dp, 0.dp, 5.dp, 0.dp)) {
                                Button(
                                    onClick = getLocationOnClick,
                                    elevation = ButtonDefaults.buttonElevation(10.dp, 5.dp),
                                    shape = RectangleShape,
                                    modifier = Modifier
                                        .height(50.dp)
                                        .background(Color.Transparent),
                                    border = BorderStroke(2.dp, Color.Black),
                                    colors = ButtonDefaults.buttonColors(Color.White)
                                ) {
                                    Text(
                                        text = "Save",
                                        color = Color.Black,
                                        fontSize = 18.sp
                                    )
                                }
                            }

                            Box(modifier = Modifier.padding(5.dp, 0.dp, 5.dp, 0.dp)) {
                                Button(
                                    onClick = startRound,
                                    elevation = ButtonDefaults.buttonElevation(10.dp, 5.dp),
                                    shape = RectangleShape,
                                    modifier = Modifier
                                        .height(50.dp)
                                        .background(Color.Transparent),
                                    border = BorderStroke(2.dp, Color.Black),
                                    colors = ButtonDefaults.buttonColors(Color.White)
                                ) {
                                    Text(
                                        text = "Start",
                                        color = Color.Black,
                                        fontSize = 18.sp
                                    )
                                }
                            }
                        }
                        else if(game.status=="Started") {
                            Box(modifier = Modifier.padding(5.dp, 0.dp, 5.dp, 0.dp)) {
                                Button(
                                    onClick = completeRound,
                                    elevation = ButtonDefaults.buttonElevation(10.dp, 5.dp),
                                    shape = RectangleShape,
                                    modifier = Modifier
                                        .height(50.dp)
                                        .background(Color.Transparent),
                                    border = BorderStroke(2.dp, Color.Black),
                                    colors = ButtonDefaults.buttonColors(Color.White)
                                ) {
                                    Text(
                                        text = "Complete",
                                        color = Color.Black,
                                        fontSize = 18.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun previewDialogInfo(){
    val showDialog =  remember { mutableStateOf(false) }
    roundDialogInfo(value = "", game= GameRound(0,"", emptyList(), emptyList(), emptyList(),""),setShowDialogInfo = {
        showDialog.value = true
    }, navController = rememberNavController()){
        Log.i("HomePage","HomePage : $it")
    }
}