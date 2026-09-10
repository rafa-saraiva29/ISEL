package maqest;

import agente.Accao;
import ambiente.Evento;

/**
 * Classe MaquinaEstados que representa a implementação de uma máquina de estados geral.
 * Uma máquina de estados é um modelo matematico usado para descrever o comportamento de 
 * um sistema que pode estar num número finito de estados distintos, em momentos específicos.
 * Consiste num conjunto finito de estados, um conjunto finito de transiçoes entre esses estados 
 * e uma funçao de transformaçao que determina o estado para qual o sistema deve transitar, 
 * quando ocorre um evento, e as saídas.
 * Os varios estados que o sistema pode assumir e a forma como eles evoluem ao longo do tempo
 * constitui a dinamica do sistema. Esta dinamica pode ser descrita como uma funçao de transformaçao
 * que perante um estado e as entradas, produz o estado seguinte e as saidas. O conjunto de simbolos
 * que a maquina de estados admite na sua entrada designa-se alfabeto de entrada. No contexto do nosso
 * jogo representa as teclas de input que o utilizador usa para indicar o evento observado. o conjunto
 * de simbolos que a maquina de estados produz a saida designa se alfabeto de saida. No nosso contexto
 * é o conjunto de comandos definidos no enumerado ComandoJogo. 
 * A funçao de transformaçao do sistema pode ser descrita com base em duas funçoes distintas
 * funçao delta: funçao de transiçao de estado, a partir do estado atual e das entradas, gera o estado seguinte
 * funçao lambda: funçao de saida, a partir do estado atual e das entradas, gera as saidas
 */

public class MaquinaEstados {
    private Estado estado;

    
    public MaquinaEstados(Estado estadoInicial) {
        estado = estadoInicial;
    }

    public Estado getEstado() {
        return estado;
    }

    public Accao processar(Evento evento){
        Transicao transicao = estado.processar(evento);

        if (transicao != null) {
            estado = transicao.getEstadoSucessor();
            return transicao.getAccao();
        }

        return null;
    }
}
