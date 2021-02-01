package com.thehecklers.sburjpa

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
    @JsonProperty("vert_rate")
    val vertRate: Int = 0,
    @JsonProperty("selected_altitude")
    val selectedAltitude: Int = 0,
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val barometer: Double = 0.0,
    @JsonProperty("polar_distance")
    val polarDistance: Double = 0.0,
    @JsonProperty("polar_bearing")
    val polarBearing: Double = 0.0,
    @JsonProperty("is_adsb")
    val isADSB: Boolean = false,
    @JsonProperty("is_on_ground")
    val isOnGround: Boolean = false,
    @JsonProperty("last_seen_time")
    val lastSeenTime: Instant? = null,
    @JsonProperty("pos_update_time")
    val posUpdateTime: Instant? = null,
    @JsonProperty("bds40_seen_time")
    val bds40SeenTime: Instant? = null
)