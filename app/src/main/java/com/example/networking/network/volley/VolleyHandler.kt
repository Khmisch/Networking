package com.example.networking.network.volley

import java.net.CacheResponse

interface VolleyHandler {
    fun onSuccess(response: String?)
    fun onError(error: String?)
}