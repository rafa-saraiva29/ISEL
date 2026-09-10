from abc import ABC, abstractmethod

"""
Interface Avaliador que representa o contrato funcional para definir um avaliador.
Um avaliador representa a função de avaliação utilizada pelas procuras melhor-primeiro,
definida no método prioridade(), e este será definido pelas classes que implementarem esta
interface, pois esta função pode ter em conta o custo do nó, uma heurística ou ambos, 
dependendo do método de procura.
"""
class Avaliador(ABC):

    @abstractmethod
    def prioridade(self, no):
        """"""