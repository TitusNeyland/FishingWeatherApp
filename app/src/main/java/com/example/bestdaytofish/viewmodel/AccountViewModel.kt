package com.example.bestdaytofish.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AccountViewModel : ViewModel() {
    var firstName by mutableStateOf("")
        private set
        
    var lastName by mutableStateOf("")
        private set
        
    var email by mutableStateOf("")
        private set
        
    var password by mutableStateOf("")
        private set
        
    var confirmPassword by mutableStateOf("")
        private set
        
    var isLoggedIn by mutableStateOf(false)
        private set
        
    var isSignUpMode by mutableStateOf(false)
        private set
        
    var error by mutableStateOf<String?>(null)
        private set
        
    fun updateFirstName(newFirstName: String) {
        firstName = newFirstName
        error = null
    }
    
    fun updateLastName(newLastName: String) {
        lastName = newLastName
        error = null
    }
    
    fun updateEmail(newEmail: String) {
        email = newEmail
        error = null
    }
    
    fun updatePassword(newPassword: String) {
        password = newPassword
        error = null
    }
    
    fun updateConfirmPassword(newPassword: String) {
        confirmPassword = newPassword
        error = null
    }
    
    fun toggleSignUpMode() {
        isSignUpMode = !isSignUpMode
        clearFields()
    }
    
    private fun clearFields() {
        firstName = ""
        lastName = ""
        email = ""
        password = ""
        confirmPassword = ""
        error = null
    }
    
    fun login() {
        if (email.isEmpty() || password.isEmpty()) {
            error = "Please fill in all fields"
            return
        }
        // This is a mock login - in a real app, you'd want to implement actual authentication
        isLoggedIn = true
        error = null
    }
    
    fun signUp() {
        when {
            firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || 
            password.isEmpty() || confirmPassword.isEmpty() -> {
                error = "Please fill in all fields"
            }
            !email.contains("@") -> {
                error = "Please enter a valid email address"
            }
            password.length < 6 -> {
                error = "Password must be at least 6 characters long"
            }
            password != confirmPassword -> {
                error = "Passwords do not match"
            }
            else -> {
                // This is a mock sign-up - in a real app, you'd want to implement actual user creation
                isLoggedIn = true
                isSignUpMode = false
                error = null
            }
        }
    }
    
    fun logout() {
        isLoggedIn = false
        clearFields()
    }
} 