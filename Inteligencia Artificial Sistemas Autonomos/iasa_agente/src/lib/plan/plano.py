from abc import ABC, abstractmethod

"""
Interface Plano que representa o contrato funcional para a implementação de planos de ação, no 
contexto de problemas resolvidos com raciocínio prático, mais especificamente, raciocínio sobre meios
(planeamento). Um plano de ação é o resultado deste tipo de raciocínio, ou seja, partindo dos objetivos
do agente e das ações que o agente é capaz de realizar, são gerados planos de ação que vão determinar
o comportamento do agente.
"""
class Plano(ABC):

    @abstractmethod
    def obter_accao(self, estado):
        """"""

    @abstractmethod
    def mostrar(self, vista):
        """"""