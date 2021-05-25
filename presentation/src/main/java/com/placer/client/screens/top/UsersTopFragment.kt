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
import com.placer.client.adapters.users.UserClickListener
import com.placer.client.adapters.users.UsersAdapter
import com.placer.client.base.BaseFragment
import com.placer.client.databinding.FragmentTopBinding
import com.placer.client.entity.UserView
import com.placer.client.navigation.UserViewTransaction
import com.placer.client.util.extensions.ViewExtensions.close

internal class UsersTopFragment : BaseFragment(), UserViewTransaction {

    override val viewModel: UsersTopViewModel by viewModels()
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
        val adapter = UsersAdapter(
            UserClickListener {
                viewModel.userClicked(it)
            }
        )
        binding?.let { binding ->
            binding.baseConstraint.swipeRefreshLayout.setOnRefreshListener {
                searchView?.close()
                viewModel.reload()
            }
            binding.recycler.adapter = adapter
        }
        viewModel.topUsers.observe(this, {
            adapter.submitList(it)
        })
        viewModel.goToUserView.observe(this, {
            it?.let {
                setUserViewFragment(it)
                viewModel.goToUserViewExecuted()
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
                viewModel.loadUsers(newText ?: "")
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

    override fun loadingStateChanged(state: Int) {
        binding?.baseConstraint?.loadConstraint?.visibility = state
    }

    override fun setUserViewFragment(user: UserView) {
        findNavController().navigate(UsersTopFragmentDirections.actionUsersTopFragmentToUserViewFragment(user))
    }
}