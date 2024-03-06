package com.example.grootclub.ui.coach

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grootclub.data.CoachListModelItem
import com.example.grootclub.data.Remote.Repository.Home.CoachRepository
import com.example.grootclub.data.TimeTableBookingModel
import com.example.grootclub.data.TimeTableBookingModelItem
import kotlinx.coroutines.launch
import retrofit2.Response

class CoachVM(private val repository: CoachRepository) : ViewModel() {
    private val _coachList = MutableLiveData<List<CoachListModelItem>>()
    val allCoachList: LiveData<List<CoachListModelItem>> = _coachList

    private val _timeTableBooking = MutableLiveData<List<TimeTableBookingModelItem>>()
    val timeTableBooking: LiveData<List<TimeTableBookingModelItem>> = _timeTableBooking

    val readMoreEvent = MutableLiveData<CoachListModelItem>()

    fun fetchAllCoach() {
        viewModelScope.launch {
            try {
                val result = repository.getAllCoach()
                _coachList.postValue(result)
            } catch (e: Exception) {
                _coachList.postValue(emptyList())
            }
        }
    }

    fun fetchTimeTableBooking(typeSport: String, date: String) {
        viewModelScope.launch {
            try {
                val result = repository.getTimeTableBooking(typeSport, date)
                _timeTableBooking.postValue(result)
            } catch (e: Exception) {
                _timeTableBooking.postValue(emptyList())
            }
        }
    }

}