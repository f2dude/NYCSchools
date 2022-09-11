package com.example.nycschools.utils

import java.io.InputStreamReader

/**
 * Mocks file response
 *
 * @param path response json file path
 *
 * @author Saikrishna Pawar
 * @since 9/10/22
 */
class MockResponseFileReader(path: String) {

    val content: String

    init {
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
        content = reader.readText()
        reader.close()
    }
}