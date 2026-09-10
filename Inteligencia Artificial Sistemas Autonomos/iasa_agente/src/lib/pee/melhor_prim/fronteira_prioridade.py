from ..mec_proc.fronteira import Fronteira
from heapq import heappush, heappop

"""
Classe FronteiraPrioridade que representa a fronteira que é utilizada nas procuras
melhor-primeiro. Essas procuras usam uma função f(n) para a avaliação de cada nó gerado. 
A fronteira é, portanto, ordenada com base nessa função cujo valor é calculado no método 
prioridade() dos avaliadores.
"""
class FronteiraPrioridade(Fronteira):

    def __init__(self, avaliador):
        super().__init__()
        self.__avaliador = avaliador

    
    def inserir(self, no):
        prioridade = self.__avaliador.prioridade(no)
        heappush(self._nos, (prioridade, no))
    
    def remover(self):
        _, no = heappop(self._nos)
        return no