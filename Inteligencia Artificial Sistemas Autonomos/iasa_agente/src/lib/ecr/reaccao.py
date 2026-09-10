from .comportamento import Comportamento

"""
Uma arquitectura de agentes reactivos define um ciclo percepção-reacção-acção, 
onde as reacções definem de forma modular as associações entre estímulos 
(derivados da percepção) e respostas (geradoras de acção). As açoes sao 
diretamente ativadas em funçao das perceçoes.
Esta classe representa uma reação. Uma reaçao é um módulo que associa
estimulos a respostas e vai encapsular esta associaçao
Esta classe é também um comportamento e por isso é uma realizaçao da interface
Comportamento
"""

class Reaccao(Comportamento):

    def __init__(self, estimulo, resposta):
        self.__estimulo = estimulo
        self.__resposta = resposta
    
    def activar(self, percepcao):
        intensidade = self.__estimulo.detectar(percepcao)
        if intensidade > 0:
            return self.__resposta.activar(percepcao, intensidade)