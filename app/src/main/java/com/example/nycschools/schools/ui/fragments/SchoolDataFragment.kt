package com.example.nycschools.schools.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.nycschools.R
import com.example.nycschools.databinding.FragmentSchoolDataBinding
import com.example.nycschools.schools.viewmodel.SchoolViewModel
import com.example.nycschools.utils.Constants

/**
 * Fragment to display school information
 *
 * @author Saikrishna Pawar
 * @since 9/9/22
 */
class SchoolDataFragment : Fragment() {

    private lateinit var mBinding: FragmentSchoolDataBinding
    private val mViewModel: SchoolViewModel by activityViewModels()

    private var mDbn: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mDbn = it.getString(Constants.ARG_DBN)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentSchoolDataBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeControls()
        initializeData()
    }

    /**
     * Initialize controls
     */
    private fun initializeControls() {
        mBinding.lifecycleOwner = this

        // set title
        requireActivity().title = getString(R.string.school_data)
    }

    /**
     * Initialize data
     */
    private fun initializeData() {
        mDbn?.let {
            mViewModel.getSchoolData(it)

            mViewModel.schoolData.observe(viewLifecycleOwner) { schoolInfo ->
                mBinding.item = schoolInfo
            }
        }
    }
}