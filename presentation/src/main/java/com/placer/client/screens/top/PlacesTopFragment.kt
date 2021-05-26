package com.placer.client.screens.top

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.placer.client.Constants
import com.placer.client.R
import com.placer.client.adapters.places.PlaceClickListener
import com.placer.client.adapters.places.PlacesAdapter
import com.placer.client.base.BaseFragment
import com.placer.client.databinding.FragmentTopBinding
import com.placer.client.entity.PlaceView
import com.placer.client.navigation.PlaceViewTransaction
import com.placer.client.util.extensions.ViewExtensions.close

internal class PlacesTopFragment : BaseFragment(), PlaceViewTransaction {

    override val viewModel: PlacesTopViewModel by viewModels()
    private var binding: FragmentTopBinding? = null
    private var searchView: SearchView? = null
    private var queryTextListener: SearchView.OnQueryTextListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_top, container, false)
        (requireActivity() as AppCompatActivity?)?.supportActionBar?.elevation = Constants.ACTION_BAR_ELEVATION
        (requireActivity() as AppCompatActivity?)?.supportActionBar?.show()
        setHasOptionsMenu(true)
        return binding?.root
    }

    override fun initListeners() {
        val adapter = PlacesAdapter(
            PlaceClickListener {
                viewModel.placeClicked(it)
            }
        )
        binding?.let { binding ->
            binding.baseConstraint.swipeRefreshLayout.setOnRefreshListener {
                searchView?.close()
                viewModel.reload()
            }
            binding.recycler.adapter = adapter
        }
        viewModel.topPlaces.observe(this, {
            adapter.submitList(it)
        })
        viewModel.goToPlaceView.observe(this, {
            it?.let {
                setPlaceViewFragment(it)
                viewModel.navigatedToPlaceView()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchItem?.let { searchView = searchItem.actionView as SearchView }
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.loadPlaces(newText ?: "")
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

    override fun refreshStateChanged(state: Boolean) {
        binding?.baseConstraint?.swipeRefreshLayout?.isRefreshing = state
    }

    override fun loadingStateChanged(state: Boolean) {
        binding?.baseConstraint?.setLoading(state)
    }

    override fun setPlaceViewFragment(place: PlaceView) {
        findNavController().navigate(PlacesTopFragmentDirections.actionPlacesTopFragmentToPlaceViewFragment(place))
    }
}