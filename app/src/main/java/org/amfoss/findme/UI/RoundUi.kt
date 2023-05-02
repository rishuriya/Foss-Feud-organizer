package org.amfoss.findme.UI

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.amfoss.findme.Navigation.AppNavigationItem
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.amfoss.findme.Dialog.roundDialog
import org.amfoss.findme.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rounds(navController: NavController, page: String) {

    val showDialog =  remember { mutableStateOf(false) }

    if(showDialog.value)
        roundDialog(value = "",game=page, setShowDialog = {
            showDialog.value = it
        }, navController = navController) {
            Log.i("HomePage","HomePage : $it")
        }

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
        Box(
            modifier=Modifier
                .fillMaxWidth()
                .fillMaxHeight(1f)
        ){
            LazyColumn {
                items(9) {i->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(10.dp)
                            .border(2.dp, Color.Black, RectangleShape),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                    )
                    {
                        Row (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
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
                                    text = "Round ${i}",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(
                                    text = "4 Players",
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
                                    .align(Alignment.CenterVertically)
                                    .padding(3.dp)
                            ) {
                                drawCircle(
                                    color = Color.Green
                                )
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
