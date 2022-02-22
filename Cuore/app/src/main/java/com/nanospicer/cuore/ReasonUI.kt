package com.nanospicer.cuore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.nanospicer.cuore.databinding.ReasonItemBinding

class ReasonUI(
    private val reason: Reason,
    val onClick: () -> Unit
)  : NanoBindable<ReasonItemBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup
    ): ViewDataBinding = ReasonItemBinding.inflate(inflater, container, false)


    override fun onBinding(binding: ReasonItemBinding) = binding.doBind()


    private fun ReasonItemBinding.doBind() {

        description.maxLines =
            if(reason.isCollapsed) 1
            else 20

        ivBg.scaleType =
            if(reason.isCollapsed)
                ImageView.ScaleType.CENTER_CROP
            else
                ImageView.ScaleType.CENTER

        Glide
            .with(root.context)
            .load(reason.mipmapId)
            .into(ivBg)

        title.text = reason.title
        description.text = reason.description

        root.setOnClickListener {
            onClick()
            invalidateAll()
        }
    }
}