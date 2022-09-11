package com.example.nycschools.api

import com.example.nycschools.api.manager.SchoolsManager
import com.example.nycschools.api.model.SchoolInfo
import com.example.nycschools.api.model.SchoolList
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * General manager class for api services
 *
 * @author Saikrishna Pawar
 * @since 9/9/22
 */
@Singleton
class ApiServiceMain @Inject constructor(
    private val mSchoolsManager: SchoolsManager
) {

    /**
     * Get schools list
     *
     * @return [Response] of type [SchoolList]
     */
    suspend fun getSchoolList() = mSchoolsManager.getSchoolsList()


    /**
     * Get additional school information
     *
     * @param dbn school id
     *
     * @return [Response] of type [SchoolInfo]
     */
    suspend fun getSchoolInfo(dbn: String) = mSchoolsManager.getSchoolInfo(dbn = dbn)
}