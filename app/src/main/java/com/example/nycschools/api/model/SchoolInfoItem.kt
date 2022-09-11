package com.example.nycschools.api.model

/**
 * School Info
 *
 * @author Saikrishna Pawar
 * @since 9/9/22
 */
data class SchoolInfoItem(
    val dbn: String,
    val num_of_sat_test_takers: String,
    val sat_critical_reading_avg_score: String,
    val sat_math_avg_score: String,
    val sat_writing_avg_score: String,
    val school_name: String
)