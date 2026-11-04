package com.example.placemark

import timber.log.Timber
import java.util.concurrent.atomic.AtomicLong

class PlacemarkerMemStore {
    private val placemarks = ArrayList<PlacedMarkData>()
    private val lastId = AtomicLong(0L)

    fun findAll(): List<PlacedMarkData> {
        return placemarks
    }

    fun create(placemark: PlacedMarkData) {
        placemark.validate()
        placemark.id = lastId.incrementAndGet()
        Timber.i("add placemark")
        placemarks.add(placemark)

    }

    fun update(placemark: PlacedMarkData): Boolean {

        placemark.validate()
        val foundPlacemark = findOne(placemark.id)


        return if (foundPlacemark != null) {
                foundPlacemark.title = placemark.title
                foundPlacemark.description = placemark.description
                foundPlacemark.x = placemark.x
                foundPlacemark.y = placemark.y
                foundPlacemark.imageUri = placemark.imageUri
            true
        } else {
            false
        }
    }

    fun delete(id: Long): Boolean {
        val foundPlacemark = findOne(id)
        return if (foundPlacemark != null) {


            placemarks.remove(foundPlacemark)
            true
        } else {
            false
        }
    }

    fun findOne(id: Long): PlacedMarkData? {
        return placemarks.find { p -> p.id == id }
    }
}

