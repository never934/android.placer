package com.placer.client.screens.city

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.placer.client.Constants
import com.placer.client.R
import com.placer.client.adapters.cities.CitiesAdapter
import com.placer.client.adapters.cities.CityClickListener
import com.placer.client.base.BaseActivity
import com.placer.client.base.BaseFragment
import com.placer.client.databinding.FragmentChooseCityBinding
import com.placer.client.util.extensions.FragmentExtensions.hideKeyBoard
import com.placer.client.util.extensions.ViewExtensions.close

internal class ChooseCityFragment : BaseFragment() {

    override val viewModel: ChooseCityViewModel by viewModels()
    private var binding: FragmentChooseCityBinding? = null
    private var searchView: SearchView? = null
    private var queryTextListener: SearchView.OnQueryTextListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_choose_city, container, false
        )
        (requireActivity() as AppCompatActivity?)?.supportActionBar?.elevation = Constants.ACTION_BAR_ELEVATION
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
                viewModel.loadCities(newText ?: "")
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
        val adapter = CitiesAdapter(
            CityClickListener{
                viewModel.cityChosen(it)
            }
        )
        viewModel.cities.observe(this, {
            adapter.submitList(it)
        })
        viewModel.cityUpdated.observe(this, {
            it?.let {
                searchView?.close()
                if (requireActivity() is BaseActivity){
                    findNavController().navigateUp()
                }else{
                    val intent = Intent()
                    intent.putExtra(Constants.CITY_CHOSEN_RESULT_KEY, true)
                    requireActivity().setResult(Activity.RESULT_OK, intent)
                    requireActivity().finish()
                }
                viewModel.cityUpdatedTransactionExecuted()
            }
        })
        binding?.let { binding ->
            binding.recycler.adapter = adapter
            binding.baseConstraint.swipeRefreshLayout.setOnRefreshListener {
                searchView?.close()
                viewModel.loadCities("")
            }
        }

    }

    override fun refreshStateChanged(state: Boolean) {
        binding?.baseConstraint?.swipeRefreshLayout?.isRefreshing = state
    }

    override fun loadingStateChanged(state: Int) {
        binding?.baseConstraint?.loadConstraint?.visibility = state
    }
}