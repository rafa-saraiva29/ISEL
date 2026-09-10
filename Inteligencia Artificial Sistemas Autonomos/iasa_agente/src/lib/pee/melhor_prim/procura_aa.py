from .procura_informada import ProcuraInformada
from .aval.avaliador_aa import AvaliadorAA

"""
Classe ProcuraAA é uma especialização da classe ProcuraInformada. Esta procura
é uma procura informada que utiliza uma função de avaliação que depende da soma
entre o custo do nó atual e a heurística. A heurística utilizada nesta procura tem
que ser necessariamente admissível. Dos estudados, este é o método com eficiência
ótima, na medida em que nenhum outro expandirá menos nós, mantendo as características 
de ser completo e ótimo, ou seja, encontra uma solução ótima no menor número de nós
expandidos possíveis. 
"""
class ProcuraAA(ProcuraInformada):

    def __init__(self):
        super().__init__(AvaliadorAA())