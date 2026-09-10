from .procura_informada import ProcuraInformada
from .aval.avaliador_sof import AvaliadorSof

"""
Classe ProcuraSofrega é uma especialização da classe ProcuraInformada. Esta procura
é uma procura informada que utiliza uma função de avaliação apenas baseada numa 
heurística. Tem como objetivo a minimização da estimativa de custo para atingir o objetivo 
não tendo em conta o custo do percurso já explorado. Esta procura produz soluções sub-ótimas,
isto é, não existe garantia que a solução encontrada seja a melhor, no entanto, revela menor
complexidade computacional.
"""
class ProcuraSofrega(ProcuraInformada):

    def __init__(self):
        super().__init__(AvaliadorSof())