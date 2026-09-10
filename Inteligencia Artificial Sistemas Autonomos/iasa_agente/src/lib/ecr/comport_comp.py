from .comportamento import Comportamento
from abc import abstractmethod

"""
Realização da interface Comportamento, ou seja, especificaçao de um comportamento,
neste caso um comportamento composto.
"""

class ComportComp(Comportamento):

    def __init__(self, comportamentos):
        self.__comportamentos = comportamentos

    
    def activar(self, percepcao):
        accoes = []
        for comportamento in self.__comportamentos:
            accao = comportamento.activar(percepcao)
            if accao:
                accoes.append(accao)
        
        if accoes:
            return self.seleccionar_accao(accoes)
        
    
    @abstractmethod
    def seleccionar_accao(self, accoes):
        """
        Processo de seleçao de açao. Processo que ocorre num comportamento composto
        para selecionar uma resposta a partir de todas as respostas possiveis
        """