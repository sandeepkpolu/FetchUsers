package com.sandeep.fetchusers.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.sandeep.fetchusers.utils.NetworkUtils.isInternetConnected
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NetworkUtilTest {

    private lateinit var context: Context
    private lateinit var connectivityManager: ConnectivityManager

    @Before
    fun setup() {
        context = mock(Context::class.java)
        connectivityManager = mock(ConnectivityManager::class.java)
        `when`(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(
            connectivityManager
        )
    }

    @Test
    fun `isInternetConnected returns true when connected to WiFi on`() {
        val network = mock(Network::class.java)
        val networkCapabilities = mock(NetworkCapabilities::class.java)
        `when`(connectivityManager.activeNetwork).thenReturn(network)
        `when`(connectivityManager.getNetworkCapabilities(network)).thenReturn(networkCapabilities)
        `when`(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)).thenReturn(true)
        assertTrue(isInternetConnected(context))
    }

    @Test
    fun `isInternetConnected returns true when connected to Mobile Data on`() {
        val network = mock(Network::class.java)
        val networkCapabilities = mock(NetworkCapabilities::class.java)
        `when`(connectivityManager.activeNetwork).thenReturn(network)
        `when`(connectivityManager.getNetworkCapabilities(network)).thenReturn(networkCapabilities)
        `when`(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)).thenReturn(
            true
        )
        assertTrue(isInternetConnected(context))
    }

    @Test
    fun `isInternetConnected returns false when no network is available`() {
        `when`(connectivityManager.activeNetwork).thenReturn(null)
        assertFalse(isInternetConnected(context))
    }
}