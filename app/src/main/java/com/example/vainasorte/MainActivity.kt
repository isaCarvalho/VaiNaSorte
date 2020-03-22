package com.example.vainasorte

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
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

            // pega o limite infeiror na entrada
            val limInfText = findViewById<EditText>(R.id.limInferior).text

            // pega o limite superior na entrada
            val limSupText = findViewById<EditText>(R.id.limSuperior).text

            // valida o limite inferior
            var inf = this.checkNullorEmpty(limInfText.toString(), "Limite inferior não preenchido. Valor padrão utilizado",
                1)

            inf = checkNegative(inf)

            // valida o limite superior
            var sup = checkNullorEmpty(limSupText.toString(), "Limite superior não preenchido. Valor padrão utilizado",
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
            findViewById<TextView>(R.id.letterTextView).text = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".random().toString()
        }
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
