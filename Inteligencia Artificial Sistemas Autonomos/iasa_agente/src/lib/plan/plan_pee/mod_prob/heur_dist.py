from pee.melhor_prim.heuristica import Heuristica
from math import dist

"""
Classe HeurDist é uma realização da interface Heuristica. Esta classe representa uma heurística que,
conhecendo o estado final, é definida pela distância euclidiana entre um determinado estado e o 
estado final.
"""
class HeurDist(Heuristica):

    def __init__(self, estado_final):
        self.__estado_final = estado_final

    def h(self, estado):
        return dist(estado.posicao, self.__estado_final.posicao)