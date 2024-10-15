package com.paraskcd.unitedsetups

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.paraskcd.unitedsetups.core.common.TopLevelRoute
import com.paraskcd.unitedsetups.presentation.authentication.AuthenticationView
import com.paraskcd.unitedsetups.presentation.authentication.AuthenticationViewModel
import com.paraskcd.unitedsetups.presentation.main.MainView
import com.paraskcd.unitedsetups.presentation.main.screens.home.HomeViewModel
import com.paraskcd.unitedsetups.ui.theme.DarkColorScheme
import com.paraskcd.unitedsetups.ui.theme.UnitedSetupsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                DarkColorScheme.surface.hashCode()
            ),
            navigationBarStyle = SystemBarStyle.dark(
                DarkColorScheme.surface.hashCode()
            )
        )
        setContent {
            val mainViewModel: MainActivityViewModel = hiltViewModel()
            val authenticationViewModel: AuthenticationViewModel = hiltViewModel()
            val homeViewModel: HomeViewModel = hiltViewModel()

            val navController = rememberNavController()
            val topLevelRoutes = listOf(
                TopLevelRoute("Home", "Home", Icons.Filled.Home),
                TopLevelRoute("Profile", "Profile", Icons.Filled.Person)
            )
            var navigationSelectedItem by remember {
                mutableStateOf(0)
            }

            val currentBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute by remember { derivedStateOf { currentBackStackEntry?.destination?.route ?: "Home" } }
            val currentRouteId by remember { derivedStateOf { currentBackStackEntry?.destination?.id } }

            UnitedSetupsTheme {
                if (authenticationViewModel.isLoggedIn || mainViewModel.isLoggedIn) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        floatingActionButton = {
                            if (topLevelRoutes.any { it.route == currentRoute }
                                && (authenticationViewModel.isLoggedIn || mainViewModel.isLoggedIn)) {
                                if (currentRouteId != null) {
                                    FloatingActionButton(
                                        onClick = { navController.navigate("NewPost") {
                                            popUpTo(currentRouteId!!) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        } },
                                        containerColor = DarkColorScheme.primary
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.EditNote,
                                            contentDescription = "New Post"
                                        )
                                    }
                                }
                            }
                        },
                        topBar = {
                            AnimatedVisibility(
                                visible = (topLevelRoutes.any { currentRoute.contains(it.route) }) == true,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                TopAppBar(
                                    title = {
                                        Image(
                                            painter = painterResource(id = R.drawable.uslogowhite),
                                            contentDescription = "Logo",
                                            contentScale = ContentScale.Fit,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(64.dp)
                                        )
                                    },
                                    colors = TopAppBarDefaults.topAppBarColors(
                                        containerColor = DarkColorScheme.surface
                                    ),
                                    modifier = Modifier.shadow(16.dp)
                                )
                            }
                            AnimatedVisibility(
                                visible = (topLevelRoutes.any { currentRoute.contains(it.route) }) == false && currentRoute == "NewPost",
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                TopAppBar(
                                    title = {
                                        Text("New Post")
                                    },
                                    colors = TopAppBarDefaults.topAppBarColors(
                                        containerColor = DarkColorScheme.surface
                                    ),
                                    navigationIcon = {
                                        IconButton(onClick = { navController.popBackStack() }) {
                                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                                        }
                                    }
                                )
                            }
                            AnimatedVisibility(
                                visible = (topLevelRoutes.any { currentRoute.contains(it.route) }) == false && currentRoute.contains("PostImage"),
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                TopAppBar(
                                    title = {
                                        Text("")
                                    },
                                    colors = TopAppBarDefaults.topAppBarColors(
                                        containerColor = DarkColorScheme.surface
                                    ),
                                    navigationIcon = {
                                        IconButton(onClick = { navController.popBackStack() }) {
                                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                                        }
                                    }
                                )
                            }
                        },
                        bottomBar = {
                            AnimatedVisibility(
                                visible = (topLevelRoutes.any { currentRoute.contains(it.route) }) == true,
                                enter = expandVertically(expandFrom = Alignment.Top),
                                exit = shrinkVertically(shrinkTowards = Alignment.Top)
                            ) {
                                NavigationBar(
                                    containerColor = DarkColorScheme.surface,
                                    contentColor = DarkColorScheme.background
                                ) {
                                    topLevelRoutes.forEachIndexed { index, item ->
                                        NavigationBarItem(
                                            colors = NavigationBarItemDefaults.colors(
                                                indicatorColor = DarkColorScheme.background
                                            ),
                                            selected = currentRoute.contains(item.route),
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
                        MainView(
                            modifier = Modifier.padding(innerPadding),
                            navController = navController,
                            homeViewModel = homeViewModel
                        )
                    }
                } else {
                    AuthenticationView(
                        modifier = Modifier.fillMaxSize(),
                        authenticationViewModel = authenticationViewModel
                    )
                }

            }
        }
    }
}