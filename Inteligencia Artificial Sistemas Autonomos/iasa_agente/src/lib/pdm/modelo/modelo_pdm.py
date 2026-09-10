from abc import ABC, abstractmethod

"""
Interface ModeloPDM representa o contrato funcional a utilizar por classes que definam modelos 
que utilizem o processo de decisão de Markov. As classes que realizem esta interface têm que 
implementar métodos que ajudam a definir a representação do mundo.
"""
class ModeloPDM(ABC):

    @abstractmethod
    def S(self):
        """"""

    @abstractmethod
    def A(self, s):
        """"""

    @abstractmethod
    def T(self, s, a, sn):
        """"""

    @abstractmethod
    def R(self, s, a, sn):
        """"""
    
    @abstractmethod
    def suc(self, s, a):
        """"""