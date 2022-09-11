package com.example.nycschools.api.request

import com.example.nycschools.api.model.SchoolInfo
import com.example.nycschools.api.model.SchoolList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Api interface class for getting the schools data
 *
 * @author Saikrishna Pawar
 * @since 9/9/22
 */
interface SchoolApi {

    /**
     * Get list of schools
     *
     * @return [Response] of type [SchoolList]
     */
    @GET("s3k6-pzi2.json")
    suspend fun getNYCSchools(
        @Header("X-App-Token") token: String,
        @Query("\$select") query: String,
        @Query("\$limit") limit: String,
        @Query("\$offset") offset: String
    ): Response<SchoolList>

    /**
     * Get school additional information
     *
     * @return [Response] of type [SchoolInfo]
     */
    @GET("f9bf-2cp4.json")
    suspend fun getSchoolDetails(
        @Header("X-App-Token") token: String,
        @Query("dbn") dbnValue: String
    ): Response<SchoolInfo>
}