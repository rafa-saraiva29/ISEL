package jogo.ambiente;

import ambiente.Evento;

/**
 * Esta classe é uma realização da interface Evento
 */

public enum EventoJogo implements Evento{
    SILENCIO,
    RUIDO,
    ANIMAL,
    FUGA,
    FOTOGRAFIA,
    TERMINAR;

    /**
     * este metodo permite mostrar ao utilizador, atraves da consola,
     * o evento atual
     */
    @Override
    public void mostrar(){
        System.out.printf("Evento: %s\n", this);
    }
}
