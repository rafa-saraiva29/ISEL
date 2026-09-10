package jogo.ambiente;

import ambiente.Comando;

/**
 * Esta classe é uma realização da interface Comando
 */

public enum ComandoJogo implements Comando{
    PROCURAR,
    APROXIMAR,
    OBSERVAR,
    FOTOGRAFAR;

    /**
     * este metodo permite mostrar ao utilizador, atraves da consola,
     * o comando a utilizar
     */
    @Override
    public void mostrar() {
       System.out.printf("Comando: %s\n", this);
    }
    
}
