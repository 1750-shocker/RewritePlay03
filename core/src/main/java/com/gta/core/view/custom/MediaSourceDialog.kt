package com.gta.core.view.custom

import android.app.Dialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gta.core.R
import com.gta.core.databinding.DialogMediaSourceBinding
import com.gta.model.model.ServiceBean
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MediaSourceDialog(context: Context) : DialogFragment(R.layout.dialog_media_source) {
    private var binding: DialogMediaSourceBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogMediaSourceBinding.bind(view).apply{
            lifecycleOwner = viewLifecycleOwner
        }
        binding?.serviceInfo = ServiceBean(
            "网易云音乐",
            ContextCompat.getDrawable(requireContext(), R.drawable.baseline_business_center_24)
        )
        binding?.root?.setOnClickListener{
            dismiss()
        }
        //模拟数据
        val appList = listOf(
            AppItem("应用程序1", R.drawable.baseline_business_center_24),
            AppItem("应用程序2", R.drawable.baseline_business_center_24),
            AppItem("应用程序3", R.drawable.baseline_business_center_24),
            AppItem("应用程序4", R.drawable.baseline_business_center_24)
        )
        val adapter = AppAdapter(appList)
        adapter.setOnItemClickListener { _, position ->
            Toast.makeText(context, "打开${appList[position].name}", Toast.LENGTH_SHORT).show()
            dismiss()
        }
        //设置RecyclerView
        binding?.recyclerView?.layoutManager = GridLayoutManager(context, 3)
        binding?.recyclerView?.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply{
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawableResource(R.color.main_background)
        }
    }

    fun showDialog(activity: AppCompatActivity) {
        show(activity.supportFragmentManager, "MediaSourceDialog")
    }

}