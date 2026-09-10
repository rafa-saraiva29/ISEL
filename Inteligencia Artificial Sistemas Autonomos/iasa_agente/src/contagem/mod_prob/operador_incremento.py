from mod.operador import Operador
from .estado_contagem import EstadoContagem

"""
Classe OperadorIncremento é uma especialização da interface Operador. Este
é o operador utilizado no problema da contagem. Este representa um incremento
sobre o estado em que é aplicado, isto é, uma soma ao valor desse estado. 
"""
class OperadorIncremento(Operador):

    def __init__(self, incremento):
        self.__incremento = incremento

    @property
    def incremento(self):
        return self.__incremento

    """
    Neste método é realizada a transformação de estado aquando da aplicação do
    operador. É retornado um novo estado cujo valor é igual á soma entre o valor
    do estado que é recebido e o incremento do operador utilizado.
    """
    def aplicar(self, estado):
        return EstadoContagem(estado.valor + self.__incremento)
    
    """
    O custo deste operador, neste caso, não depende do estado nem do estado sucessor.
    Ao invés disso, o custo é igual ao quadrado do valor do incremento utilizado.
    """
    def custo(self, estado, estado_suc):
        return self.__incremento ** 2
    
    """
    Método utilizado para redifinir a forma como é realizada a visualização de um operador
    na consola
    """
    def __repr__(self):
        return f'OperadorIncremento({self.__incremento})'

