package com.example.materialdesign

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class LoginActivity : ComponentActivity() {
    private lateinit var databaseHelper: UserDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the UserDatabaseHelper with the context of the activity
        databaseHelper = UserDatabaseHelper(this)  // 'this' is the context of the Activity

        // Set the content for the screen
        setContent {
            // Pass the context and databaseHelper to the composable
            LoginScreen(context = this, databaseHelper = databaseHelper)
        }
    }
}

@Composable
fun LoginScreen(context: Context, databaseHelper: UserDatabaseHelper) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Add an image for the login screen
        Image(painterResource(id = R.drawable.study_login), contentDescription = "")

        // Title for the login screen
        Text(
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.Cursive,
            text = "Login"
        )

        // Spacer between elements
        Spacer(modifier = Modifier.height(10.dp))

        // Username input field
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier
                .padding(10.dp)
                .width(280.dp)
        )

        // Password input field
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .padding(10.dp)
                .width(280.dp)
        )

        // Display error message if there is an issue
        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }

        // Login Button
        Button(
            onClick = {
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    // Try to get user data from the database
                    val user = databaseHelper.getUserByUsername(username)

                    // Check if user exists and the password matches
                    if (user != null && user.password == password) {
                        // On successful login, navigate to MainActivity
                        error = "Successfully logged in"
                        context.startActivity(
                            Intent(context, MainActivity::class.java)
                        )
                    } else {
                        error = "Invalid username or password"
                    }
                } else {
                    error = "Please fill all fields"
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Login")
        }

        // Register and Forgot Password Row
        Row(modifier = Modifier.padding(top = 16.dp)) {
            // Register Button
            TextButton(onClick = {
                context.startActivity(
                    Intent(context, RegisterActivity::class.java)
                )
            }) {
                Text(text = "Register")
            }

            Spacer(modifier = Modifier.width(60.dp))

            // Forgot Password Button
            TextButton(onClick = {
                // Handle forgot password logic if required
            }) {
                Text(text = "Forget password?")
            }
        }
    }
}
