package jogo;

import jogo.ambiente.AmbienteJogo;
import jogo.ambiente.EventoJogo;
import jogo.personagem.Personagem;

public class Jogo {
    private static AmbienteJogo ambiente;
    private static Personagem personagem;
    public static void main(String[] args) {
        ambiente = new AmbienteJogo();
        personagem = new Personagem(ambiente);
        executar();
    }

    /**
     * Metodo privado, auxiliar ao metodo main que executa o motor de jogo.
     * De acordo com o modelo de interaçao, este metodo invoca o metodo evoluir do ambiente
     * e o metodo executar da personagem, dentro de um loop. Optou-se por utilizar um loop 
     * do tipo do-while porque antes de se fazer a verificaçao da condiçao, que involve saber se o 
     * evento do ambiente é igual a Terminar, é necessario que o ambiente evolua uma vez.
     */
    private static void executar(){
        do{
            ambiente.evoluir();
            personagem.executar();
        }
        while(ambiente.getEvento() != EventoJogo.TERMINAR);
    }
}
