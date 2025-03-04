package com.dave.github.view.search

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dave.github.R
import com.dave.github.databinding.SearchUserActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchUserActivity : AppCompatActivity() {

    private lateinit var binding : SearchUserActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = DataBindingUtil.setContentView(this, R.layout.search_user_activity)


    }
}