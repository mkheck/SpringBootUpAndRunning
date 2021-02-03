package com.thehecklers.aircraftpositions

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Aircraft(
    @Id
    @GeneratedValue
    val id: Long? = null,
    val callsign: String? = null,
    val squawk: String? = null,
    val reg: String? = null,
    val flightno: String? = null,
    val route: String? = null,
    val type: String? = null,
    val category: String? = null,
    val altitude: Int = 0,
    val heading: Int = 0,
    val speed: Int = 0,
    @field:JsonProperty("vert_rate")
    val vertRate: Int = 0,
    @field:JsonProperty("selected_altitude")
    val selectedAltitude: Int = 0,
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val barometer: Double = 0.0,
    @field:JsonProperty("polar_distance")
    val polarDistance: Double = 0.0,
    @field:JsonProperty("polar_bearing")
    val polarBearing: Double = 0.0,
    @field:JsonProperty("is_adsb")
    val isADSB: Boolean = false,
    @field:JsonProperty("is_on_ground")
    val isOnGround: Boolean = false,
    @field:JsonProperty("last_seen_time")
    val lastSeenTime: Instant? = null,
    @field:JsonProperty("pos_update_time")
    val posUpdateTime: Instant? = null,
    @field:JsonProperty("bds40_seen_time")
    val bds40SeenTime: Instant? = null
)