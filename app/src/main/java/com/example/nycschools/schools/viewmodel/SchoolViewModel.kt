package com.example.nycschools.schools.viewmodel

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import com.example.nycschools.R
import com.example.nycschools.api.ApiServiceMain
import com.example.nycschools.api.model.SchoolInfoItem
import com.example.nycschools.api.model.SchoolList
import com.example.nycschools.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * School view model scoped to activity
 *
 * @author Saikrishna Pawar
 * @since 9/9/22
 */
@HiltViewModel
class SchoolViewModel @Inject constructor(
    private val mApiServiceMain: ApiServiceMain,
    application: Application
) : AndroidViewModel(application) {

    // school list
    private var mSchoolList: MutableLiveData<SchoolList> = MutableLiveData()
    val schoolList: LiveData<SchoolList> = mSchoolList

    // navigation
    private var mNavigationFlow: MutableSharedFlow<Bundle> = MutableSharedFlow()
    val navigationFlow: SharedFlow<Bundle> = mNavigationFlow

    // school data
    private var mSchoolData: MutableLiveData<SchoolInfoItem> = MutableLiveData()
    val schoolData: LiveData<SchoolInfoItem> = mSchoolData

    companion object {
        private const val TAG = "SchoolViewModel"
    }

    /**
     * Get school list
     */
    fun getSchoolList() {
        Log.d(TAG, "getSchoolList()")

        viewModelScope.launch(Dispatchers.IO) {
            if ((mSchoolList.value?.size ?: 0) == 0) {
                val result = mApiServiceMain.getSchoolList()
                if (result.isSuccessful) {
                    result.body()?.let { schoolList ->
                        Log.d(TAG, "School list: $schoolList")
                        mSchoolList.postValue(schoolList)
                    }
                }
            } else {
                Log.d(TAG, "Data already loaded")
            }
        }
    }

    fun onSchoolItemCLicked(dbn: String) {
        Log.d(TAG, "onSchoolItemCLicked(), DBN: $dbn")
        viewModelScope.launch {
            mSchoolData.postValue(generateSchoolInfoItem(schoolName = "", notAvailable = ""))

            val bundle = Bundle()
            bundle.putString(Constants.ARG_DBN, dbn)
            mNavigationFlow.emit(bundle)
        }
    }

    /**
     * Get school data
     *
     * @param dbn school id
     */
    fun getSchoolData(dbn: String) {
        Log.d(TAG, "getSchoolData(), DBN: $dbn")

        viewModelScope.launch(Dispatchers.IO) {
            val result = mApiServiceMain.getSchoolInfo(dbn)
            if (result.isSuccessful) {
                result.body()?.let { data ->
                    Log.d(TAG, "School data: $data")
                    if (data.size > 0) {
                        mSchoolData.postValue(data[0])
                    } else {
                        mSchoolData.postValue(
                            generateSchoolInfoItem()
                        )
                    }
                }
            } else {
                mSchoolData.postValue(
                    generateSchoolInfoItem()
                )
            }
        }
    }

    /**
     * Generate blank school info item
     *
     * @param schoolName    Sample text to use
     * @param notAvailable  Sample text to use
     */
    private fun generateSchoolInfoItem(
        schoolName: String = getApplication<Application>().getString(R.string.data_not_found),
        notAvailable: String = getApplication<Application>().getString(R.string.na)
    ): SchoolInfoItem {
        return SchoolInfoItem(
            dbn = notAvailable,
            school_name = schoolName,
            num_of_sat_test_takers = notAvailable,
            sat_critical_reading_avg_score = notAvailable,
            sat_math_avg_score = notAvailable,
            sat_writing_avg_score = notAvailable
        )
    }
}