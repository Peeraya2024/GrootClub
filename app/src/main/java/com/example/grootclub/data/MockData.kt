package com.example.grootclub.data

import com.example.grootclub.R
import kotlin.random.Random

class MockData {
    companion object {
        fun generateSpotOptions(): List<String> {
            return listOf("Tennis", "Table Tennis", "Badminton", "Yoga", "Aerobic", "Table Tennis", "Badminton")
        }

        val nameSpotList = arrayOf(" ", "A-Z", "Z-A")

        val typeSpotList = arrayOf("Tennis", "Table Tennis", "Badminton", "Yoga", "Aerobic")


        val serviceRules = listOf(
            ServiceRulesData("1", "1. Time Limit", "Each session or round of play is limited to a maximum duration of 1 hour."),
            ServiceRulesData("2", "2. Booking Requirement", "Each session or round of play is limited to a maximum duration of 1 hour."),
            ServiceRulesData("3", "3. Booking Confirmation", "Each session or round of play is limited to a maximum duration of 1 hour."),
            ServiceRulesData("4", "4. Time Limit", "Each session or round of play is limited to a maximum duration of 1 hour."),
            ServiceRulesData("5", "5. Time Limit", "Each session or round of play is limited to a maximum duration of 1 hour."),
            ServiceRulesData("6", "6. Time Limit", "Each session or round of play is limited to a maximum duration of 1 hour."),
            ServiceRulesData("7", "7. Time Limit", "Each session or round of play is limited to a maximum duration of 1 hour."),
            ServiceRulesData("8", "8. Time Limit", "Each session or round of play is limited to a maximum duration of 1 hour."),
            ServiceRulesData("9", "9. Time Limit", "Each session or round of play is limited to a maximum duration of 1 hour."),
            ServiceRulesData("10", "10. Time Limit", "Each session or round of play is limited to a maximum duration of 1 hour."),
            ServiceRulesData("11", "11. Time Limit", "Each session or round of play is limited to a maximum duration of 1 hour."),
            ServiceRulesData("12", "12. Time Limit", "Each session or round of play is limited to a maximum duration of 1 hour."),
            ServiceRulesData("13", "13. Time Limit", "Each session or round of play is limited to a maximum duration of 1 hour."),
            ServiceRulesData("14", "14. Time Limit", "Each session or round of play is limited to a maximum duration of 1 hour."),
            )

        var imageList = arrayListOf(
            R.drawable.logo, R.drawable.arrow_drop_down, R.drawable.calendar_month_24, R.drawable.logo
        )

        fun generateRandomGender(): String {
            val spotOptions = generateSpotOptions()
            val randomIndex = Random.nextInt(spotOptions.size)
            return spotOptions[randomIndex]
        }
    }
}
