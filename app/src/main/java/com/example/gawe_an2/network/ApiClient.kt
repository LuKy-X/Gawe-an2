package com.example.gawe_an2.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object ApiClient {
    suspend fun request(
        url: String,
        method: HttpMethod,
        body: JSONObject? = null
    ): ApiResponse<JSONObject> {

        return withContext(Dispatchers.IO) {
            try {
                val conn = URL(url).openConnection() as HttpURLConnection
                conn.requestMethod = method.name
                conn.setRequestProperty("Content-Type", "application/json")
                conn.setRequestProperty("Accept", "application/json")

                if (method != HttpMethod.GET && body != null) {
                    conn.doOutput = true
                    conn.outputStream.write(body.toString().toByteArray())
                }

                val reader = BufferedReader(
                    InputStreamReader(
                        if (conn.responseCode in 200..299)
                            conn.inputStream
                        else
                            conn.errorStream
                    )
                )

                val response = reader.readText()
                reader.close()

                if (conn.responseCode in 200..299) {
                    ApiResponse.Success(JSONObject(response))
                } else {
                    ApiResponse.Error(
                        JSONObject(response).optString("message", "Error")
                    )
                }
            } catch (e: Exception) {
                ApiResponse.Error(e.message ?: "Network Error")
            }
        }
    }
}