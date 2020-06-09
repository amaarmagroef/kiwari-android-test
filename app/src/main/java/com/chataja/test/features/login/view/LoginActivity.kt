package com.chataja.test.features.login.view

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.chataja.test.R
import com.chataja.test.features.login.viewmodel.LoginViewModel
import com.chataja.test.utils.BaseFragment
import com.chataja.test.utils.PrefManager
import com.chataja.test.features.login.model.UserFirebase
import com.chataja.test.utils.observe
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.test_fragment_login.*
import kotlinx.coroutines.launch

class LoginActivity : BaseFragment() {

    lateinit var mPrefManager : PrefManager
    override fun layoutResId(): Int = R.layout.test_fragment_login
    lateinit var listUsers : LoginActivity

    private val viewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        listUsers = this
        super.onViewCreated(view, savedInstanceState)
        mPrefManager = PrefManager(requireContext())
        bindViewModel()
        test_action_login.setOnClickListener {
            var loginValid = true
            val email = test_edittext_email.text.toString()
            val password = test_edittext_password.text.toString()
            if(viewModel.isEmptyString(email))
            {
                setErrorMessageOnWidget(test_edittext_email,
                    "Silakan isi email terlebih dahulu")
                loginValid = false
            }

            if(viewModel.isEmptyString(password))
            {
                setErrorMessageOnWidget(test_edittext_password,
                    "Silakan isi password terlebih dahulu")
                loginValid = false
            }

            if(!viewModel.validEmail(email))
                loginValid = false

            if(!viewModel.validPassword(password))
                loginValid = false

            if(loginValid) {
                var user_ : UserFirebase? = null
                val mDatabase = FirebaseDatabase.getInstance().getReference()
                val query = mDatabase.child("users").orderByChild("email").equalTo(email)
                launch {
                    query.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            Toast.makeText(context, "Terjadi Kesalahan Internal", Toast.LENGTH_SHORT)
                                .show()
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            if (p0.exists()) {
                                for (item in p0.children) {
                                    val tempModelClass: UserFirebase? =
                                        item.getValue(UserFirebase::class.java)
                                    if (password.equals(tempModelClass!!.password)) {
                                        user_ = tempModelClass
                                        test_edittext_email.text.clear()
                                        test_edittext_password.text.clear()
                                        mPrefManager.setAvatar(tempModelClass.avatar!!)
                                        mPrefManager.setUsername(tempModelClass.nama!!)
                                        mPrefManager.setEmail(tempModelClass.email!!)
                                        val direction =
                                            LoginActivityDirections.actionLoginActivityToMainFragment()
                                        it.findNavController().navigate(direction)
                                    }
                                }
                            } else
                                Toast.makeText(context, "Email tidak ditemukan", Toast.LENGTH_SHORT)
                                    .show()

                        }

                    })

                }


            }
        }
    }


    private fun setErrorMessageOnWidget(edittext: EditText, message : String){
        edittext.requestFocus()
        edittext.error = message
    }

    private fun bindViewModel() {
        observe(viewModel.users) {
            if (view == null) return@observe
            if(viewModel.errorCode == 500){
                Toast.makeText(this.context, "Terjadi kesalahan internal", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
