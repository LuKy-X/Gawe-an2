package com.example.gawe_an2.util

import com.example.gawe_an2.model.User
import org.json.JSONObject

object LoginMapper {
    fun map(json: JSONObject): User {
        val data = json.getJSONObject("data")

        return User(
            id = data.getInt("id"),
            profilePicture = data.getString("profilePicture"),
            fullname = data.getString("fullname"),
            email = data.getString("email"),
            phoneNumber = data.getString("phoneNumber"),
            role = data.getString("role")
        )
    }
}