package com.miftah.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.miftah.auth.databinding.FragmentLoginBinding
import com.miftah.core.data.DataResult
import com.miftah.core.data.source.preference.model.UserModel
import com.miftah.core.utils.DataMapper.toUserModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class LoginFragment : Fragment(), TextWatcher {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WelcomeViewModel by activityViewModel<WelcomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        check()
        playAnimation()

        binding.edLoginEmail.addTextChangedListener(this)
        binding.edLoginPassword.addTextChangedListener(this)

        binding.btnLogin.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            viewModel.userLogIn(email, password).observe(viewLifecycleOwner) {
                when (it) {
                    is DataResult.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is DataResult.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            it.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is DataResult.Success -> {
                        viewModel.userSaveSession(it.data.result?.toUserModel() as UserModel)
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.inf_intro), Toast.LENGTH_SHORT
                        ).show()
                        val uri = Uri.parse("tedednew://main")
                        startActivity(Intent(Intent.ACTION_VIEW, uri))
                        activity?.finish()
                    }
                }
            }
        }
    }

    private fun check() {
        val edEmail = binding.edLoginEmail.text.toString()
        val edPassword = binding.edLoginPassword.text.toString()
        val check = (edEmail.isNotEmpty() && edPassword.isNotEmpty())
        binding.btnLogin.isActivated = check
        binding.btnLogin.isEnabled = check
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        check()
    }

    override fun afterTextChanged(s: Editable?) {

    }

    private fun playAnimation() {
        val tvTitleLogin =
            ObjectAnimator.ofFloat(binding.tvTitleLogin, View.ALPHA, 1F).setDuration(500)
        val tvImageTitle =
            ObjectAnimator.ofFloat(binding.tvImgTitle, View.ALPHA, 1F).setDuration(500)
        val imageView = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1F).setDuration(500)
        val tvTitleEmail =
            ObjectAnimator.ofFloat(binding.tvTitleEmail, View.ALPHA, 1F).setDuration(500)
        val edEmailLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1F).setDuration(500)
        val edLoginEmail =
            ObjectAnimator.ofFloat(binding.edLoginEmail, View.ALPHA, 1F).setDuration(500)
        val tvTitlePass =
            ObjectAnimator.ofFloat(binding.tvTitlePassword, View.ALPHA, 1F).setDuration(500)
        val edPassLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1F).setDuration(500)
        val edLoginPass =
            ObjectAnimator.ofFloat(binding.edLoginPassword, View.ALPHA, 1F).setDuration(500)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1F).setDuration(500)

        val emailAct = AnimatorSet().apply {
            playTogether(tvTitleEmail, edEmailLayout, edLoginEmail)
        }

        val passAct = AnimatorSet().apply {
            playTogether(tvTitlePass, edPassLayout, edLoginPass)
        }

        val titleAct = AnimatorSet().apply {
            playTogether(tvTitleLogin, tvImageTitle, imageView)
        }

        AnimatorSet().apply {
            playSequentially(titleAct, emailAct, passAct, btnLogin)
            start()
        }
    }
}