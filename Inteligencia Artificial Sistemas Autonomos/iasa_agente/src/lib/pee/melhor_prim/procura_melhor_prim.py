from ..mec_proc.procura_grafo import ProcuraGrafo
from .fronteira_prioridade import FronteiraPrioridade

"""
Classe ProcuraMelhorPrim que representa o mecanismo de procura melhor-primeiro. Esta classe
é uma especialização da classe ProcuraGrafo. Este mecanismo de procura utiliza uma função de
avaliação que avalia cada nó gerado, tipicamente com base no custo da solução através desse nó.
Quanto menor o valor dessa função, mais promissor para expansão é esse nó. A fronteira de
exploração, neste mecanismo, ordena os nós por ordem crescente do valor obtido pela função
de avaliação.
"""
class ProcuraMelhorPrim(ProcuraGrafo):

    def __init__(self, avaliador):
        super().__init__(FronteiraPrioridade(avaliador))
        self._avaliador = avaliador

    """
    Override do método _manter da super classe pois, neste caso, um nó pode ser mantido caso
    ainda não esteja no dicionário de explorados mas também se o seu custo for inferior ao 
    custo de um nó com o mesmo estado.
    """
    def _manter(self, no):
        return super()._manter(no) or no < self._explorados[no.estado]