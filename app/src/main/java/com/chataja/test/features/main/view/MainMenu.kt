package com.chataja.test.features.main.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.*
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.chataja.test.R
import com.chataja.test.features.login.model.UserFirebase
import com.chataja.test.features.login.view.LoginActivity
import com.chataja.test.features.login.viewmodel.LoginViewModel
import com.chataja.test.features.main.ChatAdapter
import com.chataja.test.features.main.viewmodel.MainMenuViewModel
import com.chataja.test.utils.BaseFragment
import com.chataja.test.utils.PrefManager
import com.chataja.test.utils.observe
import kotlinx.android.synthetic.main.test_fragment_main.*


/**
 * Created by siapaSAYA on 6/9/2020
 */

class MainMenu : BaseFragment() {

    var listUser = mutableListOf<UserFirebase>()

    private val adapterChat by lazy { ChatAdapter() }

    private var username = ""

    private lateinit var menus : Menu

    override fun layoutResId(): Int = R.layout.test_fragment_main

    private lateinit var mAccountPrefManager : PrefManager

    private val viewModel by lazy { ViewModelProvider(this).get(MainMenuViewModel::class.java) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
        bindViewModel()
        mAccountPrefManager = PrefManager(requireContext())
        username = mAccountPrefManager.getUsername()
        test_fab_action_send.setOnClickListener {
            if(!test_edittext_chat.text.isEmpty())
            {
                viewModel.pushChat(test_edittext_chat.text.toString().trim(), username)
                test_edittext_chat.text.clear()
            }
        }
    }


    private fun bindViewModel() {
        observe(viewModel.chats) {
            if (view == null) return@observe
            it?.let { adapterChat.update(it)
            }
        }

        observe(viewModel.users){
            if(view == null) {
                return@observe
            }
            it?.let {
            }
        }
    }

    private fun configureRecyclerView(){
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            isNestedScrollingEnabled = true
            adapter = this@MainMenu.adapterChat
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menus = menu
        menu.clear()
        inflater.inflate(R.menu.toolbar_menu, menu)
        changeUser()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_exit -> {
                navController.navigate(R.id.action_global_loginFragment)
            }
        }
        return true
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.test_fragment_main, container, false)
    }

    fun changeUser(){
        listUser = LoginViewModel.listUsers
        for(item in listUser) {
            if (!mAccountPrefManager.getEmail().equals(item.email))
            {
                Log.d("user", item.nama)
                menus.findItem(R.id.action_user_other).setTitle(item.nama)
                /*Glide.with(this)
                    .load(item.avatar)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(14)))
                    .into(menu.findItem(R.id.action_user_other).icon)*/
            }

        }
    }


}