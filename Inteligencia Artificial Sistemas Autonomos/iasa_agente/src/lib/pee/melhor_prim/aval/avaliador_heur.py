from .avaliador import Avaliador

"""
Classe AvaliadorHeur é uma realização da interface Avaliador. Este avaliador
representa um avaliador, ou função de avaliação, que é baseada numa heurística.
Esta heurística é definida pelo método definir_heuristica(), que recebe uma 
heurística como parâmetro.
"""
class AvaliadorHeur(Avaliador):

    def definir_heuristica(self, heuristica):
        self._heuristica = heuristica