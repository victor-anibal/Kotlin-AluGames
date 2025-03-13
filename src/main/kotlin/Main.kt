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


    val resultado = runCatching {
    val meuInfoJogo = gson.fromJson(json, InfoJogo::class.java)

        val meujogo = Jogo(
            meuInfoJogo.info.title,
            meuInfoJogo.info.thumb
        )
        println(meujogo)
    }

    resultado.onFailure {
        println("Jogo inextistente. Tente outro id.")
    }
}