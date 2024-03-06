package com.example.grootclub.utils

import com.orhanobut.hawk.Hawk

object GlobalVar {

//    const val typeSpot = "tennisCourt"
//    const val selectDate = "2024-02-27"
//    const val timeTableBooking = "/${typeSpot}/${selectDate}"
val timeTableBooking: String
    get() = "/${typeSpot}/${selectDate}"
    var email: String
        get() = Hawk.get("email", "")
        set(value) {
            Hawk.put("email", value)
        }

    var typeSpot: String
        get() = Hawk.get("typeSpot", "")
        set(value) {
            Hawk.put("typeSpot", value)
        }
    var selectDate: String
        get() = Hawk.get("selectDate", "")
        set(value) {
            Hawk.put("selectDate", value)
        }
    fun deleteHawk(str: String) {
        Hawk.delete(str)
    }



    val typeSpotList = arrayOf("Tennis", "Table Tennis", "Badminton", "Yoga", "Aerobic")

}