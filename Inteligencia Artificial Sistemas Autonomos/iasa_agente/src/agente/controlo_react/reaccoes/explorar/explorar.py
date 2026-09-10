from ecr.comportamento import Comportamento
from ..resposta.resposta_mover_aleat import RespostaMoverAleat

"""
Classe Explorar que representa um comportamento em que o agente se move
aleatoriamente pelo ambiente. A classe é uma realizaçao da classe comportamento
e por isso implementa o metodo activar()
O comportamento explorar é um comportamento fixo, ou seja, é um comportamento que 
produz uma resposta sem necessitar de um estimulo.
Outros tipos de comportamentos sao a reaçao, que produz respostas com base em estimulos, e o 
comportamento composto que é um conjunto de sub-comportamentos
"""
class Explorar(Comportamento):

    """
    metodo activar que ativa o comportamento, isto é, ativa a resposta com base numa
    percepcao, gerando uma açao. neste caso a resposta é uma RespostaMoverAlet(), pois
    esta gera açoes com uma direçao aleatoria
    """
    def activar(self, percepcao):
        resposta = RespostaMoverAleat()
        return resposta.activar(percepcao)