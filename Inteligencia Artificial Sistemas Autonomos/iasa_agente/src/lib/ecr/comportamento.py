from abc import ABC, abstractmethod

"""
Interface Comportamento que representa um comportamento no contexto de agentes reativos.
Um agente reativo pode ser composto por várias reaçoes mas, de forma a reduzir complexidade
da arquitetura, aumentar a coesao e reduzir o acoplamento estas reaçoes sao modularizadas 
em comportamentos.
Um comportamento é um conjunto de reações que possuem semelhança de propósito
Se um comportamento for composto por apenas uma reação é um comportamento simples (classe Reaccao)
Se tiver várias reaçoes ou outros comportamentos é um comportamento composto (classe ComportComp)
"""

class Comportamento(ABC):

    @abstractmethod
    def activar(self, percepcao):
        """"""