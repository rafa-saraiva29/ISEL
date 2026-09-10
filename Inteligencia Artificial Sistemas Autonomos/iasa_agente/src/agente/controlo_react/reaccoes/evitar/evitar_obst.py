from ecr.hierarquia import Hierarquia
from .resposta_evitar import RespostaEvitar
from .evitar_dir import EvitarDir
from sae import Direccao
'''
A classe EvitarObst representa um comportamento de um agente reativo, cujo objetivo 
é evitar obstáculos. Este comportamento é um comportamento composto cujo mecanismo 
de seleção de ação é a hierarquia. Por isso, esta classe é uma especialização da 
classe Hierarquia. Este comportamento terá 4 sub-comportamentos, cada um deles uma 
instância da classe EvitarDir, para cada uma das direções possíveis do movimento do agente. 
'''
class EvitarObst(Hierarquia):

    def __init__(self):
        self.__resposta = RespostaEvitar()
        super().__init__([EvitarDir(direccao, self.__resposta) for direccao in Direccao])