from ..plano import Plano

"""
Classe PlanoPDM é uma realização da interface Plano utilizada no contexto dos PDM. Partindo da
política, que contem a estratégia de ação do agente, é possível obter o operador que se deve
utilizar num determinado estado.
"""
class PlanoPDM(Plano):

    def __init__(self, utilidade, politica):
        self.__utilidade = utilidade
        self.__politica = politica

    def obter_accao(self, estado):
        if self.__politica:
            return self.__politica.get(estado)
    
    def mostrar(self, vista):
        if self.__politica:
            for estado, valor in self.__utilidade.items():
                vista.mostrar_valor_posicao(estado.posicao, valor)

            for estado, accao in self.__politica.items():
                vista.mostrar_vector(estado.posicao, accao.ang)