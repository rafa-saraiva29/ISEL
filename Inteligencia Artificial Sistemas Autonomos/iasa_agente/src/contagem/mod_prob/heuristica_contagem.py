from pee.melhor_prim.heuristica import Heuristica

"""
Classe HeuristicaContagem é uma realização da interface Heuristica. Esta representa
a heurística que será utilizada no contexto do problema de contagem, caso se utilizem
métodos de procura informada. Neste caso, a heurística é definida pela distância entre
o valor do estado que está em avaliação e o valor final.
"""
class HeuristicaContagem(Heuristica):

    def __init__(self, valor_final):
        self.__valor_final = valor_final

    def h(self, estado):
        return abs(estado.valor - self.__valor_final)