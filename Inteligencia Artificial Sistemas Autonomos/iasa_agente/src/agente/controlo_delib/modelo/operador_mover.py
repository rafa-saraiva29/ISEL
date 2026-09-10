from mod.operador import Operador
from sae import Accao
from .estado_agente import EstadoAgente
import math

"""
Classe OperadorMover é uma realização da interface Operador. Este operador é
utilizado no problema de planeamento. Este representa a transformação do estado
atual do agente para outro estado, com outra posição, na direção que é recebida
no construtor.
"""
class OperadorMover(Operador):

    def __init__(self, modelo_mundo, direccao):
        self.__modelo_mundo = modelo_mundo
        self.__accao = Accao(direccao)

    @property
    def ang(self):
        return self.__accao.direccao.value
    
    @property
    def accao(self):
        return self.__accao

    """
    Neste método é realizada a transformação de estado aquando da aplicação do
    operador. Com o auxílio do método translaccao(), é calculada a nova posição
    e é retornado um novo estado, com essa posição, se esse novo estado estiver
    na lista de estados admissíveis do modelo do mundo.
    """
    def aplicar(self, estado):
        nova_posicao = self.__translaccao(estado.posicao, self.accao.passo, self.ang)
        novo_estado = EstadoAgente(nova_posicao)
        if novo_estado in self.__modelo_mundo.obter_estados():
            return novo_estado
    
    """
    Com o objetivo de maximizar a coesão e a modularidade da implementação, foi criado o
    método privado translaccao(), para auxiliar ao método aplicar, que calcula a nova posição
    do agente partindo da posição atual, com a distância e ângulo recebidos como parâmetros
    do método e de acordo com a arquitetura disponibilizada.
    """
    def __translaccao(self, posicao, distancia, angulo):
        x, y = posicao
        dx = round(distancia * math.cos(angulo))
        dy = -round(distancia * math.sin(angulo))
        nova_posicao = x + dx, y + dy
        return nova_posicao

    """
    O custo associado a este operador é definido pela distância euclidiana entre a posição de 
    um estado e a posição do seu estado sucessor, calculada com recurso ao método dist() do módulo
    math do Python
    """
    def custo(self, estado, estado_suc):
        return math.dist(estado.posicao, estado_suc.posicao)