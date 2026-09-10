package agente;

import ambiente.Comando;

/**
 * Classe Accao que representa a consequência do processamento 
 * de uma perceção por parte do Controlo e que dará origem a 
 * um determinado comando que será executado sobre o ambiente.
 * Tem uma relação de associação com a interface Comando pois 
 * necessita conhecer o comando que lhe está associado.
 * Contém um atributo do tipo Comando disponivel apenas
 * para leitura (read only). Esta propriedade foi implementada
 * declarando o atributo como privado e gerando um método getter
 * público, para possibilitar o acesso ao valor do atributo.
 * Não é criado um método setter para que o valor não possa
 * ser alterado. 
 */

public class Accao {
    private Comando comando;

    public Accao(Comando comando) {
        this.comando = comando;
    }

    public Comando getComando() {
        return comando;
    }
}
