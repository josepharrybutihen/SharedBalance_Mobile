package com.example.sharedbalance.data.api

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()

        // ✅ Skip login/signup
        val path = original.url.encodedPath

        if (
            path.contains("/login") ||
            path.contains("/signup")
        ) {
            return chain.proceed(original)
        }

        val prefs = context.getSharedPreferences(
            "sharedbalance",
            Context.MODE_PRIVATE
        )

        val token = prefs.getString("token", null)

        Log.d("AUTH", "TOKEN IN PREFS = $token")
        Log.d("AUTH", "Interceptor running for: ${original.url}")
        Log.d("AUTH_CHECK", "TOKEN = $token")

        if (token.isNullOrEmpty()) {
            Log.e("AUTH", "NO TOKEN FOUND - REQUEST WILL FAIL")
        }

        val builder = original.newBuilder()

        if (!token.isNullOrEmpty()) {
            builder.addHeader(
                "Authorization",
                "Bearer $token"
            )
        }

        return chain.proceed(builder.build())
    }
}