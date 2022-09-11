package com.example.nycschools.schools.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nycschools.R
import com.example.nycschools.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity class
 *
 * @author Saikrishna Pawar
 * @since 9/9/22
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mBinding.root)
    }
}