package com.ali.clothsapp.Fragments.SignIn


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ali.clothsapp.R
import com.ali.clothsapp.Data.User
import com.ali.clothsapp.Util.SignUpValidation
import com.ali.clothsapp.Util.Resource
import com.ali.clothsapp.Viewmodel.SignUpViewModel
import com.ali.clothsapp.databinding.FragmentSingUpBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
//import com.github.razir.progressbutton.ProgressButton
import com.github.razir.progressbutton.bindProgressButton
//import com.github.razir.progressbutton.startAnimation
//import com.github.razir.progressbutton.revertAnimation

private val TAG = "RegisterFragment"
@AndroidEntryPoint

class SignUpFragment : Fragment(){
    private lateinit var binding: FragmentSingUpBinding
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSingUpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ✅ Bind ProgressButton once
        bindProgressButton(binding.signInButton2)

        binding.doyouhaveanaccount.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        binding.apply {
            signInButton2.setOnClickListener {
                val user = User(
                    edFirstName.text.toString().trim(),
                    edLastName.text.toString().trim(),
                    edEmailSignUp.text.toString().trim()
                )
                val password = edPasswordSignUp.text.toString()
                viewModel.createAccountWithEmailAndPassword(user, password)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.register.collect {
                when (it) {
                    is Resource.Loading -> {
                        binding.signInButton2.startAnimation()
                        binding.signInButton2.isEnabled = false
                    }
                    is Resource.Success -> {

                        binding.signInButton2.revertAnimation()
                        binding.signInButton2.isEnabled = true                    }
                    is Resource.Error -> {
                        Log.e(TAG,it.message.toString())
                        binding.signInButton2.revertAnimation()
                        binding.signInButton2.isEnabled = true                    }
                    else -> Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.validation.collect { validation ->
                if (validation.email is SignUpValidation.Failed){
                    withContext(Dispatchers.Main){
                        binding.edEmailSignUp.apply {
                            requestFocus()
                            error = validation.email.message
                        }
                    }
                }

                if (validation.password is SignUpValidation.Failed){
                    withContext(Dispatchers.Main){
                        binding.edPasswordSignUp.apply {
                            requestFocus()
                            error = validation.password.message
                        }
                    }
                }
            }
        }
    }
}