from ecr.reaccao import Reaccao
from .estimulo_obst import EstimuloObst

"""
Classe EvitarDir representa uma reaçao que, neste caso vai associar um 
estimulo (os obstaculos) a uma resposta (RespostaEvitar). 
Esta classe é uma especializaçao da classe Reaccao
"""
class EvitarDir(Reaccao):

    def __init__(self, direccao, resposta):
        super().__init__(EstimuloObst(direccao), resposta)