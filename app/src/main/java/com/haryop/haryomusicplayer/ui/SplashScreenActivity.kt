package com.haryop.haryomusicplayer.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import com.haryop.haryomusicplayer.databinding.ActivitySplashScreenBinding
import com.haryop.haryomusicplayer.ui.main.MainActivity
import com.haryop.haryomusicplayer.utils.BaseActivityBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity: BaseActivityBinding<ActivitySplashScreenBinding>() {

    val activityScope = CoroutineScope(Dispatchers.Main)

    override val bindingInflater: (LayoutInflater) -> ActivitySplashScreenBinding
        get() = ActivitySplashScreenBinding::inflate

    override fun setupView(binding: ActivitySplashScreenBinding) {
        setupAction(binding.root)
    }

    fun setupAction(view: View) = with(binding) {
        activityScope.launch {
            delay(2000)
            var intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}