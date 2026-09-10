from plan.modelo.modelo_plan import ModeloPlan
from .estado_agente import EstadoAgente
from .operador_mover import OperadorMover
from sae import Elemento, Direccao
from math import dist

"""
Classe ModeloMundo é uma realização da interface ModeloPlan. O modelo do mundo
é a representação interna do ambiente (memória) que serve de suporte para as componentes 
do raciocínio prático (deliberação e planeamento). Este serve de repositório de informação,
permite guardar o estado atual do agente, os estados válidos que o agente pode tomar, os 
operadores que o agente tem capacidade de realizar, os elementos do ambiente captados por
uma perceção, a distância para um outro estado e se o modelo já foi alterado ou não. Para além
de guardar informação, permite que esta seja atualizada a partir de perceções obtidas do ambiente
ou por efeito dos mecanismos de raciocínio prático.
"""
class ModeloMundo(ModeloPlan):

    def __init__(self):
        self.__estado = None
        self.__estados = []
        self.__elementos = {}
        self.__recolha = False
        self.__operadores = [OperadorMover(self, direccao) for direccao in Direccao]

    @property
    def alterado(self):
        return self.__recolha
    
    @property
    def elementos(self):
        return self.__elementos
    
    def obter_estado(self):
        return self.__estado
    
    def obter_estados(self):
        return self.__estados
    
    def obter_operadores(self):
        return self.__operadores
    
    def obter_elemento(self, estado):
        return self.__elementos.get(estado.posicao)
    
    def distancia(self, estado):
        return dist(self.__estado.posicao, estado.posicao)
    
    def actualizar(self, percepcao):
        self.__estado = EstadoAgente(percepcao.posicao)
        self.__estados = [EstadoAgente(posicao) for posicao in percepcao.posicoes]
        self.__elementos = percepcao.elementos
        self.__recolha = percepcao.recolha
    
    def mostrar(self, vista):
        for posicao, elemento in self.__elementos.items():
            if elemento in [Elemento.ALVO, Elemento.OBSTACULO]:
                vista.mostrar_elemento(posicao, elemento)
            vista.marcar_posicao(self.__estado.posicao)
        