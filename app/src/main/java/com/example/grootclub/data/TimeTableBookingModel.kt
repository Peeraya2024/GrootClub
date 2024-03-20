package com.example.grootclub.data
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName
class TimeTableBookingModel : ArrayList<TimeTableBookingModelItem>()

@Serializable
data class TimeTableBookingModelItem(
    @SerialName("courtNumber")
    val courtNumber: Int,
    @SerialName("_id")
    val id: String,
    @SerialName("slots")
    val slots: List<Slot>,
    var isButtonSelected: Boolean = false
)

@Serializable
data class Slot(
    @SerialName("Booker")
    val booker: String,
    @SerialName("endTime")
    val endTime: String,
    @SerialName("_id")
    val id: String,
    @SerialName("isBooked")
    val isBooked: Boolean,
    @SerialName("startTime")
    val startTime: String
)