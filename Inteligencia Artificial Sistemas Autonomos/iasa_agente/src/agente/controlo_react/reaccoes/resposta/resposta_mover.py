from ecr.resposta import Resposta
from sae import Accao

"""
Classe RespostaMover, é uma especialização da classe Resposta
O que a diferencia é que a accao que esta classe tem associada tem uma direçao especifica,
especificada no construtor
"""

class RespostaMover(Resposta):

    def __init__(self, direccao):
        super().__init__(Accao(direccao))