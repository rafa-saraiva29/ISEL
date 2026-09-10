from ecr.reaccao import Reaccao
from ..resposta.resposta_mover import RespostaMover
from .estimulo_alvo import EstimuloAlvo

"""
Classe AproximarDir representa uma reaçao (comportamento simples) que, neste caso
vai associar um estimulo (os alvos) a uma resposta (RespostaMover, movimento numa
determinada direçao). Esta classe é uma especializaçao da classe Reaccao
"""

class AproximarDir(Reaccao):
    """
    Esta classe associa um estimulo numa determinada direccao, a uma resposta nessa
    mesma direccao, invocando o construtor da classe Reaccao
    """
    def __init__(self, direccao):
        super().__init__(EstimuloAlvo(direccao), RespostaMover(direccao))