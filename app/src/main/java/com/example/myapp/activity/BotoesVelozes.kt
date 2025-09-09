package com.example.myapp.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.R

class BotoesVelozes : AppCompatActivity(R.layout.activity_botoes_velozes) {

    private lateinit var container: FrameLayout
    private lateinit var mensagemInicial: TextView
    private lateinit var resetButton: Button
    private var totalBotoes = 50
    private var botoesRestantes = totalBotoes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Botões Velozes"

        container = findViewById(R.id.container)
        resetButton = findViewById(R.id.resetar)

        var totalCliques = 0
        container.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                totalCliques++
                println("Tela clicada $totalCliques vezes")
            }
            true
        }

        resetButton.visibility = View.GONE

        mensagemInicial = findViewById(R.id.mensagem_inicial)
        val mensagens = listOf("Isso será dificil... 3", "Estar preparado?... 2", "Vamos lá... 1")
        var indice = 0

        fun mostrarProximaMensagem() {
            if (indice < mensagens.size) {
                mensagemInicial.text = mensagens[indice]
                indice++
                mensagemInicial.postDelayed({ mostrarProximaMensagem() }, 1500)
            } else {
                mensagemInicial.visibility = View.GONE
                criarBotoes() // aqui começa o jogo
            }
        }

        mostrarProximaMensagem()

    }

    private fun criarBotoes() {
        val inicio = System.currentTimeMillis()
        container.post {
            container.removeAllViews() // limpa os antigos
            botoesRestantes = totalBotoes

            for (i in 1..totalBotoes) {
                val bt = Button(this)
                bt.text = "B$i"

                // define tamanho fixo maior
                val params = FrameLayout.LayoutParams(200, 100)
                bt.layoutParams = params

                val maxX = container.width - 170
                val maxY = container.height - 170

                bt.x = (0..maxX).random().toFloat()
                bt.y = (0..maxY).random().toFloat()

                container.addView(bt)
                bt.setOnClickListener {
                    bt.visibility = View.GONE
                    botoesRestantes--

                    if (botoesRestantes == 0) {
                        val tempoMs = System.currentTimeMillis() - inicio
                        val tempoSegundos = tempoMs.toDouble() / 1000
                        val tempoFormatado = String.format("%.2f", tempoSegundos)

                        mensagemInicial.visibility = View.VISIBLE
                        mensagemInicial.text = "Tempo: $tempoFormatado s"

                        resetButton.visibility = View.VISIBLE
                    }
                }
            }
        }

        resetButton.setOnClickListener {
            mensagemInicial.visibility = View.GONE
            resetButton.visibility = View.GONE
            criarBotoes()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish() // seta volta
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
