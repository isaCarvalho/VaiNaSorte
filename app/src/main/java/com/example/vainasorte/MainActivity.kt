package com.example.vainasorte

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(), NumberDialogFragment.NumberDialogListener {

    var tab : TabLayout? = null
    var pager : ViewPager? = null

    companion object {
        var limInfText: String = "1"
        var limSupText: String = "100"
    }

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

            if (tab!!.selectedTabPosition == 0)
                sortear(limInfText, limSupText)
            else
                findViewById<TextView>(R.id.letterTextView).text = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".random().toString()
        }
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        Toast.makeText(applicationContext, "Valores padrão utilizados", Toast.LENGTH_SHORT).show()

        limInfText = "1"
        limSupText = "100"
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, limiteSup: String, limiteInf: String) {
        Toast.makeText(applicationContext, "Valores selecionados", Toast.LENGTH_SHORT).show()

        limInfText = limiteInf
        limSupText = limiteSup
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflate : MenuInflater = menuInflater
        inflate.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId)
        {
            R.id.copiar -> true

            R.id.escolher -> {
                showNumberDialog()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun sortear(limInf : String, limSup : String)
    {
        // valida o limite inferior
        var inf = this.checkNullorEmpty(limInf, "Limite inferior não preenchido. Valor padrão utilizado",
            1)

        inf = checkNegative(inf)

        // valida o limite superior
        var sup = checkNullorEmpty(limSup, "Limite superior não preenchido. Valor padrão utilizado",
            100)

        sup = checkNegative(sup)

        // se os limites forem iguais, utiliza os valores padrao
        if (sup == inf)
        {
            Toast.makeText(applicationContext, "Limite superior igual ao limite inferior. Valores padrão serão utilizados",
                Toast.LENGTH_SHORT).show()

            inf = 1
            sup = 100
        }

        // se o limite superior for menor que o inferior, troca os valores
        if (sup < inf)
        {
            Toast.makeText(applicationContext, "Limite superior menor que o limite inferior. Valores serão trocados",
                Toast.LENGTH_SHORT).show()

            val aux = inf
            inf = sup
            sup = aux
        }

        // faz o sorteio
        findViewById<TextView>(R.id.numbersTextView).text = (inf..sup).random().toString()
    }

    private fun showNumberDialog() {
        val dialog = NumberDialogFragment()
        dialog.show(supportFragmentManager, "NumberDialogFragment")
    }

    // verifica se o valor e negativo -- apenas para validacao
    private fun checkNegative(value : Int) : Int
    {
        if (value < 0)
            return value * (-1)

        return value
    }

    // valida os valores de entrada
    private fun checkNullorEmpty(text : String, message : String, defaultValue : Int) : Int
    {
        // converte para inteiro ou nulo
        val value = text.toIntOrNull()

        // se o texto for vazio
        if (text.isEmpty())
        {
            // dispara a mensagem e retorna o valor padrão
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
            return defaultValue
        }
        else
        {
            // se for nulo, retorna o valor padrão
            if (value == null)
                return defaultValue
        }

        // retorna o valor de entrada
        return value
    }
}
