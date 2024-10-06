package com.paraskcd.unitedsetups

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.paraskcd.unitedsetups.core.common.TopLevelRoute
import com.paraskcd.unitedsetups.presentation.authentication.AuthenticationView
import com.paraskcd.unitedsetups.presentation.authentication.AuthenticationViewModel
import com.paraskcd.unitedsetups.presentation.main.MainView
import com.paraskcd.unitedsetups.ui.theme.DarkColorScheme
import com.paraskcd.unitedsetups.ui.theme.UnitedSetupsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainViewModel: MainActivityViewModel = hiltViewModel()
            val authenticationViewModel: AuthenticationViewModel = hiltViewModel()
            val navController = rememberNavController()
            val topLevelRoutes = listOf(
                TopLevelRoute("Home", "Home", Icons.Filled.Home)
            )
            var navigationSelectedItem by remember {
                mutableStateOf(0)
            }

            UnitedSetupsTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        if (authenticationViewModel.isLoggedIn || mainViewModel.isLoggedIn) {
                            TopAppBar(
                                title = {
                                    Image(
                                        painter = painterResource(id = R.drawable.uslogowhite),
                                        contentDescription = "Logo",
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier.fillMaxWidth().height(64.dp)
                                    )
                                },
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = DarkColorScheme.surface
                                ),
                                modifier = Modifier.shadow(16.dp)
                            )
                        }
                    },
                    bottomBar = {
                        if (authenticationViewModel.isLoggedIn || mainViewModel.isLoggedIn) {
                            NavigationBar(
                                containerColor = DarkColorScheme.surface,
                                contentColor = DarkColorScheme.background
                            ) {
                                topLevelRoutes.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        colors = NavigationBarItemDefaults.colors(
                                            indicatorColor = DarkColorScheme.background
                                        ),
                                        selected = index == navigationSelectedItem,
                                        label = {
                                            Text(item.name)
                                        },
                                        icon = {
                                            Icon(item.icon, contentDescription = item.name)
                                        },
                                        onClick = {
                                            navigationSelectedItem = index
                                            navController.navigate(item.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    if (authenticationViewModel.isLoggedIn || mainViewModel.isLoggedIn) {
                        MainView(
                            modifier = Modifier.padding(innerPadding),
                            navController
                        )
                    } else {
                        AuthenticationView(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize(),
                            authenticationViewModel
                        )
                    }
                }
            }
        }
    }
}