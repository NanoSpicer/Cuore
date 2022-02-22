package com.nanospicer.cuore

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.nanospicer.cuore.databinding.ActivitySplashBinding
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {


    private val messages = listOf(
        "I love you",
        "Ti amo",
        "T'estim",
        "Te quiero",
        "Ti voglio bene",
        "Ets lo millor",
        "I miss you",
        "Take care",
        "The dogga misses you\nand so do I"
    )

    private val animListener = object : Animator.AnimatorListener {
        override fun onAnimationStart(p0: Animator?) = Unit
        override fun onAnimationCancel(p0: Animator?) = Unit
        override fun onAnimationRepeat(p0: Animator?) = Unit
        override fun onAnimationEnd(animation: Animator?) = lifecycleScope.launch {
            tvLoveYou.visible()
            delay(3_500)

            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }.let { Unit }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableInmersiveMode(drawBehindNavBar = true)
        val binding =
            DataBindingUtil
                .setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)
        binding.setup()
    }


    private fun ActivitySplashBinding.setup() {
        lottieAnimView.addAnimatorListener(animListener)
        tvLoveYou.text = messages.shuffled().first()
    }
}
