package com.sandeep.fetchusers.ui.screens

import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.sandeep.fetchusers.R
import com.sandeep.fetchusers.data.model.User
import com.sandeep.fetchusers.utils.NetworkUtils
import com.sandeep.fetchusers.viewmodel.Status
import com.sandeep.fetchusers.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(navController: NavController, viewModel:UserViewModel) {

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        stringResource(R.string.user_list_title), modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = dimensionResource(R.dimen.text_size_28sp).value.sp,
                        color = Color.Red)
                }
            )
        }
        ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding),
           contentAlignment = Alignment.Center ) {
            MyApp(viewModel, navController)
        }
    }
}

fun groupPostsByUserId(posts: List<User>) : Map<Int, List<User>> {
    return posts
        .filter {
            it.name!= null && it.name.isNotEmpty()
        }
        .sortedBy {
            it.listId
        }
        .groupBy { it.listId }
}

@Composable
fun MyApp(viewModel: UserViewModel, navController: NavController) {
    val context = LocalContext.current
    val usersStatus by viewModel.usersStatus.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val isRefreshing = usersStatus is Status.Loading
    LaunchedEffect(Unit) {
        if (usersStatus is Status.Loading && NetworkUtils.isInternetConnected(context)) {
            viewModel.fetchUsers()
        }
    }
    if (!NetworkUtils.isInternetConnected(context)) {
        showDialog = true
    } else {
        when (val status = usersStatus) {
            is Status.Loading -> LoadingScreen()
            is Status.Error -> ErrorScreen(message = status.message, viewModel)
            is Status.Success -> UserList(navController = navController,
                status.data, isRefreshing = isRefreshing, onRefresh = {
                    viewModel.refreshPosts()
                })
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(onClick = {
                    showDialog = false
                    (context as Activity).finish()
                }) {
                    Text(stringResource(R.string.ok))
                }
            },
            icon = {
                Icon(imageVector = Icons.Filled.Warning, contentDescription = "Warning Icon")
            },
            title = {
                Text(text =stringResource(R.string.network_error_title), color = Color.Black)
            },
            text = {
                Text(
                    text = stringResource(R.string.network_error_text),
                    color = Color.DarkGray
                )
            },
        )
    }
}


@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(message: String, viewModel: UserViewModel) {
    Column(
        modifier = Modifier.fillMaxSize()
            .wrapContentSize(Alignment.Center),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            color = Color.Red,
            style = MaterialTheme.typography.labelMedium
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_10dp)))

        Button(onClick = {
            viewModel.refreshPosts()
        }) {
            Text(stringResource(R.string.refresh))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserList(navController: NavController, users: List<User>,
             isRefreshing: Boolean, onRefresh: () -> Unit) {
    val groupedPosts = groupPostsByUserId(users)
    val listState = rememberLazyListState()
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = onRefresh
    ) {
        LazyColumn(state = listState) {
            groupedPosts.forEach { (listId, groupUsers) ->
                val sortedUser = groupUsers.sortedBy { it.name }
                stickyHeader {
                    UserHeader(listId)
                }
                items(sortedUser) { user ->
                    UserItem(user = user, navController)
                }
            }
        }
    }
}

@Composable
fun UserHeader(listId: Int) {
    Text(
        text = "List ID: $listId",
        style = MaterialTheme.typography.titleMedium,
        color = Color.White,
        fontSize = dimensionResource(R.dimen.text_size_24sp).value.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(dimensionResource(R.dimen.size_16dp))
    )
}

@Composable
fun UserItem(user: User, navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(dimensionResource(R.dimen.size_8dp))
            .clickable {
                navController.navigate(Screen.UserDetail.route + "?userId=${user.id}&userName=${user.name}")
            },
        elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.size_4dp))
    ) {
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.size_16dp))) {
            Text(
                text = stringResource(R.string.user_id)+" ${user.id}",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = dimensionResource(R.dimen.text_size_26sp).value.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_8dp)))
            Text(
                text = stringResource(R.string.user_name)+" ${user.name}",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = dimensionResource(R.dimen.text_size_28sp).value.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}