from abc import ABC, abstractmethod

"""
Interface Estimulo que representa a deteçao de um estimulo
presente numa perceçao. Um estimulo define informaçao
ativadora de uma reaçao
"""

class Estimulo(ABC):
    @abstractmethod
    def detectar(self, percepcao):
        """Detetar estimulo"""

