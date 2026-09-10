from ecr.comportamento import Comportamento
from sae import Accao

"""
Classe de teste que representa um comportamento e por isso é uma especializaçao
da classe Comportamento. Este comportamento é ativado com uma percepcao e gera 
uma açao com base na direçao dessa percepcao
"""
class ComportTeste(Comportamento):

    def activar(self, percepcao):
        return Accao(percepcao.direccao)