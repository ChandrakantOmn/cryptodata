package com.antonicastejon.cryptodata.presentation.main.crypto_list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.antonicastejon.cryptodata.R
import com.antonicastejon.cryptodata.domain.LIMIT_USER_LIST
import com.antonicastejon.cryptodata.presentation.common.UserListRecyclerAdapter
import com.antonicastejon.cryptodata.presentation.widgets.paginatedRecyclerView.PaginationScrollListener
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.crypto_list_fragment.*
import kotlinx.android.synthetic.main.crypto_list_fragment.view.*
import javax.inject.Inject

/**
 * Created by Antoni Castejón on 29/12/2017.
 */
val USER_LIST_FRAGMENT_TAG = UserListFragment::class.java.name

private val TAG = CryptoListFragment::class.java.name

fun newUserListFragment() = UserListFragment()

class UserListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: UserListViewModel

    private val cryptoListAdapter by lazy { UserListRecyclerAdapter() }
    private var isLoading = false
    private var isLastPage = false

    private val stateObserver = Observer<UserListState> { state ->
        state?.let {
            isLastPage = state.loadedAllItems
            when (state) {
                is DefaultState1 -> {
                    isLoading = false
                    swipeRefreshLayout.isRefreshing = false
                    cryptoListAdapter.updateData(it.data)
                }
                is LoadingState1 -> {
                    swipeRefreshLayout.isRefreshing = true
                    isLoading = true
                }
                is PaginatingState1 -> {
                    isLoading = true
                }
                is ErrorState1 -> {
                    isLoading = false
                    swipeRefreshLayout.isRefreshing = false
                    cryptoListAdapter.removeLoadingViewFooter()
                }
            }
        }
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserListViewModel::class.java)
        observeViewModel()
        savedInstanceState?.let {
            viewModel.restoreCryptoList()
        } ?: viewModel.updateCryptoList()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.stateLiveData.removeObserver(stateObserver)
    }

    private fun observeViewModel() {
        viewModel.stateLiveData.observe(this, stateObserver)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.crypto_list_fragment, container, false)
        initializeToolbar(view)
        initializeRecyclerView(view)
        initializeSwipeToRefreshView(view)
        return view
    }

    private fun initializeToolbar(view:View) {
        view.toolbar.title = getString(R.string.app_name)
    }

    private fun initializeRecyclerView(view:View) {
        val linearLayoutManager = LinearLayoutManager(context)
        view.recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = cryptoListAdapter
            addOnScrollListener(OnScrollListener(linearLayoutManager))
        }
    }

    private fun initializeSwipeToRefreshView(view:View) {
        view.swipeRefreshLayout.setOnRefreshListener { viewModel.resetCryptoList() }
    }

    private fun loadNextPage() {
        cryptoListAdapter.addLoadingViewFooter()
        viewModel.updateCryptoList()
    }


    inner class OnScrollListener(layoutManager: LinearLayoutManager) : PaginationScrollListener(layoutManager) {
        override fun isLoading() = isLoading
        override fun loadMoreItems() = loadNextPage()
        override fun getTotalPageCount() = LIMIT_USER_LIST
        override fun isLastPage() = isLastPage
    }
}