from .procura_profundidade import ProcuraProfundidade

"""
Classe ProcuraProfLim que representa o mecanismo de procura em profundidade limitada.
Esta classe é uma especialização da classe ProcuraProfundidade. Este mecanismo é identico
ao mecanismo de procura em profundidade, com a particularidade que este tem um limite de
profundidade, ou seja, só expande nós enquanto esse limite não for atingido. Este mecanismo
não é ótimo nem completo, ou seja, não garante uma solução ótima nem garante que uma solução
seja encontrada.
"""
class ProcuraProfLim(ProcuraProfundidade):

    def __init__(self, prof_max):
        self.__prof_max = prof_max
        super().__init__()

    @property
    def prof_max(self):
        return self.__prof_max
    
    @prof_max.setter
    def prof_max(self, valor):
        self.__prof_max = valor
    
    """
    Método responsável pela expansão dos nós. Neste caso, é feito override ao método da super
    classe pois, neste mecanismo, um nó só é expandido caso a sua profundidade seja menor que
    a profundidade maxima definida no construtor
    """
    def _expandir(self, problema, no):
        return super()._expandir(problema, no) if no.profundidade < self.__prof_max else []