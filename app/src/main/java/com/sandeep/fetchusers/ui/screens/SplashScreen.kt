package com.sandeep.fetchusers.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sandeep.fetchusers.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        val imageModifier = Modifier
            .size(dimensionResource(R.dimen.splash_image_size))
            .border(BorderStroke(dimensionResource(R.dimen.size_4dp), Color.Black))
            .background(Color.Yellow)

        Image(painter = painterResource(R.drawable.fetch_icon),
            contentDescription = "Splash Image",
            contentScale = ContentScale.Fit,
            modifier = imageModifier)

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_10dp)))

        Text(text = stringResource(R.string.app_name),
            fontSize = dimensionResource(R.dimen.text_size_26sp).value.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = Color.Red
        )
    }

    LaunchedEffect(Unit) {
        delay(3000L)
        navController.navigate(Screen.UserList.route) {
            popUpTo(Screen.SplashScreen.route) {
                inclusive = true
            }
        }
    }

}