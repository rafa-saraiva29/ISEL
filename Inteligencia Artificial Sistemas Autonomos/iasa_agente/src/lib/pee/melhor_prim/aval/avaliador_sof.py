from .avaliador_heur import AvaliadorHeur

"""
Classe AvaliadorSof é uma especialização da classe AvaliadorHeur. Este avaliador
representa a função de avaliação que será utilizada na procura sôfrega, que depende
apenas de uma heurística.
"""
class AvaliadorSof(AvaliadorHeur):

    def prioridade(self, no):
        return self._heuristica.h(no.estado)