package com.example.plantingapp.data.remote

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

internal class CookieInterceptor : Interceptor {
    private var cookie: String? = null
    fun setSessionCookie(cookie: String?) {
        this.cookie = cookie
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        if (cookie != null) {
            request = request.newBuilder()
                .header("Cookie", cookie!!)
                .build()
        }
        return chain.proceed(request)
    }
}