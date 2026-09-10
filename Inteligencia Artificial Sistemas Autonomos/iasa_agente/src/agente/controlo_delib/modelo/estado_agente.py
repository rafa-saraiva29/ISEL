from mod.estado import Estado

"""
Classe EstadoAgente é uma realização da interface Estado. Esta classe
representa um estado do agente no contexto de um problema de planeamento
em que um agente se desloca num ambiente. No domínio do problema, o estado
do agente é representado pela sua posição no ambiente, um tuplo (x,y),
guardado na propriedade posicao. O id desta classe é definido pelo hash code
relativo à propriedade posicao.
"""
class EstadoAgente(Estado):

    def __init__(self, posicao):
        self.__posicao = posicao

    @property
    def posicao(self):
        return self.__posicao
    
    def id_valor(self):
        return hash(self.posicao)