package com.example.restorumba

import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.logging.Level
import java.util.logging.Logger

class APIService {
    companion object {
        const val BASE_URL = "http://localhost:8081"
    }

    private fun getUser(url: String, timeout: Int = 10000): String? {
        var c: HttpURLConnection? = null
        try {
            val u = URL(url)
            c = u.openConnection() as HttpURLConnection
            c.setRequestMethod("POST")
            c.setRequestProperty("Content-length", "0")
            c.setUseCaches(false)
            c.setAllowUserInteraction(false)
            c.setConnectTimeout(timeout)
            c.setReadTimeout(timeout)
            c.connect()
            val status: Int = c.getResponseCode()
            when (status) {
                200, 201 -> {
                    val streamReader = InputStreamReader(c.inputStream)
                    var text = ""
                    streamReader.use {
                        text = it.readText()
                    }
                    return text
                }
            }
        } catch (ex: MalformedURLException) {
            Logger.getLogger(javaClass.name).log(Level.SEVERE, null, ex)
        } catch (ex: IOException) {
            Logger.getLogger(javaClass.name).log(Level.SEVERE, null, ex)
        } finally {
            if (c != null) {
                try {
                    c.disconnect()
                } catch (ex: Exception) {
                    Logger.getLogger(javaClass.name).log(Level.SEVERE, null, ex)
                }
            }
        }
        return null
    }

    fun getUserToken(id: Int): User? {
        val response =  getUser(
            "${BASE_URL}/register"
        )
        var jsonObject: JSONObject? = null
        try {
            jsonObject = response?.let { JSONObject(it) }
            return jsonObject?.let { convertToUser(it) }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

    private fun convertToUser(jsonObject: JSONObject): User {
        return User(
            jsonObject.getString("jwtToken")
        )
    }
}