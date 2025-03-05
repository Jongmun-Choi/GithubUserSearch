package com.dave.github.view.search

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dave.github.R
import com.dave.github.databinding.SearchUserActivityBinding
import com.dave.github.view.search.adapter.UserAdapter
import com.dave.github.viewmodel.SearchUserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserSearchActivity : AppCompatActivity() {

    private lateinit var binding : SearchUserActivityBinding
    private val viewModel : SearchUserViewModel by viewModels()
    private var adapter : UserAdapter = UserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = DataBindingUtil.setContentView(this, R.layout.search_user_activity)

        binding.lifecycleOwner = this

        binding.edtSearch.doOnTextChanged { text, _, _, _ ->
            if(!text.isNullOrEmpty()) {
                viewModel.searchUser(text.toString(), true)
            }
        }

        initPage()
    }

    private fun initPage() {
        lifecycleScope.launch {
            binding.rvUserList.adapter = adapter
            viewModel.user.collect { userList ->
                adapter.submitList(userList.toMutableList())
            }
        }

        binding.rvUserList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val rvPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()

                val totalCount = recyclerView.adapter?.itemCount?.minus(1)

                if (rvPosition == totalCount) {
                    viewModel.searchUser(binding.edtSearch.text.toString())
                }
            }
        })

    }


}