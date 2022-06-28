package com.example.mvicompose.common

import android.content.Context
import android.content.ContextWrapper
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.net.toUri
import androidx.navigation.*
import com.squareup.moshi.Moshi

inline fun <reified T> NavController.navigate(
    route: String,
    data: Pair<String, T>,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    val count = route
        .split("{${data.first}}")
        .size
        .dec()

    if (count != 1) {
        throw IllegalArgumentException()
    }

    val out = Moshi.Builder()
        .build()
        .adapter(T::class.java)
        .toJson(data.second)
    val newRoute = route.replace(
        oldValue = "{${data.first}}",
        newValue = Uri.encode(out),
    )

    navigate(
        request = NavDeepLinkRequest.Builder
            .fromUri(NavDestination.createRoute(route = newRoute).toUri())
            .build(),
        navOptions = navOptions,
        navigatorExtras = navigatorExtras,
    )
}

inline fun <reified T> NavBackStackEntry.getData(key: String): T? {
    val data = arguments?.getString(key)

    return when {
        data != null -> Moshi.Builder()
            .build()
            .adapter(T::class.java)
            .fromJson(data)
        else -> null
    }
}

@Composable
inline fun <reified T> NavBackStackEntry.rememberGetData(key: String): T? {
    return remember { getData<T>(key) }
}

inline fun <reified Activity : android.app.Activity> Context.getActivity(): Activity? {
    return when (this) {
        is Activity -> this
        else -> {
            var context = this
            while (context is ContextWrapper) {
                context = context.baseContext
                if (context is Activity) return context
            }
            null
        }
    }
}