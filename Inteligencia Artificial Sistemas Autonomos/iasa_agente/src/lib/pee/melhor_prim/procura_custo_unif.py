from .procura_melhor_prim import ProcuraMelhorPrim
from .aval.avaliador_custo_unif import AvaliadorCustoUnif

"""
Classe ProcuraCustoUnif representa o mecanismo de procura de custo uniforme. Esta classe
é uma especialização da classe ProcuraMelhorPrim e um caso particular desse mecanismo de
procura. Neste caso, a função de avaliação corresponde diretamente ao custo de cada nó,
como está definido no AvaliadorCustoUnif().
"""
class ProcuraCustoUnif(ProcuraMelhorPrim):

    def __init__(self):
        super().__init__(AvaliadorCustoUnif())