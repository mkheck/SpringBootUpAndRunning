package com.thehecklers.sburredis

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import java.time.Instant

@JsonIgnoreProperties(ignoreUnknown = true)
data class Aircraft(
    @Id @JsonProperty("id") val id: Long? = 0L,
    @JsonProperty("callsign") val callsign: String? = "",
    @JsonProperty("squawk") val squawk: String? = "",
    @JsonProperty("reg") val reg: String? = "",
    @JsonProperty("flightno") val flightno: String? = "",
    @JsonProperty("route") val route: String? = "",
    @JsonProperty("type") val type: String? = "",
    @JsonProperty("category") val category: String? = "",
    @JsonProperty("altitude") val altitude: Int? = 0,
    @JsonProperty("heading") val heading: Int? = 0,
    @JsonProperty("speed") val speed: Int? = 0,
    @JsonProperty("vert_rate")
    val vertRate: Int? = 0,
    @JsonProperty("selected_altitude")
    val selectedAltitude: Int? = 0,
    @JsonProperty("lat") val lat: Double? = 0.0,
    @JsonProperty("lon") val lon: Double? = 0.0,
    @JsonProperty("barometer") val barometer: Double? = 0.0,
    @JsonProperty("polar_distance")
    val polarDistance: Double? = 0.0,
    @JsonProperty("polar_bearing")
    val polarBearing: Double? = 0.0,
    @JsonProperty("is_adsb")
    val isADSB: Boolean? = false,
    @JsonProperty("is_on_ground")
    val isOnGround: Boolean? = false,
    @JsonProperty("last_seen_time")
    val lastSeenTime: String? = Instant.ofEpochSecond(0).toString(),
    @JsonProperty("pos_update_time")
    val posUpdateTime: String? = Instant.ofEpochSecond(0).toString(),
    @JsonProperty("bds40_seen_time")
    val bds40SeenTime: String? = Instant.ofEpochSecond(0).toString()
)
