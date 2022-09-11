package com.example.nycschools.schools.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nycschools.R
import com.example.nycschools.databinding.FragmentSchoolListBinding
import com.example.nycschools.schools.ui.adapter.SchoolsAdapter
import com.example.nycschools.schools.viewmodel.SchoolViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Fragment to display list of schools
 *
 * @author Saikrishna Pawar
 * @since 9/9/22
 */
@AndroidEntryPoint
class SchoolListFragment : Fragment(), SchoolsAdapter.ClickListener {

    private lateinit var mBinding: FragmentSchoolListBinding
    private lateinit var mAdapter: SchoolsAdapter
    private val mViewModel: SchoolViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentSchoolListBinding.inflate(inflater)
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
        // set title
        requireActivity().title = getString(R.string.school_list)

        // set recycler view
        mAdapter = SchoolsAdapter(this)
        mBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter

            val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            ContextCompat.getDrawable(context, R.drawable.list_divider)?.let {
                itemDecoration.setDrawable(it)
                addItemDecoration(itemDecoration)
            }
        }
    }

    /**
     * Initialize data
     */
    private fun initializeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            launch {
                mViewModel.getSchoolList()
            }

            launch {
                mViewModel.schoolList.observe(viewLifecycleOwner) { schoolList ->
                    mAdapter.setData(schoolList)
                }
            }

            launch {
                mViewModel.navigationFlow.collect { bundle ->
                    findNavController().navigate(
                        R.id.action_schoolListFragment_to_schoolDataFragment,
                        bundle
                    )
                }
            }
        }
    }

    /**
     * Called when list item is clicked
     */
    override fun onItemClick(dbn: String) {
        mViewModel.onSchoolItemCLicked(dbn)
    }
}