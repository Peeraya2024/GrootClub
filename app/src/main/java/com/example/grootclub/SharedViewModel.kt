package com.example.grootclub

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.grootclub.data.CoachListModelItem
import com.example.grootclub.data.ProductModel
import com.example.grootclub.data.TimeTableBookingModelItem

class SharedViewModel : ViewModel() {
    private val selectedStadium = MutableLiveData<ProductModel.Stadium>()
    private val selectedCoach = MutableLiveData<List<CoachListModelItem>>()
    private val selectedSport = MutableLiveData<String>()
    private val timeTableBooking = MutableLiveData<List<TimeTableBookingModelItem>>()

    fun selectStadium(stadium: ProductModel.Stadium) {
        selectedStadium.value = stadium
    }

    fun getSelectedStadium(): LiveData<ProductModel.Stadium> {
        return selectedStadium
    }

    fun selectCoach(coach: List<CoachListModelItem>) {
        selectedCoach.value = coach
    }
    fun getSelectedCoach(): LiveData<List<CoachListModelItem>> {
        return selectedCoach
    }

    fun setSelectedSport(sport: String) {
        selectedSport.value = sport
    }
    fun getSelectedSport(): MutableLiveData<String> {
        return selectedSport
    }
    fun setTimeTableBooking(data: List<TimeTableBookingModelItem>) {
        timeTableBooking.value = data
    }

    fun getTimeTableBooking(): LiveData<List<TimeTableBookingModelItem>> {
        return timeTableBooking
    }
}
