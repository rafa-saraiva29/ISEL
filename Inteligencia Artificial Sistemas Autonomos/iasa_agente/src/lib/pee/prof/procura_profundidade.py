from ..mec_proc.mecanismo_procura import MecanismoProcura
from .fronteira_lifo import FronteiraLIFO

"""
Classe ProcuraProfundidade que representa o mecanismo de procura em profundidade. Esta
classe é uma especialização da classe MecanismoProcura. Neste mecanismo, a procura decorre 
explorando os nós mais recentes primeiro (últimos a ser gerados), aumentando por isso a 
profundidade do ramo corrente de procura e, por isso, a fronteira utilizada é uma FronteiraLIFO.
"""
class ProcuraProfundidade(MecanismoProcura):

    def __init__(self):
        super().__init__(FronteiraLIFO())
    
    def _memorizar(self, no):
        self._fronteira.inserir(no)
