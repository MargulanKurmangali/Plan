package com.example.anonymus.plan

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.Menu
import android.view.MenuItem
import android.text.method.TextKeyListener.clear
import com.example.anonymus.plan.R.id.lstTask
import android.graphics.drawable.Drawable
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.*


class MainActivity : AppCompatActivity() {
    var Data: Data? = null
    var mAdapter: ArrayAdapter<String>? = null
    var lstTask: ListView? = null
    private val TAG = "MainActivity"
//    private var theDate: TextView? = null
//    private var fab: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        theDate = findViewById<View>(R.id.date) as TextView
//        fab = findViewById<View>(R.id.fab) as Button


        Data = Data(this)

        lstTask = findViewById(R.id.lstTask)

        loadTaskList()
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

//        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
//        fab!!.setOnClickListener { view ->
//            Snackbar.make(view, "Add task", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
    }

    private fun loadTaskList() {
        val taskList = Data!!.getTaskLists()
        if (mAdapter == null) {
            mAdapter = ArrayAdapter<String>(this, R.layout.row, R.id.task_title, taskList)
            lstTask!!.setAdapter(mAdapter)
        } else {
            mAdapter!!.clear()
            mAdapter!!.addAll(taskList)
            mAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        //Change menu icon color
        val icon = menu.getItem(0).icon
        icon.mutate()
        icon.setColorFilter(resources.getColor(android.R.color.white), PorterDuff.Mode.SRC_IN)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_task -> {
                val taskEditText = EditText(this)
                val dialog = AlertDialog.Builder(this)
                        .setTitle("Add New Task")
                        .setMessage("What do you want to do next?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", DialogInterface.OnClickListener { dialog, which ->
                            val task = taskEditText.text.toString()
                            Data!!.insertNewTask(task)
                            loadTaskList()
                        })
                        .setNegativeButton("Cancel", null)
                        .create()
                dialog.show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun deleteTask(view: View) {
        val parent = view.parent as View
        val taskTextView = parent.findViewById<View>(R.id.task_title) as TextView
        Log.e("String", taskTextView.text as String)
        val task = taskTextView.text.toString()
        Data!!.deleteTask(task)
        loadTaskList()
    }






}
