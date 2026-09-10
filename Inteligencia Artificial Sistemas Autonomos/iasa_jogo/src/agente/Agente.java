package agente;

import ambiente.Ambiente;
import ambiente.Evento;

/**
 * Classe Agente que representa o interveniente no ambiente,
 * que é capaz de observar o ambiente, formando perceções (método percepcionar()),
 * processar as perceções criadas, gerando ações, e atuar
 * sobre essas ações (método actuar()).
 * Esta classe tem uma relação de associação com a interface 
 * Ambiente porque o Agente necessita de conhecer o ambiente
 * para poder interagir com ele, e por isso tem um atributo 
 * do tipo Ambiente.
 * Tem também uma relação de composição com a interface Controlo,
 * porque o controlo é uma parte de um agente, por isso, quando 
 * é criado um agente, também é criado um controlo. Isto significa
 * que existe um atributo do tipo Controlo que tem que ser 
 * inicializado no construtor. Como uma interface não pode ser instanciada,
 * a instancia é recebida como parametro do construtor.
 */

public class Agente {
    private Ambiente ambiente;
    private Controlo controlo;

    public Agente(Ambiente ambiente, Controlo controlo) {
        this.ambiente = ambiente;
        this.controlo = controlo;
    }

    public void executar(){
        Percepcao percepcao = percepcionar();
        Accao accao = controlo.processar(percepcao);
        actuar(accao);
    }

    protected Percepcao percepcionar(){
        Evento evento = ambiente.observar();
        return new Percepcao(evento);
    }

    protected void actuar(Accao accao){
        if (accao != null) {
            ambiente.executar(accao.getComando());
        }
    }
}
