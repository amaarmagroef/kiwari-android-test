package com.chataja.test.features.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chataja.test.R
import com.chataja.test.databinding.TestItemChatBinding
import com.chataja.test.features.main.model.ChatModelData
import com.chataja.test.utils.BindableViewHolder


/**
 * Created by siapaSAYA on 6/9/2020
 */

class UserAdapter : RecyclerView.Adapter<UserAdapter.ItemViewHolder>() {

    private var data = mutableListOf<ChatModelData>()


    fun update(data: List<ChatModelData>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.test_item_chat,
            parent,
            false)
        return ItemViewHolder(view)

    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val model = data[position]
        holder.bind(model)

    }

    class ItemViewHolder(view: View) :
        BindableViewHolder<TestItemChatBinding, ChatModelData>(view) {
        override fun bind(viewModel: ChatModelData) {
            binding?.model = viewModel
            binding!!.testChat.text = viewModel.chat
            binding!!.testUsername.text = viewModel.username
            binding!!.testTime.text = viewModel.time
        }
    }


}