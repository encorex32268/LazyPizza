package com.lihan.lazypizza.core.analytics

data class AnalyticsEvent(
    val type: String,
    val extras: List<Param> = emptyList()
){
    data class Param(
        val key: String,
        val value: Any?
    )

    class Types {
        companion object{
            const val SCREEN_VIEW = "screen_view"
        }
    }

    class ParmaKeys{
        companion object{
            const val SCREEN_NAME = "screen_name"
        }
    }

}
