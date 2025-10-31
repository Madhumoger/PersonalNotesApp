package com.personal.madNotex.core.common

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry
import com.personal.madNotex.core.navigation.NavigationTransitions
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

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

    // Formats an Instant to display date only(e.g., "2024-01-15")
    fun formatDate(instant: Instant): String {
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        return "${localDateTime.date}"
    }

    //Formats a timestamp (Long) to display date only (e.g., "2024-01-15")
    fun formatDate(timestamp: Long): String {
        val instant = Instant.fromEpochMilliseconds(timestamp)
        return formatDate(instant)
    }

    //Formats an Instant to display date and time (e.g., "2024-01-15 14:30")
    fun formatDateTime(instant: Instant): String {
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        return "${localDateTime.date} ${localDateTime.hour.toString().padStart(2, '0')}:${
            localDateTime.minute.toString().padStart(2, '0')
        }"
    }

    //Formats a timestamp (Long) to display date and time (e.g., "2024-01-15 14:30")
    fun formatDateTime(timestamp: Long): String {
        val instant = Instant.fromEpochMilliseconds(timestamp)
        return formatDateTime(instant)
    }
}

