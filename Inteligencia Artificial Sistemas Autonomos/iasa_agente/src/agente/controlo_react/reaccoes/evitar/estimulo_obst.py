from ecr.estimulo import Estimulo

"""
Classe EstimuloObst implementa a interface Estimulo. Representa um estimulo
que está associado a existencia de um obstaculo, numa determinada direçao.
"""
class EstimuloObst(Estimulo):

    def __init__(self, direccao, intensidade=1):
        self.__direccao = direccao
        self.__intensidade = intensidade

    """
    metodo que deteta ou nao o obstaculo, caso detete a intensidade do estimulo é
    maxima, corresponde a intensidade iniciada no construtor, definida a 1 por omissao
    caso nao detete obstaculo, a intensidade é zero
    """
    def detectar(self, percepcao):
        if percepcao.contacto_obst(self.__direccao):
            intensidade = self.__intensidade
        else:
            intensidade = 0
        return intensidade