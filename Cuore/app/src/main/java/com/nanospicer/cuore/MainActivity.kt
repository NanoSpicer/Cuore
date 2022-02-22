package com.nanospicer.cuore

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nanospicer.cuore.databinding.ActivityMainBinding
import dev.chrisbanes.insetter.doOnApplyWindowInsets
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val vm by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableInmersiveMode(drawBehindNavBar = true)
        DataBindingUtil
            .setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .apply {
                rvReasonsList.doOnApplyWindowInsets { view, insets, initialState ->
                    /*view.updatePadding(
                        left = initialState.paddings.left,
                        right = initialState.paddings.right,
                        top = insets.systemWindowInsetTop,
                        bottom =  insets.systemWindowInsetBottom
                    )*/
                }

                swipe.doOnApplyWindowInsets { view, insets, initialState ->
                    view.updatePadding(
                        left = initialState.paddings.left,
                        right = initialState.paddings.right,
                        top = insets.systemWindowInsetTop,
                        bottom =  insets.systemWindowInsetBottom
                    )
                }

            }
            .also {
                it.lifecycleOwner = this
                it.vm = vm
                it.swipe.setOnRefreshListener {
                    lifecycleScope.launch {
                        vm.refresh()
                        it.swipe.isRefreshing = false
                    }
                }
            }
    }



}
