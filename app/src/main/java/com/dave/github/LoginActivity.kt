package com.dave.github

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.dave.github.databinding.LoginActivityBinding
import com.dave.github.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding :LoginActivityBinding
    private val viewModel by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.login_activity)
    }

    fun initFlow() {
       lifecycleScope.launch {
            viewModel.login.collect() { loginSuccess ->
                //handle login success
            }
        }

        binding.lifecycleOwner = this

    }

    fun onClick(v : View) {
        when(v.id) {
            R.id.btnLogin -> {
                val loginUrl = Uri.Builder().scheme("https").authority("github.com")
                    .appendPath("login")
                    .appendPath("oauth")
                    .appendPath("authorize")
                    .appendQueryParameter("client_id", BuildConfig.clientId)
                    .build()

                CustomTabsIntent.Builder().build().also {
                    it.launchUrl(this, loginUrl)
                }

            }
        }
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data?.getQueryParameter("code")?.let { code ->
            viewModel.getAccessToken(code)
        }
    }
}