package maqest;

import java.util.HashMap;
import java.util.Map;

import agente.Accao;
import ambiente.Evento;

/*
 * Classe Estado que representa um estado no contexto de uma maquina de estados.
 * O estado representa uma configuraçao particular do sistema num determinado momento.
 * Por composicçao com a classe Transicao, a classe tem como atributo um dicionario 
 * que associa um evento a uma determinada transiçao de estado.
 */

public class Estado {
    private String nome;
    private Map<Evento, Transicao> transicoes;

    public Estado(String nome) {
        this.nome = nome;
        transicoes = new HashMap<Evento, Transicao>();
    }

    public String getNome() {
        return nome;
    }

    public Transicao processar(Evento evento) {
        return transicoes.get(evento);
    }

    /*
     * Os metodos transicao(), sao responsaveis por implementar a funçao de transformaçao do sistema
     * Sao exemplo de polimorfismo por terem o mesmo nome mas implementaçoes e parametros ligeiramente 
     * diferentes porque sao usados em contextos diferentes
     * Sao exemplo de fatorizaçao porque o método mais abrangente realiza a maior parte da implementaçao
     * e o metodo menos abragente utiliza o outro metodo de modo a eliminar a redundancia
     * O método menos abrangente implementa apenas a funçao delta em que a entrada é o evento, o estado
     * atual é o proprio(this) e é definido o estado sucessor
     * O método mais abrangente implementa tambem a funçao lambda em que a saida é a accao
     */
    public Estado transicao(Evento evento, Estado estadoSucessor){
        return transicao(evento, estadoSucessor, null);
    }
    
    public Estado transicao(Evento evento, Estado estadoSucessor, Accao accao) {
        Transicao transicao = new Transicao(estadoSucessor, accao);
        transicoes.put(evento, transicao);
        return this;
    }
}
