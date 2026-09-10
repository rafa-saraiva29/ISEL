package agente;

import ambiente.Evento;

/**
 * Classe Percepcao que representa a interpretação formada
 * pelo agente após observar um evento no ambiente.
 * Tem uma relação de associação com a interface Evento pois 
 * necessita conhecer o evento que lhe está associado.
 * Contém um atributo do tipo Evento disponivel apenas
 * para leitura (read only). Esta propriedade foi implementada
 * declarando o atributo como privado e gerando um método getter
 * público, para possibilitar o acesso ao valor do atributo.
 * Não é criado um método setter para que o valor não possa
 * ser alterado. 
 */

public class Percepcao {
    private Evento evento;
    
    public Percepcao(Evento evento) {
        this.evento = evento;
    }

    public Evento getEvento() {
        return evento;
    }
}
