package com.example.myapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.activity.BotoesVelozes
import com.example.myapp.activity.JogoDaVelhaActivity

class MainActivity : AppCompatActivity() {

    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botaoJogoDaVelha = findViewById<Button>(R.id.botao_jogo_da_velha)
        val botaoBotoesVelozes = findViewById<Button>(R.id.botao_botoes_velozes)
        val botaoJogoDaMemoria = findViewById<Button>(R.id.botao_memoria)


        botaoJogoDaVelha.setOnClickListener {
            toast?.cancel()
            val intent = Intent(
                this,
                JogoDaVelhaActivity::class.java)
            startActivity(intent)
        }

        botaoBotoesVelozes.setOnClickListener {
            toast?.cancel()  // cancela Toast anterior
            val intent = Intent(
                this,
                BotoesVelozes::class.java)
            startActivity(intent)
        }

        botaoJogoDaMemoria.setOnClickListener {
            toast?.cancel()  // cancela Toast anterior
            toast = Toast.makeText(this, "Jogo Indisponivel", Toast.LENGTH_SHORT)
            toast?.show()
        }
    }
}
