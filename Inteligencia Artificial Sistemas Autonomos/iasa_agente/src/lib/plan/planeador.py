from abc import ABC, abstractmethod

"""
Interface Planeador representa o contrato funcional a utilizar para implementar planeadores no contexto
de planeamento automático. Este é realizado com base em métodos de raciocínio automático como a procura
em espaço de estados ou processo de decisão de Markov. O planeador é responsável por gerar os planos de 
ação, com base no modelo do problema e nos objetivos que se pretendem atingir. 
"""
class Planeador(ABC):

    @abstractmethod
    def planear(self, modelo_plan, objectivos):
        """"""