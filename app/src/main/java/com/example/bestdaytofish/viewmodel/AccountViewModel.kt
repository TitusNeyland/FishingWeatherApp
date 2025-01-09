package com.example.bestdaytofish.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AccountViewModel : ViewModel() {
    var email by mutableStateOf("")
        private set
        
    var password by mutableStateOf("")
        private set
        
    var isLoggedIn by mutableStateOf(false)
        private set
        
    var error by mutableStateOf<String?>(null)
        private set
        
    fun updateEmail(newEmail: String) {
        email = newEmail
        error = null
    }
    
    fun updatePassword(newPassword: String) {
        password = newPassword
        error = null
    }
    
    fun login() {
        // This is a mock login - in a real app, you'd want to implement actual authentication
        if (email.isNotEmpty() && password.isNotEmpty()) {
            isLoggedIn = true
            error = null
        } else {
            error = "Please fill in all fields"
        }
    }
    
    fun logout() {
        isLoggedIn = false
        email = ""
        password = ""
        error = null
    }
} 