package com.example.nycschools.di

import com.example.nycschools.api.ApiServiceMain
import com.example.nycschools.api.manager.SchoolsManager
import com.example.nycschools.api.request.SchoolApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Dagger module for API injections
 *
 * @author Saikrishna Pawar
 * @since 9/9/22
 */
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    /**
     * Dependency for [ApiServiceMain]
     *
     * @param schoolsManager [SchoolsManager]
     *
     * @return [ApiServiceMain]
     */
    @Singleton
    @Provides
    fun provideApiServiceMain(
        schoolsManager: SchoolsManager
    ): ApiServiceMain {
        return ApiServiceMain(schoolsManager)
    }

    /**
     * Dependency for [Retrofit]
     *
     * @return [Retrofit]
     */
    @Singleton
    @Provides
    @Named("schools_api")
    fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder().baseUrl("https://data.cityofnewyork.us/resource/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    /**
     * Dependency for [SchoolApi]
     *
     * @param retrofit [Retrofit]
     *
     * @return [SchoolApi]
     */
    @Singleton
    @Provides
    fun provideSchoolApi(@Named("schools_api") retrofit: Retrofit): SchoolApi {
        return retrofit.create(SchoolApi::class.java)
    }
}