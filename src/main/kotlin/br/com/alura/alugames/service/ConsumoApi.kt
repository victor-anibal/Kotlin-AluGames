package br.com.alura.alugames.service

import br.com.fiap.br.com.alura.alugames.model.InfoJogo
import com.google.gson.Gson
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers

class ConsumoApi {
   fun buscaJogo(id: String): InfoJogo {
        val endereco = "https://www.cheapshark.com/api/1.0/games?id=$id"

        val client: HttpClient = HttpClient.newHttpClient()
        val request = HttpRequest.newBuilder()
            .uri(URI(endereco))
            .build()
        val response = client.send(request, BodyHandlers.ofString())
        val json = response.body()
        val gson = Gson()
        val meuInfoJogo = gson.fromJson(json, InfoJogo::class.java)

        return meuInfoJogo
    }


    //Declarar a Client
    // Responsável por pegar uma requisição feita pelo usuário no navegador e enviá-la(Request) para o servidor
    // E pegar a requisição processada pelo servidor(Response), que serão exibidas para o usuário, enviá-las para o Client.

    //Declarar a Request
    // Comunicação do Client para o servidor


    //Declarar Response
    //Comunicação do servidor para o Client
}

// Client faz a requisição ao servidor por meio de REQUEST.
// Servidor envia a requisição para o Client por meio de Response.