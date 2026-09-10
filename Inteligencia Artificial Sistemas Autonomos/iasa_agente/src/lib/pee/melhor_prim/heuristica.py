from abc import ABC, abstractmethod

"""
Interface Heuristica que representa o contrato funcional para definir uma
heurística. Uma heurística é uma função que classifica alternativas em algoritmos 
de pesquisa em cada etapa de ramificação com base nas informações disponíveis para 
decidir qual ramificação seguir. Ou seja, reflete conhecimento do domínio do problema
para guiar a procura. Pode ser uma estimativa do custo do percurso do nó atual até ao 
objetivo. De certa forma, troca a exatidão ou precisão pela otimização do tempo de 
execução do algoritmo. A função heurística é definida no método h(). Uma heurística
pode ser admissível, isto significa que a estimativa de custo é sempre igual ou 
inferior ao custo efetivo mínimo. Um exemplo de uma heurística admissível é a distância
euclidiana. Esta é admissível porque a estimativa de custo representa a distância em linha
reta até ao objetivo, portanto, o custo efetivo nunca será menor que a estimativa.
"""
class Heuristica(ABC):

    @abstractmethod
    def h(self, estado):
        """"""