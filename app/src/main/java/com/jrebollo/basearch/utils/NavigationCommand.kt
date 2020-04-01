package com.jrebollo.basearch.utils

import android.os.Bundle
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

sealed class NavigationCommand {
    data class To(val directions: NavDirections) : NavigationCommand()
    object Back : NavigationCommand()
    data class BackTo(val destinationId: Int) : NavigationCommand()
    object ToRoot : NavigationCommand()
    data class WithArgs(
        val directions: NavDirections,
        val args: Bundle,
        val navOptions: NavOptions
    ) : NavigationCommand()
}