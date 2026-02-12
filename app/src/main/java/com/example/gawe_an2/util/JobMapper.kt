package com.example.gawe_an2.util

import com.example.gawe_an2.model.Company
import com.example.gawe_an2.model.Job
import org.json.JSONObject

object JobMapper {
    fun mapList(json: JSONObject): List<Job> {
        val list = mutableListOf<Job>()
        val array = json.getJSONArray("data")

        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            val companyObj = obj.getJSONObject("company")

            val job = Job(
                id = obj.getInt("id"),
                name = obj.getString("name"),
                company = Company(
                    name = obj.getString("name")
                ),
                locationType = obj.getString("locationType"),
                locationRegion = obj.getString("locationRegion"),
                yearOfExperience = obj.getString("yearOfExperience"),
                quota = obj.getInt("quota")
            )

            list.add(job)
        }
        return list
    }
}