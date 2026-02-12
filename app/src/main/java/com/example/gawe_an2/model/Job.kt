package com.example.gawe_an2.model


data class Job(
    val id: Int,
    val name: String,
    val company: Company,
    val locationType: String,
    val locationRegion: String,
    val yearOfExperience: String,
    val quota: Int
)
