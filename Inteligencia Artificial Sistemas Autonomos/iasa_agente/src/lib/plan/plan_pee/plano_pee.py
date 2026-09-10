from ..plano import Plano

"""
Classe PlanoPEE é uma realização da interface Plano. Esta representa um plano num problema com procura
em espaço de estados. Partindo dos passos de uma solução, este permite obter o operador que deve ser
utilizado, para um determinado estado do problema. 
"""
class PlanoPEE(Plano):

    def __init__(self, solucao):
        self.__solucao = solucao
        self.__passos = [passo for passo in self.__solucao]

    def obter_accao(self, estado):
        if self.__passos:
            passo = self.__passos.pop(0)
            if passo.estado == estado:
                return passo.operador
                    
    def mostrar(self, vista):
        if self.__passos:
            for passo in self.__passos:
                vista.mostrar_vector(passo.estado.posicao, passo.operador.ang)