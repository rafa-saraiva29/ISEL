from mod.problema import Problema

class ProblemaPlan(Problema):

    """
    Classe ProblemaPlan é uma especialização da classe Problema no contexto de um problema de 
    planeamento. Esta recebe como parâmetros o modelo e o estado final e invoca o construtor da 
    super classe, fornecendo o estado atual do modelo como estado inicial e os seus operadores. 
    """
    def __init__(self, modelo_plan, estado_final):
        super().__init__(modelo_plan.obter_estado(), modelo_plan.obter_operadores())
        self.__estado_final = estado_final
        
    """
    O objetivo é atingido quando o estado atual for igual ao estado final recebido
    """
    def objectivo(self, estado):
        return estado == self.__estado_final