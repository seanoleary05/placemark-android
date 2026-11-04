package com.example.placemark

interface OnMarkListener {
    fun onMarkClick(mark: PlacedMarkData)

    fun onMarkLongClick(mark: PlacedMarkData): Boolean
}