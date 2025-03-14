package br.com.fiap.br.com.alura.alugames.main

import br.com.alura.alugames.service.ConsumoApi
import br.com.fiap.br.com.alura.alugames.model.Jogo
import java.util.*


fun main() {

    //Leitura dos dados
    val leitura = Scanner(System.`in`)

    println("Digite um código de jogo para buscar:")
    val busca = leitura.nextLine()
    val buscaApi = ConsumoApi()

    var meuJogo: Jogo? = null

    val resultado = runCatching {
        val informacaoJogo = buscaApi.buscaJogo(busca)
        //A variável "meuInfoJogo" precisa estar dentro do escopo runCatching, visto que ele pode gerar a exceção.
        // Caso ela esteja fora deste escopo, o erro não será tratado e, como consequência, vai cair no mesmo erro de antes.
        meuJogo = Jogo(
            informacaoJogo.info.title,
            informacaoJogo.info.thumb
        )
    }

    // "onFailure" executado quando falha
    resultado.onFailure {
        println("Jogo inexistente. Tente outro id.")
    }

    //"onSuccess" executado quando é sucedido
    resultado.onSuccess {
        println("Deseja inserir uma descrição personalizada? S/N")
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
    resultado.onSuccess {
        println("Busca realizada com sucesso!")
    }

}
