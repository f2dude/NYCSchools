package com.example.nycschools.api.manager

import com.example.nycschools.api.model.SchoolInfo
import com.example.nycschools.api.model.SchoolList
import com.example.nycschools.api.request.SchoolApi
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class for managing NW request to schools
 *
 * @author Saikrishna Pawar
 * @since 9/9/22
 */
@Singleton
class SchoolsManager @Inject constructor(private val schoolApi: SchoolApi) {

    companion object {
        private const val ACCESS_TOKEN = "yfX7ovB47u1rth5orJ2FlpZjs"
    }


    /**
     * Get schools list
     *
     * @return [Response] of type [SchoolList]
     */
    suspend fun getSchoolsList() =
        schoolApi.getNYCSchools(
            token = ACCESS_TOKEN,
            query = "dbn,school_name",
            limit = "100",
            offset = "0"
        )

    /**
     * Get additional school information
     *
     * @param dbn school id
     *
     * @return [Response] of type [SchoolInfo]
     */
    suspend fun getSchoolInfo(dbn: String) =
        schoolApi.getSchoolDetails(token = ACCESS_TOKEN, dbnValue = dbn)

}