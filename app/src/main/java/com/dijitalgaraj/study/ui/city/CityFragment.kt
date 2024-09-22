package com.dijitalgaraj.study.ui.city

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dijitalgaraj.study.R
import com.dijitalgaraj.study.base.BaseActionState
import com.dijitalgaraj.study.base.BaseFragment
import com.dijitalgaraj.study.databinding.CityListFragmentBinding
import com.dijitalgaraj.study.models.loadPlacesFromJson
import com.dijitalgaraj.study.ui.city.adapter.CityAdapter
import com.dijitalgaraj.study.ui.district.DistrictFragmentArgs
import com.dijitalgaraj.study.utils.extensions.OnTextChangeListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CityFragment : BaseFragment<CityViewModel, CityListFragmentBinding>(
    CityViewModel::class.java
) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> CityListFragmentBinding =
        CityListFragmentBinding::inflate

    private var cityListAdapter: CityAdapter? = null
    private var textChangeListener: OnTextChangeListener? = null

    override fun init() {
        readData()
        setupEditTextListener()
        setupClickListeners()
    }

    override fun renderActionState(actionState: BaseActionState?) {
        when (val state = actionState as? CityActionState) {
            CityActionState.Init -> Unit
            else -> {}
        }
    }

    private fun readData() {
        viewLifecycleOwner.lifecycleScope.launch {
            val places = withContext(Dispatchers.IO) {
                loadPlacesFromJson(requireContext())
            }
            viewModel.baseCityList = places
            setupAdapter()
        }
    }


    private fun setupEditTextListener() {
        binding.edtSearchBar.addTextChangedListener { text ->
            val searchText = text.toString()
            viewModel.search(searchText)
            binding.imgDelete.isVisible = searchText.isNotEmpty()
        }

        viewModel.filteredCityList.observe(viewLifecycleOwner) { filteredList ->
            cityListAdapter?.submitList(filteredList)
        }
    }

    private fun setupAdapter() {
        cityListAdapter = CityAdapter {
            viewModel.selectedItem = it
            navigateDetail()
        }

        binding.rvCities.adapter = cityListAdapter
        cityListAdapter?.submitList(viewModel.baseCityList)
    }

    private fun setupClickListeners() {
        binding.apply {
            imgDelete.setOnClickListener {
                binding.edtSearchBar.setText("")
            }
        }
    }

    private fun navigateDetail() {
        val data = DistrictFragmentArgs(viewModel.selectedItem)
        findNavController().navigate(R.id.districtFragment, data.toBundle())
    }
}
