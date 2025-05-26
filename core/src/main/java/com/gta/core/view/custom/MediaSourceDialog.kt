package com.gta.core.view.custom

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gta.core.R


class MediaSourceDialog(context: Context) : Dialog(context) {
    init {
        //加载弹窗
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_media_source, null)
        setContentView(view)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.BLACK))
//        设置点击外部关闭弹窗
        setCanceledOnTouchOutside(true)
//        根容器设置点击事件,可以点击图标以外的地方关闭弹窗
        view.setOnClickListener {
            dismiss()
        }
        //设置RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        //模拟数据
        val appList = listOf(
            AppItem("应用程序1", R.drawable.baseline_business_center_24),
            AppItem("应用程序2", R.drawable.baseline_business_center_24),
            AppItem("应用程序3", R.drawable.baseline_business_center_24),
            AppItem("应用程序4", R.drawable.baseline_business_center_24)
        )
        val adapter = AppAdapter(appList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        adapter.setOnItemClickListener { _, position ->
            Toast.makeText(context, "打开${appList[position].name}", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }
}