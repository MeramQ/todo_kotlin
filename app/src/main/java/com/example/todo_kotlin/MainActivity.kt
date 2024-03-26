package com.example.todo_kotlin

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper

class MainActivity : AppCompatActivity() {

    private lateinit var input: EditText
    private lateinit var addButton: Button
    private lateinit var removeButton: Button
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var toDoList: MutableList<String>
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        input = findViewById(R.id.editText)
        addButton = findViewById(R.id.addButton)
        removeButton = findViewById(R.id.removeButton)
        listView = findViewById(R.id.listView)

        sharedPreferences = getSharedPreferences("todo_list", Context.MODE_PRIVATE)

        val savedList = sharedPreferences.getStringSet("todo_items", HashSet()) ?: HashSet()
        toDoList = savedList.toMutableList()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, toDoList)
        listView.adapter = adapter

        addButton.setOnClickListener {
            val listItem: String = input.text.toString()
            if (listItem.isNotEmpty()) {
                toDoList.add(listItem)
                adapter.notifyDataSetChanged()
                input.text.clear()
                saveList()
            } else {
                Toast.makeText(this, "Wprowadź rzecz do zrobienia", Toast.LENGTH_SHORT).show()
            }
        }

        removeButton.setOnClickListener {
            toDoList.clear()
            adapter.notifyDataSetChanged()
            saveList()
        }

        listView.setOnItemLongClickListener { parent, view, position, id ->
            toDoList.removeAt(position)
            adapter.notifyDataSetChanged()
            saveList()
            true
        }
    }

    override fun onPause() {
        super.onPause()
        saveList()
    }

    private fun saveList() {
        val editor = sharedPreferences.edit()
        editor.putStringSet("todo_items", HashSet(toDoList))
        editor.apply()
    }
}
