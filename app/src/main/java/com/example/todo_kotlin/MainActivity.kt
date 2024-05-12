package com.example.todo_kotlin

import ItemAdapter
import SwipeToDeleteCallback
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val input: EditText = findViewById(R.id.editText)
        val addButton: Button = findViewById(R.id.addButton)
        val clearButton: Button = findViewById(R.id.removeButton)
        val recyclerView: RecyclerView = findViewById(R.id.listView)
        val itemList: ArrayList<String> = ArrayList()
        val adapter = ItemAdapter(itemList, this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        adapter.loadList()

        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)

        addButton.setOnClickListener {
            val newItem = input.text.toString()
            if (newItem.isNotEmpty()) {
                newItem.trim()
                itemList.add(newItem)
                adapter.saveList()
                adapter.notifyDataSetChanged()
                input.text.clear()
            }
        }

        clearButton.setOnClickListener {
            itemList.clear()
            adapter.saveList()
            adapter.notifyDataSetChanged()
        }
    }
}
