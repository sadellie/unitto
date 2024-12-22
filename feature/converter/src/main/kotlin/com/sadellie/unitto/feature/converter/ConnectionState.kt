/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2024 Elshan Agaev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.sadellie.unitto.feature.converter

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

// https://github.com/PatilShreyas/NotyKT/pull/210/files#diff-88d0c098edd51dfdd06d871b22de23efe0935234fe80b2b54592583262fbe846

internal sealed class ConnectionState {
  data object Available : ConnectionState()

  data object Unavailable : ConnectionState()
}

private fun Context.observeConnectivityAsFlow() = callbackFlow {
  val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

  val callback =
    object : ConnectivityManager.NetworkCallback() {
      override fun onAvailable(network: Network) {
        trySend(ConnectionState.Available)
      }

      override fun onLost(network: Network) {
        trySend(ConnectionState.Unavailable)
      }
    }

  val networkRequest =
    NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()

  connectivityManager.registerNetworkCallback(networkRequest, callback)

  awaitClose { connectivityManager.unregisterNetworkCallback(callback) }
}

@Composable
internal fun connectivityState(): State<ConnectionState> {
  val context = LocalContext.current

  return context
    .observeConnectivityAsFlow()
    .collectAsStateWithLifecycle(ConnectionState.Unavailable)
}
