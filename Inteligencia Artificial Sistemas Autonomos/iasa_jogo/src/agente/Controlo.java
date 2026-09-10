package agente;

/** 
 * Interface Controlo que representa o "pensamento" do agente 
 * porque permite que este utilize uma perceção e forme uma
 * ação adequada, através do método processar().
 * Esta classe tem um relação de Dependencia com as classes
 * Percepcao e Accao pois utiliza localmente instancias dessas
 * classes.
 */

public interface Controlo {
    public Accao processar(Percepcao percepcao);
}
