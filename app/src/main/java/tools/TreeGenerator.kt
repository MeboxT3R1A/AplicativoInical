package tools

import java.io.File

val necesarios: List<String> = listOf(
    "build",
    ".gradle",
    ".idea",
    ".git",
    ".gitignore",
    "test",
    "androidTest",
    "tools",
    "drawable",
    "mipmap-anydpi",
    "mipmap-hdpi",
    "mipmap-mdpi",
    "mipmap-xhdpi",
    "mipmap-xxhdpi",
    "mipmap-xxxhdpi",
    "values",
    "values-night",
    "xml"
)

val EXCLUDE = setOf(
    *necesarios.toTypedArray(),
)

val EXCLUDE_EXT = setOf(".iml", ".pro", ".kts")

fun shouldExclude(nome: String): Boolean {
    if (nome in EXCLUDE) return true
    if (EXCLUDE_EXT.any { nome.endsWith(it) }) return true
    return false
}

fun buildTree(dir: File, prefix: String = "", relPath: String = ""): List<String> {
    val entries = dir.listFiles()
        ?.filter { !shouldExclude(it.name) }
        ?.sortedBy { it.name }
        ?: return emptyList()

    val linhas = mutableListOf<String>()
    for ((i, entry) in entries.withIndex()) {
        val connector = if (i == entries.lastIndex) "└── " else "├── "
        val relEntry = if (relPath.isEmpty()) entry.name else "$relPath/${entry.name}"

        val linha = if (entry.isDirectory) {
            "$prefix$connector${entry.name}/"
        } else {
            "$prefix$connector${entry.name}"
        }

        linhas.add(linha)

        if (entry.isDirectory) {
            val extension = if (i == entries.lastIndex) "    " else "│   "
            linhas.addAll(buildTree(entry, prefix + extension, relEntry))
        }
    }
    return linhas
}

fun main() {
    // Raiz do projeto (ajuste se quiser mais ou menos abrangência)
    val raiz = File("app/")

    // Cria pasta outputs dentro de app/
    val pastaSaida = File("app/outputs")
    if (!pastaSaida.exists()) pastaSaida.mkdirs()

    // Nome do arquivo que você quiser
    val nomeArquivo = "estrutura.txt"

    val estrutura = listOf(raiz.name + "/") + buildTree(raiz)
    val saida = File(pastaSaida, nomeArquivo)

    saida.writeText(estrutura.joinToString("\n"))

    println("Estrutura salva em: ${saida.absolutePath}")
}
