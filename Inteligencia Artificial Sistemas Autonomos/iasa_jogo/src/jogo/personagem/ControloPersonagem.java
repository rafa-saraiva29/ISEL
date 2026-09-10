package jogo.personagem;

import agente.Accao;
import agente.Controlo;
import agente.Percepcao;
import ambiente.Evento;
import jogo.ambiente.ComandoJogo;
import jogo.ambiente.EventoJogo;
import maqest.Estado;
import maqest.MaquinaEstados;

/*
 * A classe ControloPersonagem é uma realização da interface Controlo e, por isso,
 * especifica a implementação do metodo processar. Este método vai processar um evento
 * obtido atraves de uma percepçao, gerando uma açao com a ajuda de uma maquina de estados 
 * definida no construtor
 */

public class ControloPersonagem implements Controlo{
    private MaquinaEstados maqEst;
    
    public ControloPersonagem(){
        // definiçao do alfabeto de entrada
        Estado procura = new Estado("Procura");
        Estado inspeccao = new Estado("Inspecçao");
        Estado observacao = new Estado("Observaçao");
        Estado registo = new Estado("Registo");

        // definiçao do alfabeto de saida
        Accao procurar = new Accao(ComandoJogo.PROCURAR);
        Accao aproximar = new Accao(ComandoJogo.APROXIMAR);
        Accao observar = new Accao(ComandoJogo.OBSERVAR);
        Accao fotografar = new Accao(ComandoJogo.FOTOGRAFAR);

        // definiçao das transiçoes, ou seja, especificaçao da funçao de transformaçao
        procura
            .transicao(EventoJogo.RUIDO, inspeccao, aproximar)
            .transicao(EventoJogo.SILENCIO, procura, procurar)
            .transicao(EventoJogo.ANIMAL, observacao, aproximar);
        
        inspeccao
            .transicao(EventoJogo.SILENCIO, procura)
            .transicao(EventoJogo.RUIDO, inspeccao, procurar)
            .transicao(EventoJogo.ANIMAL, observacao, aproximar);

        observacao
            .transicao(EventoJogo.FUGA, inspeccao)
            .transicao(EventoJogo.ANIMAL, registo, observar);
        
        registo
            .transicao(EventoJogo.FUGA, procura)
            .transicao(EventoJogo.FOTOGRAFIA, procura)
            .transicao(EventoJogo.ANIMAL, registo, fotografar);

        maqEst = new MaquinaEstados(procura);
    }

    public Estado getEstado(){
        return maqEst.getEstado();
    }

    @Override
    public Accao processar(Percepcao percepcao) {
        Evento evento = percepcao.getEvento();
        Accao accao = maqEst.processar(evento);
        mostrar();
        return accao;
    }

    private void mostrar(){
        System.out.printf("Estado: %s\n", getEstado().getNome());
    }
    
}
