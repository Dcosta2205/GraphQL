package com.graphql.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.graphql.ui.theme.GraphQLTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        setContent {
            GraphQLTheme {
                // A surface container using the 'background' color from the theme
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    NameTextField()
                    SaveButton()
                    UserListButton(viewModel = userViewModel)
                }
            }
        }
    }
}

@Composable
private fun NameTextField() {
    val name = remember { mutableStateOf(TextFieldValue("")) }
    OutlinedTextField(value = name.value,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Enter Name") },
        placeholder = { Text(text = "What's your name") },
        onValueChange = {
            name.value = it
        })
}

@Composable
private fun ColumnScope.SaveButton() {
    Button(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)
    ) {
        Text(text = "Save Username")
    }
}

@Composable
private fun ColumnScope.UserListButton(viewModel: UserViewModel) {
    val clicked = remember { mutableStateOf(false) }
    Button(
        onClick = {
            clicked.value = !clicked.value
        },
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)
    ) {
        Text(text = "Display Users")
    }
    if (clicked.value) DisplayUsers(viewModel = viewModel)
}

@Composable
private fun DisplayUsers(viewModel: UserViewModel) {
    LazyColumn {
        items(items = viewModel.userList.value) { user ->
            UserName(userName = user.name ?: "Null")
        }
    }
}

@Composable
private fun UserName(userName: String) {
    Text(text = userName)
}