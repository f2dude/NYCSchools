package com.example.nycschools.schools.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nycschools.api.ApiServiceMain
import com.example.nycschools.api.manager.SchoolsManager
import com.example.nycschools.api.request.SchoolApi
import com.example.nycschools.di.ApiModule
import com.example.nycschools.utils.MockResponseFileReader
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.net.HttpURLConnection

/**
 * School view model test class
 */
@RunWith(MockitoJUnitRunner::class)
class SchoolViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    private val application: Application = Application()

    private lateinit var mViewModel: SchoolViewModel

    private lateinit var mApiServiceMain: ApiServiceMain

    private lateinit var mMockWebServer: MockWebServer

    private val schoolListSuccessResponse = "school_list_success_response.json"
    private val schoolListFailedResponse = "school_list_failed_response.json"

    private val schoolDetailsSuccessResponse = "school_details_success_response.json"
    private val schoolDetailsFailedResponse = "school_details_failed_response.json"

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        mMockWebServer = MockWebServer()
        mMockWebServer.start()

        val retrofit = ApiModule.provideRetrofitInstance()

        val schoolAPi = retrofit.create(SchoolApi::class.java)

        val schoolManager = SchoolsManager(schoolApi = schoolAPi)

        mApiServiceMain = ApiServiceMain(mSchoolsManager = schoolManager)

        mViewModel = SchoolViewModel(mApiServiceMain = mApiServiceMain, application = application)
    }

    /**
     * Read school list success json file
     */
    @Test
    fun `read school list success json file`() {
        val reader = MockResponseFileReader(schoolListSuccessResponse)
        assertNotNull(reader.content)
    }

    /**
     * Read school list failed json file
     */
    @Test
    fun `read school list failed json file`() {
        val reader = MockResponseFileReader(schoolListFailedResponse)
        assertNotNull(reader.content)
    }

    /**
     * Read school details success json file
     */
    @Test
    fun `read school details success json file`() {
        val reader = MockResponseFileReader(schoolDetailsSuccessResponse)
        assertNotNull(reader.content)
    }

    /**
     * Read school details failed json file
     */
    @Test
    fun `read school details failed json file`() {
        val reader = MockResponseFileReader(schoolDetailsFailedResponse)
        assertNotNull(reader.content)
    }

    /**
     * School list success
     */
    @Test
    fun `fetch school list and check response Code 200 returned`() = runBlocking {
        // mock response
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader(schoolListSuccessResponse).content)
        mMockWebServer.enqueue(response)

        // call api to fetch school list
        val actualResponse = mApiServiceMain.getSchoolList()

        // check result
        assertEquals(
            response.toString().contains("200"),
            actualResponse.code().toString().contains("200")
        )
    }

    /**
     * School list failure
     */
    @Test
    fun `fetch school list for failed response 400 returned`() = runBlocking {
        // mock response
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
            .setBody(MockResponseFileReader(schoolListFailedResponse).content)
        mMockWebServer.enqueue(response)

        // call api to fetch result
        val actualResponse = mApiServiceMain.getSchoolList()

        // check result
        assertEquals(response.toString().contains("400"), actualResponse.toString().contains("400"))
    }

    /**
     * School details success
     */
    @Test
    fun `fetch school details and check response Code 200 returned`() = runBlocking {
        // mock response
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader(schoolDetailsSuccessResponse).content)
        mMockWebServer.enqueue(response)

        // call api to fetch school list
        val actualResponse = mApiServiceMain.getSchoolInfo("01M292")

        // check result
        assertEquals(
            response.toString().contains("200"),
            actualResponse.code().toString().contains("200")
        )
    }

    /**
     * School details failure
     */
    @Test
    fun `fetch school details for failed response 400 returned`() = runBlocking {
        // mock response
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
            .setBody(MockResponseFileReader(schoolDetailsFailedResponse).content)
        mMockWebServer.enqueue(response)

        // call api to fetch result
        val actualResponse = mApiServiceMain.getSchoolInfo("")

        // check result
        assertEquals(response.toString().contains("400"), actualResponse.toString().contains("400"))
    }


    @After
    fun tearDown() {
        mMockWebServer.shutdown()
    }
}