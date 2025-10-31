package com.personal.notes.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavBackStackEntry

data class NavigationTransitions(
    val enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>) -> EnterTransition,
    val exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>) -> ExitTransition,
    val popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>) -> EnterTransition,
    val popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>) -> ExitTransition
)