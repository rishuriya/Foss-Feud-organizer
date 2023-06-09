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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.amfoss.findme.Model.registerRound
import org.amfoss.findme.Navigation.AppNavigationItem
import org.amfoss.findme.viewModel.FossViewModel

@Composable
fun roundDialog(value: String,game:String,navController: NavController, setShowDialog: (Boolean) -> Unit, setValue: (String) -> Unit) {
    val viewModel = hiltViewModel<FossViewModel>()
    val txtFieldError = remember { mutableStateOf("") }
    val txtField = remember { mutableStateOf(value) }

    Dialog(onDismissRequest = { setShowDialog(false) }) {
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
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "",
                            tint = colorResource(R.color.black),
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .clickable { setShowDialog(false) }
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Row(horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()){
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(1f)
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
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()) {
                        Box(modifier = Modifier.padding(5.dp, 0.dp, 5.dp, 0.dp)) {
                            Button(
                                onClick = {
                                    if (txtField.value.isEmpty()) {
                                        txtFieldError.value = "Field can not be empty"
                                        return@Button
                                    }
                                    setValue(txtField.value)
                                    setShowDialog(false)
                                },
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
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun previewDialog(){
    val showDialog =  remember { mutableStateOf(false) }
    roundDialog(value = "", game=" ",setShowDialog = {
        showDialog.value = true
    }, navController = rememberNavController()) {
        Log.i("HomePage","HomePage : $it")
    }
}