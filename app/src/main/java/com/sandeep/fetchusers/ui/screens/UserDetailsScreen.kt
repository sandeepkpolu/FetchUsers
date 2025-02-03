package com.sandeep.fetchusers.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sandeep.fetchusers.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsScreen(userId: Int, userName: String, navController: NavController) {

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        stringResource(R.string.user_details_title), modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = dimensionResource(R.dimen.text_size_28sp).value.sp, color = Color.Red)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = dimensionResource(R.dimen.size_20dp), bottom = dimensionResource(R.dimen.size_8dp),
                        start = dimensionResource(R.dimen.size_8dp),
                        end = dimensionResource(R.dimen.size_8dp)),
                elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.size_4dp))
            ) {
                Column(
                    modifier = Modifier.wrapContentSize().padding(dimensionResource(R.dimen.size_20dp)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Row {
                        Text(text = stringResource(R.string.user_id),
                            fontSize = dimensionResource(R.dimen.text_size_26sp).value.sp,
                            fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.size_10dp)))
                        Text(" $userId",
                            fontSize = dimensionResource(R.dimen.text_size_24sp).value.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontStyle = FontStyle.Italic)
                    }
                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.size_10dp)))
                    Row {
                        Text(text = stringResource(R.string.user_name),
                            fontSize = dimensionResource(R.dimen.text_size_26sp).value.sp,
                            fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.size_10dp)))
                        Text(" $userName",
                            fontSize = dimensionResource(R.dimen.text_size_24sp).value.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontStyle = FontStyle.Italic)
                    }
                }
            }
        }
    }
}