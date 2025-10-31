package com.personal.notes.utils

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry
import com.personal.notes.ui.navigation.NavigationTransitions

object GeneralUtils {

    fun getDefaultNavigationTransitions(
        duration: Int = 300,
    ): NavigationTransitions {
        return NavigationTransitions(
            enterTransition = { _: AnimatedContentTransitionScope<NavBackStackEntry> ->
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(duration)
                )
            },
            exitTransition = { _: AnimatedContentTransitionScope<NavBackStackEntry> ->
                fadeOut(animationSpec = tween(0))
            },
            popEnterTransition = { _: AnimatedContentTransitionScope<NavBackStackEntry> ->
                fadeIn(animationSpec = tween(0))
            },
            popExitTransition = { _: AnimatedContentTransitionScope<NavBackStackEntry> ->
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(duration)
                )
            }
        )
    }
}