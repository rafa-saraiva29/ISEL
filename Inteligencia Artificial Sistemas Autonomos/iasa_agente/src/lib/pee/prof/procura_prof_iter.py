from .procura_prof_lim import ProcuraProfLim

"""
Classe ProcuraProfIter representa o mecanismo de procura em profundidade iterativa.
Esta classe é uma especialização da classe ProcuraProfLim. Este mecanismo consiste em
fazer várias procuras em profundidade limitada, iterativamente, ou seja, com o limite
de profundidade a aumentar consoante um certo incremento. Este mecanismo é ótimo e completo.
"""
class ProcuraProfIter(ProcuraProfLim):

    def procurar(self, problema, inc_prof=1, limite_prof=100):
        for profundidade in range(0, limite_prof + 1, inc_prof):
            self.prof_max = profundidade
            solucao = super().procurar(problema)
            if solucao:
                return solucao