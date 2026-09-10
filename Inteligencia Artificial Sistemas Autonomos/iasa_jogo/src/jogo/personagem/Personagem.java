package jogo.personagem;

import agente.Agente;
import jogo.ambiente.AmbienteJogo;

/**
 * Classe Personagem tem uma relação de generalização com a classe Agente,
 * sendo uma especialização deste, fazendo uso do mecanismo de fatorização
 * designado como herança. O construtor da classe pai (Agente) tem que ser
 * invocado no construtor da classe Personagem.
 * É também um exemplo de polimorfismo porque apeser de Personagem ser um
 * Agente, o seu construtor apresenta ligeiras diferenças.
 */

public class Personagem extends Agente{
    public Personagem(AmbienteJogo ambiente) {
        super(ambiente, new ControloPersonagem());
    }
}
