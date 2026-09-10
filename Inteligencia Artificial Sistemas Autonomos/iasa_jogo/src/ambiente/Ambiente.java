package ambiente;

/**
 * Interface Ambiente que representa o meio onde se encontra o agente,
 * com o qual este consegue interagir e observar.
 * Esta classe tem uma relação de Dependência com as interfaces Evento
 * e Comando pois utiliza localmente instâncias dessas interfaces.
 * Uma interface representa um contrato funcional independente da implementação,
 * ou seja, os metodos definidos pela interface não sao implementados nesta, só
 * serão implementados nas classes que a realizarem, assim promovendo modularidade
 * através do encapsulamento da implementação.
 */

public interface Ambiente {
    public void evoluir();
    public Evento observar();
    public void executar(Comando comando);
}