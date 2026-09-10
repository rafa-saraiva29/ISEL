from .fronteira_fifo import FronteiraFIFO
from ..mec_proc.procura_grafo import ProcuraGrafo
"""
Classe ProcuraLargura que representa o mecanismo de procura Procura em Largura.
Esta classe é uma especialização da classe ProcuraGrafo. Neste mecanismo, a 
procura decorre explorando os nós mais antigos primeiro (primeiros a ser gerados), 
levando à exploração exaustiva de cada nível de procura antes da exploração de nós 
a um nível de maior profundidade e, por isso, a fronteira utilizada é uma FronteiraFIFO.
"""
class ProcuraLargura(ProcuraGrafo):

    def __init__(self):
        super().__init__(FronteiraFIFO())