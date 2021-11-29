package com.android.moscow4D


class CacheEngine {
    companion object {
        // ArrayList of Maps with place name, image and description.
        var data = arrayListOf<Map<String, String?>>(
            mapOf(
                "Place" to "Название",
                "Image" to R.drawable. ic_map.toString(),
                "Description" to "Тут должно быть описание"
            )
        )
    }
}