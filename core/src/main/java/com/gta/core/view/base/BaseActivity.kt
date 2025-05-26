package com.gta.core.view.base

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import com.gta.core.Play.context

abstract class BaseActivity<T: BaseViewModel, V: ViewDataBinding>: AppCompatActivity() {
    abstract val viewModel: T
    abstract val layoutId: Int
    lateinit var dataBinding: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        dataBinding = DataBindingUtil.setContentView(this, layoutId)
        dataBinding.lifecycleOwner = this
        ViewCompat.setOnApplyWindowInsetsListener(dataBinding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun <T> setDataStatus(
        dataLiveData: LiveData<Result<T>>,
        onDataStatus: (T) -> Unit
    ) {
        dataLiveData.observe(this) {
            if (it.isSuccess) {
                val dataList = it.getOrNull()
                if (dataList != null) {
                    onDataStatus(dataList)
                } else {
                    Toast.makeText(this, "数据为空", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, it.exceptionOrNull()?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

}
