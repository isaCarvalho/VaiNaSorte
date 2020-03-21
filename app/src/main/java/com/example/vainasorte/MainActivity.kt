package com.example.vainasorte

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    var tab : TabLayout? = null
    var pager : ViewPager? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tab = findViewById(R.id.tab)
        pager = findViewById(R.id.pager)

        tab!!.addTab(tab!!.newTab().setText("Números"))
        tab!!.addTab(tab!!.newTab().setText("Letras"))

        tab!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = MyAdapter(this, supportFragmentManager, tab!!.tabCount)
        pager!!.adapter = adapter

        pager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab))

        tab!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                pager!!.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener {
            findViewById<TextView>(R.id.numbersTextView).text = (1..100).random().toString()
            findViewById<TextView>(R.id.letterTextView).text = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".random().toString()
        }
    }
}
