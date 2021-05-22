package com.placer.client.screens.places

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.placer.client.AppClass
import com.placer.client.R
import com.placer.client.adapters.PlacesAdapter
import com.placer.client.base.BaseFragment
import com.placer.client.databinding.FragmentPlacesBinding
import com.placer.client.entity.PlaceView
import com.placer.client.navigation.PlaceViewTransaction
import com.placer.client.util.extensions.FragmentExtensions.hideKeyBoard

internal class PlacesFragment : BaseFragment(), PlaceViewTransaction {

    override val viewModel: PlacesViewModel by viewModels()
    private var binding: FragmentPlacesBinding? = null
    private var searchView: SearchView? = null
    private var queryTextListener: SearchView.OnQueryTextListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_places, container, false
        )
        (requireActivity() as AppCompatActivity?)?.supportActionBar?.elevation = 0f
        (requireActivity() as AppCompatActivity?)?.supportActionBar?.show()
        setHasOptionsMenu(true)
        return binding?.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.places_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchItem?.let { searchView = searchItem.actionView as SearchView }
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.loadPlacesByInput(newText ?: "")
                return true
            }
        }
        searchView?.setOnQueryTextListener(queryTextListener)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> return false
            else -> {}
        }
        searchView?.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun initListeners() {
        val adapter = PlacesAdapter(
            PlacesAdapter.PlaceClickListener{
                viewModel.placeClicked(it)
            }
        )
        viewModel.places.observe(this, {
            adapter.submitList(it)
        })
        viewModel.goToPlaceView.observe(this, {
            it?.let {
                setPlaceViewFragment(it)
                viewModel.navigatedToPlaceView()
            }
        })
        binding?.let { binding ->
            binding.placesRecycler.adapter = adapter
            binding.baseConstraint.swipeRefreshLayout.setOnRefreshListener { viewModel.loadPlaces() }
            binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.let {
                        viewModel.tabPositionChanged(it.position)
                    }
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }

    }

    override fun refreshStateChanged(state: Boolean) {
        binding?.baseConstraint?.swipeRefreshLayout?.isRefreshing = state
    }

    override fun setPlaceViewFragment(place: PlaceView) {
        findNavController().navigate(PlacesFragmentDirections.actionPlacesFragmentToPlaceViewFragment(place))
    }
}