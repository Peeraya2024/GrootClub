package com.example.grootclub.adapter

import com.example.grootclub.data.CoachListModelItem

interface CoachItemClickListener {
    fun onItemClicked(item: CoachListModelItem)
    fun onReadMoreClicked(item: CoachListModelItem)
}