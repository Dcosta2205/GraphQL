package com.graphql.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.exception.ApolloException
import com.graphql.data.network.Network
import com.lloyd.graphql.UsersListQuery
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    val userList: MutableState<List<UsersListQuery.User>> = mutableStateOf(emptyList())

    init {
        fetchUserList()
    }

    private fun fetchUserList() {
        viewModelScope.launch {
            val client = Network.get()
            try {
                val response = client.query(UsersListQuery()).execute()
                userList.value = response.data?.users ?: emptyList()
            } catch (e: ApolloException) {
                userList.value = emptyList()
            }
        }
    }
}