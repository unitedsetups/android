package com.paraskcd.unitedsetups.presentation.authentication

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import com.paraskcd.unitedsetups.presentation.components.EmailTextField
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.paraskcd.unitedsetups.R
import com.paraskcd.unitedsetups.ui.theme.DarkColorScheme
import com.paraskcd.unitedsetups.ui.theme.NoRippleConfiguration
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticationView(modifier: Modifier = Modifier, authenticationViewModel: AuthenticationViewModel) {
    var register by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }
    val composableScope = rememberCoroutineScope()

    BackHandler(enabled = register) {
        register = false
    }

    Box(modifier = Modifier.fillMaxSize().background(DarkColorScheme.background)) {
        Image(
            painter = painterResource(id = R.drawable.gradient_portrait_background),
            contentDescription = "BG Image",
            alignment = Alignment.BottomCenter,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().height((256).dp)
        )
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .shadow(elevation = 16.dp)
                    .background(
                        DarkColorScheme.surface,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
                    .animateContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.uslogowhite),
                    contentDescription = "Logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxWidth().height(96.dp).padding(bottom = 16.dp)
                )
                if(register) {
                    TextField(
                        value = authenticationViewModel.username,
                        onValueChange = { username -> authenticationViewModel.updateUsername(username) },
                        label = { Text("Username") },
                        singleLine = true,
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.Person, contentDescription = "Username Icon")
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = DarkColorScheme.error
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                            .background(
                                color = DarkColorScheme.background,
                                shape = RoundedCornerShape(16.dp)
                            )
                    )
                    TextField(
                        value = authenticationViewModel.name,
                        onValueChange = { name -> authenticationViewModel.updateName(name) },
                        label = { Text("Display Name") },
                        singleLine = true,
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.Person, contentDescription = "Username Icon")
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = DarkColorScheme.error
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                            .background(
                                color = DarkColorScheme.background,
                                shape = RoundedCornerShape(16.dp)
                            )
                    )
                }
                EmailTextField(
                    value = authenticationViewModel.email,
                    onValueChange = { email -> authenticationViewModel.updateEmail(email) },
                    label = "Email",
                    icon = Icons.Filled.Email,
                    imageDescription = "Email Icon"
                )
                TextField(
                    value = authenticationViewModel.password,
                    onValueChange = { password -> authenticationViewModel.updatePassword(password) },
                    label = { Text("Password") },
                    visualTransformation =
                    if (showPassword) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        if (showPassword) {
                            IconButton(onClick = { showPassword = false }) {
                                Icon(
                                    imageVector = Icons.Filled.Visibility,
                                    contentDescription = "Password show Icon"
                                )
                            }
                        } else {
                            IconButton(onClick = { showPassword = true }) {
                                Icon(
                                    imageVector = Icons.Filled.VisibilityOff,
                                    contentDescription = "Password hide Icon"
                                )
                            }
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = DarkColorScheme.error
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .background(
                            color = DarkColorScheme.background,
                            shape = RoundedCornerShape(16.dp)
                        )
                )
                Button(
                    onClick = {
                        composableScope.launch {
                            if (register) {
                                val error = authenticationViewModel.register()
                                if (error == null) {
                                    register = false
                                }
                            } else {
                                authenticationViewModel.login()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 48.dp),
                    shape = RoundedCornerShape(16.dp),
                    enabled =
                        if (register) {
                            authenticationViewModel.username.isNotBlank()
                                    && authenticationViewModel.name.isNotBlank()
                                    && authenticationViewModel.email.isNotBlank()
                                    && authenticationViewModel.password.isNotBlank()
                        } else {
                            authenticationViewModel.email.isNotBlank()
                                    && authenticationViewModel.password.isNotBlank()
                        }
                ) {
                    if (register) {
                        Text("Register")
                    } else {
                        Text("Login")
                    }
                }
                CompositionLocalProvider(LocalRippleConfiguration provides NoRippleConfiguration) {
                    TextButton(
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = {
                            register = !register
                            authenticationViewModel.updateEmail("")
                            authenticationViewModel.updatePassword("")
                            authenticationViewModel.updateName("")
                            authenticationViewModel.updateUsername("")
                        }
                    ) {
                        if (register) {
                            Text("Already have an account? Tap here to login")
                        } else {
                            Text("Don't have an account? Tap here to register")
                        }
                    }
                }
            }
        }
    }
}