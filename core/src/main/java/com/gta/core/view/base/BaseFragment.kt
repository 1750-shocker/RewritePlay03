package com.gta.core.view.base

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gta.core.R
import com.gta.core.util.showToast
import com.gta.core.view.base.lce.DefaultLceImpl
import com.gta.core.view.base.lce.ILce
import com.scwang.smart.refresh.layout.util.SmartUtil.dp2px

abstract class BaseFragment<T: BaseViewModel, V: ViewDataBinding> : Fragment() {
    abstract val viewModel: T
    abstract val layoutId: Int
    lateinit var dataBinding: V

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, null, false)
        dataBinding.lifecycleOwner = this
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}

