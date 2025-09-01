package com.example.myapp.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.R

class JogoDaVelhaActivity : AppCompatActivity() {
    private var rodada = 0
    private lateinit var botoes: List<List<Button>>
    private lateinit var resetar: Button

    private var nomeJogador1 = "Player 1"
    private var nomeJogador2 = "Player 2"

    // todas as combinações de vitória (linhas, colunas, diagonais)
    private val combinacoes = listOf(
        listOf(
            Pair(0,0),
            Pair(0,1),
            Pair(0,2)),

        listOf(
            Pair(1,0),
            Pair(1,1),
            Pair(1,2)),

        listOf(
            Pair(2,0),
            Pair(2,1),
            Pair(2,2)),

        listOf(
            Pair(0,0),
            Pair(1,0),
            Pair(2,0)),

        listOf(
            Pair(0,1),
            Pair(1,1),
            Pair(2,1)),

        listOf(
            Pair(0,2),
            Pair(1,2),
            Pair(2,2)),

        listOf(
            Pair(0,0),
            Pair(1,1),
            Pair(2,2)),

        listOf(
            Pair(0,2),
            Pair(1,1),
            Pair(2,0))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogo_da_velha)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Jogo da Velha"


        botoes = listOf(
            // 1 linha valor 0
            listOf(
                findViewById(R.id.celula00), // botão 1 da linha 1, valor 0,0
                findViewById(R.id.celula01), // botão 2 da linha 1, valor 0,1
                findViewById(R.id.celula02)),// botão 3 da linha 1, valor 0,2
            // 2 linha valor 1
            listOf(
                findViewById(R.id.celula10), // botão 1 da linha 2, valor 1,0
                findViewById(R.id.celula11), // botão 2 da linha 2, valor 1,1
                findViewById(R.id.celula12)),// botão 3 da linha 2, valor 1,2
            // 3 linha valor 2
            listOf(
                findViewById(R.id.celula20), // botão 1 da linah 3, valor 2,0
                findViewById(R.id.celula21), // botão 2 da linah 3, valor 2,1
                findViewById(R.id.celula22)) // botão 3 da linah 3, valor 2,2
        )

        resetar = findViewById(R.id.reset)

        for (linha in botoes) {
            for (botao in linha) {
                botao.setOnClickListener {
                    if (botao.text.isNotEmpty()) return@setOnClickListener

                    if (rodada % 2 == 0) {
                        botao.text = "X"
                    } else {
                        botao.text = "O"
                    }
                    rodada++

                    val vencedor = verificarVencedor()
                    if (vencedor != null) {
                        Toast.makeText(
                            this,
                            "Jogador '$vencedor' venceu!",
                            Toast.LENGTH_LONG).show()
                        resetar.visibility = View.VISIBLE
                        desabilitarTabuleiro()
                        return@setOnClickListener
                    }

                    if (rodada == 9) {
                        Toast.makeText(
                            this,
                            "Deu velha!",
                            Toast.LENGTH_LONG).show()
                        resetar.visibility = View.VISIBLE
                        desabilitarTabuleiro()
                        return@setOnClickListener
                    }
                }
            }
        }

        resetar.setOnClickListener {
            for (linha in botoes) {
                for (botao in linha) {
                    botao.text = ""
                    botao.isEnabled = true
                }
            }
            rodada = 0
            resetar.visibility = View.GONE
        }
    }

    private fun verificarVencedor(): String? {
        for (comb in combinacoes) {
            val (a1, a2, a3) = comb
            val b1 = botoes[a1.first][a1.second].text.toString()
            val b2 = botoes[a2.first][a2.second].text.toString()
            val b3 = botoes[a3.first][a3.second].text.toString()

            if (b1.isNotEmpty() && b1 == b2 && b2 == b3) {
                return if (b1 == "X") nomeJogador1 else nomeJogador2
            }
        }
        return null
    }

    private fun desabilitarTabuleiro() {
        for (linha in botoes) {
            for (botao in linha) {
                botao.isEnabled = false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {   // clicou na seta
                finish()             // fecha a Activity atual
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}