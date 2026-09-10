from .procura_melhor_prim import ProcuraMelhorPrim

"""
Classe ProcuraInformada é uma especialização da classe ProcuraMelhorPrim.
Esta procura é um tipo de procura melhor primeiro que utiliza heurísticas,
ou seja, que tira partido do conhecimento sobre o domínio do problema para 
ordenar a fronteira de exploração. Foram estudados dois métodos de procura 
informada: a procura sôfrega e a procura A*.
"""
class ProcuraInformada(ProcuraMelhorPrim):

    def procurar(self, problema, heuristica):
        self._avaliador.definir_heuristica(heuristica)
        return super().procurar(problema)