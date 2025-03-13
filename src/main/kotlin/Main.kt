package br.com.fiap

import com.google.gson.Gson
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.util.*


fun main() {

    //Leitura dos dados
    val leitura = Scanner(System.`in`)
    println("Digite um código de jogo para buscar:")
    val busca = leitura.nextLine()

    val endereco = "https://www.cheapshark.com/api/1.0/games?id=$busca"

    //DECLARANDO O CLIENT
    val client: HttpClient = HttpClient.newHttpClient()

    //DECLARANDO O REQUEST
    val request = HttpRequest.newBuilder()
        .uri(URI.create(endereco))
        .build()

    //DECLARANDO O RESPONSE
    val response = client
        .send(request, BodyHandlers.ofString())
    val json = response.body()

    val gson = Gson()

//    try {
//        val meuInfoJogo = gson.fromJson(json, InfoJogo::class.java)
//
//        val meujogo = Jogo(
//            meuInfoJogo.info.title,
//            meuInfoJogo.info.thumb
//        )
//        println(meujogo)
//
//    } catch (ex: RuntimeException) {
//        println("Jogo indisponível. Tente outro id.")
//    }

    var meuJogo: Jogo? = null

    val resultado = runCatching {
        //A variável "meuInfoJogo" precisa estar dentro do escopo runCatching, visto que ele pode gerar a exceção.
        // Caso ela esteja fora do deste escopo, o erro não será tratado e, como consequência, vai cair no mesmo erro de antes.
        val meuInfoJogo = gson.fromJson(json, InfoJogo::class.java)
        meuJogo = Jogo(
            meuInfoJogo.info.title,
            meuInfoJogo.info.thumb
        )
    }
    //O que acontece quando falha
    resultado.onFailure {
        println("Jogo inextistente. Tente outro id.")
    }

    //O que acontece quando é sucedido
    resultado.onSuccess {
        println("Deseja inserir uma descrição personalizada?")
        val opcao = leitura.nextLine()
        if (opcao.equals("S", true)) {
            println("Insira a descrição personalizada para o jogo: ")
            val descricaoPersonalizada = leitura.nextLine()
            meuJogo?.descricao = descricaoPersonalizada
        } else {
            meuJogo?.descricao = meuJogo?.titulo
        }
        println(meuJogo)

    }

}
