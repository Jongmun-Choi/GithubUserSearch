package com.dave.github.view.search

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import com.dave.github.R
import com.dave.github.databinding.SearchUserActivityBinding
import com.dave.github.viewmodel.SearchUserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserSearchActivity : AppCompatActivity() {

    private lateinit var binding : SearchUserActivityBinding
    private val viewModel : SearchUserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = DataBindingUtil.setContentView(this, R.layout.search_user_activity)

        binding.lifecycleOwner = this

        binding.edtSearch.doOnTextChanged { text, _, _, _ ->
            if(!text.isNullOrEmpty()) {
                viewModel.searchUser(text.toString())
            }
        }


    }
}