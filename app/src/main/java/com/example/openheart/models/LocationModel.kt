package com.example.openheart.models

import java.net.Inet4Address

data class LocationModel(
    var locId: String? = null,
    var locName: String? = null,
    var locAddress: String? = null,
    var locNum: String? = null,
    val locMap: String? = null
)
