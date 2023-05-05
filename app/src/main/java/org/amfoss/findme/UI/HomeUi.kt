package org.amfoss.findme.UI
import android.util.Log
import androidx.compose.foundation.*
import org.amfoss.findme.R
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.amfoss.findme.MainActivity
import org.amfoss.findme.Model.Games
import org.amfoss.findme.Navigation.AppNavigation
import org.amfoss.findme.Navigation.AppNavigationItem
import org.amfoss.findme.viewModel.FossViewModel
import androidx.hilt.navigation.compose.hiltViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindMeScreen(navController: NavController) {
    val viewModel:FossViewModel=hiltViewModel()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val roundList by viewModel.totalRound.collectAsState()
    val cards= listOf<Games>(Games("Bug Hunt", R.drawable.ic_bug_hunt, id = 2),Games("Trivia Quiz", R.drawable.ic_quiz, id = 3),Games("Blind Coding", R.drawable.ic_blind_coding, id = 4),Games("Type Racing", R.drawable.ic_speedracer, id = 5))

    LaunchedEffect(Unit){
        viewModel.fetchRounds()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .background(Color.White)
            .verticalScroll(
                state = rememberScrollState(),
            )
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.Cyan,
                        Color.Cyan,
                        Color.White,
                    ),
                    radius = 800f,
                    center = Offset(500f, 450f)
                )
            )
    ) {
        // Top Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .background(Color.Transparent)
        )
        {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "Foss Feud",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontFamily = FontFamily.SansSerif,
                    )
                    // Small Card
                    Box(
                        modifier = Modifier
                            .padding(0.dp)
                            .size(40.dp, 40.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color.White)
                            .border(2.dp, Color.Black, RoundedCornerShape(6.dp))
                            .clickable {
                                navController.navigate(AppNavigationItem.Login.route)
                            }
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_signup),
                            contentDescription = "Emoji",
                            modifier = Modifier
                                .size(30.dp)
                                .padding(0.dp)
                                .align(Alignment.Center),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
                Text(
                    text = "Hello, Organizer!",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black,
                    fontFamily = FontFamily.Cursive,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                // Search Bar
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(80.dp)
//                        .padding(16.dp)
//                        .clip(RoundedCornerShape(20.dp))
//                        .border(2.dp, Color.Black, RoundedCornerShape(20.dp)),
//                    elevation = CardDefaults.cardElevation(25.dp),
//                    colors = CardDefaults.cardColors(
//                        containerColor = Color.White,
//                    )
//                )
//                {
//                        TextField(
//                            value = "",
//                            onValueChange = {
//                                //TODO
//                            },
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .fillMaxHeight()
//                                .padding(8.dp),
//                            colors = TextFieldDefaults.textFieldColors(
//                                textColor = Color.Black,
//                                containerColor = Color.Transparent,
//                                focusedIndicatorColor = Color.Transparent,
//                                unfocusedIndicatorColor = Color.Transparent,
//                                disabledIndicatorColor = Color.Transparent,
//                                errorIndicatorColor = Color.Transparent,
//                            ),
//                            trailingIcon = {
//                                Icon(
//                                    painter = painterResource(R.drawable.ic_search),
//                                    contentDescription = "Search",
//                                    modifier = Modifier
//                                        .size(40.dp)
//                                        .padding(0.dp)
//                                )
//                            },
//                        )
//                    }
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(150.dp)
                            .border(2.dp, Color.Black, CircleShape)
                            .background(Color.White)
                    )
                    {
                        Image(
                            painter = painterResource(R.drawable.ic_emoji),
                            contentDescription = "Emoji",
                            modifier = Modifier
                                .size(180.dp)
                                .padding(10.dp)
                                .align(Alignment.Center),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End)
                .height(screenHeight - 350.dp)
                .background(Color.Transparent)
        ) {
            val scrollState = rememberScrollState()
            LazyRow(
                modifier = Modifier
                    .height(340.dp)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                items(cards) { card ->
                    var player=0
                    for(round in roundList.filter { it.Game[0].Name == card.name }){
                        player += round.Participants.size
                    }
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .width(250.dp)
                            .height(300.dp)
                            .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.Green)
                            .clickable( onClick = { navController.navigate(AppNavigationItem.Rounds.getRoute(card.name)){
                                println("Clicked")
                            }}),
                        elevation = CardDefaults.cardElevation(16.dp),
                    ) {
                        // Card Content
                        Image(
                            painter = painterResource(card.image),
                            contentDescription = "Emoji",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp))
                                .padding(0.dp)
                                .border(2.dp, Color.Black,),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = card.name,
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, start = 8.dp)
                        )
                        Text(
                            text = "${roundList.filter { it.Game[0].Name==card.name }.size} Rounds",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp)
                        )
                        Text(
                            text = "${player} Players",
                            color = Color.Black,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp)
                                .align(Alignment.End)
                        )
                    }
                }

            }
            Spacer(modifier = Modifier.height(100.dp))
//            Box(
//                modifier = Modifier
//                    .width(25.dp)
//                    .height(50.dp)
//                    .clip(RoundedCornerShape(topStart = 12.5.dp, bottomStart = 12.5.dp))
//                    .align(Alignment.BottomEnd)
//                    .background(Color.White)
//                    .border(2.dp, Color.Black, RoundedCornerShape(topStart = 12.5.dp, bottomStart = 12.5.dp))
//            ) {
//
//            }
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(12.dp)
                    .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp))
                    .align(Alignment.BottomCenter)
                    .background(Color.White)
                    .border(1.dp, Color.Gray, RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp))
            ) {

            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewHome() {
    AppNavigation(activity = MainActivity())
}