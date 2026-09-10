from random import choice
from .resposta_mover import RespostaMover
from sae import Direccao

"""
Especializaçao da classe RespostaMover, em que o que é diferente é que a direçao
da açao associada à resposta é aleatoria
Exemplo de uma implementaçao incremental, onde o que é implementado é apenas o 
que é diferente
"""
class RespostaMoverAleat(RespostaMover):

    """
    Construtor da classe que gera uma direçao aleatoria, a partir do enumerado Direccao,
    com o auxilio da funçao choice da biblioteca random, e chama o construtor  da super
    classe, com a direçao gerada
    """
    def __init__(self):
        direccao_aleatoria = choice(list(Direccao))
        super().__init__(direccao_aleatoria)