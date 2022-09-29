package com.briancaldera.hazel.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.briancaldera.hazel.R
import com.briancaldera.hazel.ui.theme.HazelTheme
import com.briancaldera.hazel.viewmodel.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    private val viewModel by viewModels<SignInViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HazelTheme {
                HomeScreen()
            }
        }
        viewModel.signOut.observe(this) {
            if (it) {
                this.finish()
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: SignInViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val showSignOutDialog = remember { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(scaffoldState = scaffoldState, topBar = {
        UserBar (viewModel.uiState.value.username, {
            showSignOutDialog.value = true
        })
    }) {

    }
    if (showSignOutDialog.value) {
        val message = stringResource(id = R.string.snackbar_error_signout)

        SignOutDialog({viewModel.signOut { success, exception ->
            if (!success) {
                scope.launch {
                    val formattedMessage  = String.format(message, exception?.message ?: "Unknown error")
                    scaffoldState.snackbarHostState.showSnackbar(formattedMessage)
                }
            }
        }}, { showSignOutDialog.value = false })
    }
}

@Composable
fun UserBar(user: String, onSignOut: () -> Unit, modifier: Modifier = Modifier) {
    TopAppBar {
        Row (horizontalArrangement = Arrangement.End, modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()) {
            UserMenu(user, onClickListener = onSignOut, modifier = modifier)
        }
    }
}

@Composable
fun UserMenu(user: String, onClickListener: () -> Unit, modifier: Modifier = Modifier) {
    val showOptions = remember { mutableStateOf(false) }
    Box(modifier = Modifier.wrapContentSize(Alignment.TopEnd)) {

        IconButton(onClick = { showOptions.value = true }, modifier = modifier.testTag("UserIcon")) {
            Icon(Icons.Rounded.Person, "User profile", modifier = modifier
                .clip(CircleShape)
                .background(Color.Gray)
            )
        }

        DropdownMenu(expanded = showOptions.value, onDismissRequest = { showOptions.value = false }, modifier = Modifier.testTag("OptionsMenu")) {
            Text(text = user)
            DropdownMenuItem(onClick = {onClickListener(); showOptions.value = false}, modifier = Modifier.testTag("SignOut")) {
                Text(text = stringResource(id = R.string.sign_out))
            }
        }
    }
}

@Composable
fun SignOutDialog(onSignOut: () -> Unit, onDismissDialog: () -> Unit, modifier: Modifier = Modifier) {
    val isSigningOut = remember { mutableStateOf(false)}
    Surface(color = colorResource(id = R.color.transparent_black), modifier = modifier
        .fillMaxSize()
        ) {
        AlertDialog(
            title = { Text(text = stringResource(id = if (!isSigningOut.value) R.string.dialog_signout_title else R.string.dialog_signout_progress_bar_tittle))},
            text = {
                if (!isSigningOut.value) {
                    Text( text = stringResource(id = R.string.dialog_signout_text))
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                        CircularProgressIndicator(modifier = modifier.padding(16.dp))

                    }
                }
                   },
            confirmButton = { if (!isSigningOut.value) TextButton(onClick = { isSigningOut.value = true; onSignOut() }) { Text(text = stringResource(id = R.string.dialog_signout_positive_button))} },
            dismissButton = { if (!isSigningOut.value) TextButton(onClick = onDismissDialog) { Text(text = stringResource(id = R.string.dialog_signout_negative_button)) }},
            onDismissRequest = { /*TODO*/ })
    }
}

@Composable
fun SigningOutProgressBar(modifier: Modifier = Modifier) {
    Surface (shape = MaterialTheme.shapes.medium, modifier = modifier.wrapContentHeight()){
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.padding(8.dp)) {
            Text(text = stringResource(id = R.string.dialog_signout_progress_bar_tittle), style = MaterialTheme.typography.h4)
            CircularProgressIndicator(modifier = modifier.padding(16.dp))
        }
    }
}

@Preview
@Composable
fun PreviewSigningOutProgressBar() {
    HazelTheme {
        SigningOutProgressBar()
    }
}

@Preview
@Composable
fun SignOutDialogPreview() {
    HazelTheme {
        SignOutDialog({}, {})
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HazelTheme() {
        HomeScreen()
    }
}