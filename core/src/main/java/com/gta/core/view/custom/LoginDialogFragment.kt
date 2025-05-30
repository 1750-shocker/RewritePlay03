package com.gta.core.view.custom


import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.gta.core.R
import com.gta.core.databinding.DialogLoginBinding
import com.gta.core.view.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginDialogFragment : DialogFragment(R.layout.dialog_login) {
    private var binding: DialogLoginBinding? = null


//    private val accountManager by lazy { viewModel.getAccountManager(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogLoginBinding.bind(view).apply {
            // 绑定生命周期和ViewModel
            lifecycleOwner = viewLifecycleOwner
            dialog = this@LoginDialogFragment
        }

        //TODO：获取userinfo，将头像和昵称绑定到视图

        //TODO:获取登录状态，然后设置到视图中的变量

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawableResource(R.color.transparent)
        }
    }

    fun showDialog(activity: AppCompatActivity) {
        show(activity.supportFragmentManager, "LoginDialogFragment")
    }

    fun logout() {
       //TODO：调用后台接口进行退出登录
    }
}

@HiltViewModel
class LoginViewModel @Inject constructor (): BaseViewModel() {

}