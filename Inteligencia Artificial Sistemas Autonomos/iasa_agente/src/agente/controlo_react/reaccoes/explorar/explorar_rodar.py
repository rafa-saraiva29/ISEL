from ecr.comportamento import Comportamento
from sae import Direccao
from ..resposta.resposta_mover import RespostaMover

"""
Classe ExplorarRodar que representa um comportamento em que o agente se move
rotativamente, no sentido dos ponteiros do relogio, no ambiente. A classe é uma realizaçao da 
classe comportamento e por isso implementa o metodo activar()
O comportamento explorar rodar é um comportamento fixo, ou seja, é um comportamento que 
produz uma resposta sem necessitar de um estimulo.
"""
class ExplorarRodar(Comportamento):
    """
    Dicionario que associa a direcao atual, obtida pela percepcao, com a direcao nova cumprindo
    o requisito de rodar o agente no sentido dos ponteiros do relogio
    """
    __direccoes_rodar = {
            Direccao.NORTE: Direccao.ESTE,
            Direccao.ESTE: Direccao.SUL,
            Direccao.SUL: Direccao.OESTE,
            Direccao.OESTE: Direccao.NORTE
        }
    
    def activar(self, percepcao):
        nova_direccao = self.__direccoes_rodar[percepcao.direccao]
        resposta = RespostaMover(nova_direccao)
        return resposta.activar(percepcao)
    
    