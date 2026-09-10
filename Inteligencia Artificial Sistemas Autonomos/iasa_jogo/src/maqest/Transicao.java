package maqest;

import agente.Accao;

/*
 * No contexto de uma máquina de estados, os objetos desta classe são utilizados 
 * para definir as transições entre os estados do sistema, especificando o próximo 
 * estado a ser alcançado e a ação causada por essa transição.
 */

public class Transicao {
    private Estado estadoSucessor;
    private Accao accao;

    public Transicao(Estado estadoSucessor, Accao accao) {
        this.estadoSucessor = estadoSucessor;
        this.accao = accao;
    }

    public Estado getEstadoSucessor() {
        return estadoSucessor;
    }

    public Accao getAccao() {
        return accao;
    }
}
