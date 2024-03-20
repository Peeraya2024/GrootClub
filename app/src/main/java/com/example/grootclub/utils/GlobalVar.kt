package com.example.grootclub.utils

import com.example.grootclub.data.MockData
import com.orhanobut.hawk.Hawk

object GlobalVar {
    var bookingInfo: MockData.Companion.BookingInformation
        get() = MockData.Companion.BookingInformation(
            sportName = Hawk.get("sportName"),
            courtNumber = Hawk.get("courtNumber", 0),
            date  = Hawk.get("date"),
            time = Hawk.get("time"),
            coach = Hawk.get("coach")
        )
        set(value) {
            Hawk.put("sportName", value.sportName)
            Hawk.put("courtNumber", value.courtNumber)
            Hawk.put("date", value.date)
            Hawk.put("time", value.time)
            Hawk.put("coach", value.coach)
        }

//    var sportName: String
//        get() = Hawk.get("sportName", "")
//        set(value) {
//            Hawk.put("sportName", value)
//        }
//    var courtNumber: String
//        get() = Hawk.get("courtNumber", "")
//        set(value) {
//            Hawk.put("courtNumber", value)
//        }
//    var date: String
//        get() = Hawk.get("date", "")
//        set(value) {
//            Hawk.put("date", value)
//        }

    fun saveHawk(str: String, value: String) {
        Hawk.put(str, value)
    }

    fun getHawk(str: String): String {
        return Hawk.get(str, "")
    }

    fun clearHawk() {
        Hawk.deleteAll()
    }

    fun clearBookingHawk() {
        Hawk.delete("sportName")
        Hawk.delete("courtNumber")
        Hawk.delete("date")
        Hawk.delete("time")
        Hawk.delete("coach")
    }

    fun deleteHawk(str: String) {
        Hawk.delete(str)
    }

}