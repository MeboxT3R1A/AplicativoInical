package tools

import java.io.File

fun gerarArquivoConsolidado(listaArquivos: List<String>, pastaSaida: String, nomeArquivo: String) {
    val pasta = File(pastaSaida)
    if (!pasta.exists()) pasta.mkdirs() // cria pasta se não existir

    val arquivoFinal = File(pasta, nomeArquivo)
    val sb = StringBuilder()

    for (caminho in listaArquivos) {
        val file = File(caminho)
        if (file.exists() && file.isFile) {
            sb.appendLine(file.name)
            sb.appendLine(file.readText())
            sb.appendLine("----")
        } else {
            sb.appendLine(file.name)
            sb.appendLine("Arquivo não encontrado ou não é um arquivo")
            sb.appendLine("----")
        }
    }

    arquivoFinal.writeText(sb.toString())
    println("Arquivo gerado em: ${arquivoFinal.absolutePath}")
}

fun main() {
    // Raiz fixa do seu projeto
    val raiz = "app/src/main/java/com/example/myapp/"

    // Lista dos arquivos relativos à raiz
    val arquivosRelativos = listOf(
        "activity/MainActivity.kt",
    )

    // Cria a lista de arquivos completos
    val arquivos = arquivosRelativos.map { caminho -> File(raiz + caminho) }

    // Pasta de saída
    val pastaSaida = File("app/outputs")
    if (!pastaSaida.exists()) pastaSaida.mkdirs()

    // Nome do arquivo final
    val nomeArquivo = "conteudo_arquivos.txt"

    // Geração do arquivo consolidado
    val arquivoFinal = File(pastaSaida, nomeArquivo)
    val sb = StringBuilder()
    for (file in arquivos) {
        sb.appendLine(file.name)
        sb.appendLine(if (file.exists()) file.readText() else "Arquivo não encontrado")
        sb.appendLine("----")
    }
    arquivoFinal.writeText(sb.toString())

    println("Arquivo gerado em: ${arquivoFinal.absolutePath}")
}
