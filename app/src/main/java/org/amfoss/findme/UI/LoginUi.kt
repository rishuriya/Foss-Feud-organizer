package org.amfoss.findme.UI

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.amfoss.findme.Navigation.AppNavigationItem
import org.amfoss.findme.R
@Composable
fun LoginPage(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_amfoss),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp)
                .width(200.dp)
                .height(66.6.dp),
contentDescription = "Login Image"
        )
    }
    Column(
        modifier = Modifier.padding(20.dp)
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val username = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }

        Image(
            painter = painterResource(id = R.drawable.ic_vidyut),
            contentDescription = "Logo",
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
        )
        Text(
            text = "Foss Feud",
            modifier = Modifier.padding(top=10.dp),
            style = TextStyle(
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            modifier = Modifier
                .border(2.dp, Color.Black, RectangleShape),
            shape = RectangleShape,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            label = { Text(text = "Username") },
            value = username.value,
            onValueChange = { username.value = it }
        )

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            modifier = Modifier
                .border(2.dp, Color.Black, RectangleShape),
            shape = RectangleShape,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            label = { Text(text = "Password") },
            value = password.value,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { password.value = it })

        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.padding(35.dp, 0.dp, 35.dp, 0.dp)) {
            Button(
                onClick = {navController.navigate(AppNavigationItem.Home.route) },
                elevation = ButtonDefaults.buttonElevation(10.dp, 5.dp),
                shape = RectangleShape,
                modifier = Modifier
                    .height(50.dp)
                    .width(100.dp)
                    .background(Color.Transparent),
                border = BorderStroke(2.dp, Color.Black),
                colors = ButtonDefaults.buttonColors(Color.Black)
            ) {
                Text(
                    text = "Login",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun LoginPreview() {
    LoginPage(navController = rememberNavController())
}