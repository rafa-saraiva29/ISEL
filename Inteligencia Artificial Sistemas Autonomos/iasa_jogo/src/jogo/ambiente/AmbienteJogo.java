package jogo.ambiente;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import ambiente.Ambiente;
import ambiente.Comando;
import ambiente.Evento;

/**
 * A classe AmbienteJogo é uma realização da interface Ambiente e, por isso,
 * especifica a implementação dos metodos evoluir, observar e executar.
 * Tem uma relaçao de dependencia com a classe ComandoJogo e uma relaçao de 
 * associaçao e composição com a classe EventoJogo
 * Como consequencia da relaçao de composiçao com EventoJogo, é necessario 
 * criar uma estrutura de dados para conter eventos. De forma a simplificar
 * a implementação do metodo gerarEvento, essa estrutura de dados é um
 * dicionario, neste caso um HashMap, que associa as teclas que serao usadas
 * pelo utilizador ao evento correspondente.
 */

public class AmbienteJogo implements Ambiente{
    private Evento evento;
    private Map<String, EventoJogo> eventos;
    private Scanner scanner = new Scanner(System.in);

    public AmbienteJogo(){
        eventos = new HashMap<String, EventoJogo>();
        eventos.put("s", EventoJogo.SILENCIO);
        eventos.put("r", EventoJogo.RUIDO);
        eventos.put("a", EventoJogo.ANIMAL);
        eventos.put("f", EventoJogo.FUGA);
        eventos.put("o", EventoJogo.FOTOGRAFIA);
        eventos.put("t", EventoJogo.TERMINAR);
    }

    public Evento getEvento(){
        return evento;
    }

    public void evoluir(){
        evento = gerarEvento();
    }

    public Evento observar(){
        if (evento != null) {
            evento.mostrar();
        }        
        return evento;
    }

    public void executar(Comando comando){
        comando.mostrar();
    }

    private EventoJogo gerarEvento() {
        System.out.println("\nEvento?");
        String comando = scanner.next();
        return eventos.get(comando);
    }
}
