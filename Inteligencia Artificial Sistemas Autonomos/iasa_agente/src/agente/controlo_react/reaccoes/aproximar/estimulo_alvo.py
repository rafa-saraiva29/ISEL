from ecr.estimulo import Estimulo
from sae import Elemento

"""
Classe EstimuloAlvo implementa a interface Estimulo. Representa um estimulo
que está associado a existencia de um alvo, numa determinada direçao.
"""

class EstimuloAlvo(Estimulo):

    def __init__(self, direccao, gama = 0.9):
        self.__direccao = direccao
        self.__gama = gama
    
    """
    metodo que deteta ou nao o alvo, caso detete calcula a intensidade do estimulo,
    quanto maior a distancia, menor a intensidade
    caso nao detete alvo, a intensidade é zero
    """
    def detectar(self, percepcao):
        elemento, distancia, _ = percepcao[self.__direccao]
        if elemento == Elemento.ALVO:
            intensidade = self.__gama ** distancia
        else:
            intensidade = 0
        return intensidade