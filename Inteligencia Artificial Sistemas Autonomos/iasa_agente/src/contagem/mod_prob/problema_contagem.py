from mod.problema import Problema
from .operador_incremento import OperadorIncremento
from .estado_contagem import EstadoContagem

"""
Classe ProblemaContagem é uma especialização da classe Problema. Este problema
é definido por um valor inicial, um valor final e uma lista de incrementos.
O valor inicial permite conhecer o estado inicial do problema, o valor final 
permite saber quando é que se atingiu o objetivo e a lista de incrementos permite
definir os operadores.
"""
class ProblemaContagem(Problema):

    def __init__(self, valor_inicial, valor_final, incrementos):
        estado_inicial = EstadoContagem(valor_inicial)
        self.__valor_final = valor_final
        operadores = [OperadorIncremento(inc) for inc in incrementos]

        super().__init__(estado_inicial, operadores)

    """
    O objetivo é atingido se o valor do estado atual for maior ou igual ao valor 
    final recebido no construtor.
    """
    def objectivo(self, estado):
        return estado.valor >= self.__valor_final