from .avaliador_heur import AvaliadorHeur

"""
Classe AvaliadorAA é uma especialização da classe AvaliadorHeur. Este avaliador
representa a função de avaliação usada na procura A*. Esta depende, neste caso,
da soma entre o custo do nó e uma heurística
"""
class AvaliadorAA(AvaliadorHeur):

    def prioridade(self, no):
        return no.custo + self._heuristica.h(no.estado) 