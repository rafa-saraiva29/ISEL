from ..resposta.resposta_mover import RespostaMover
from sae import Direccao
from random import choice
"""
A classe RespostaEvitar é uma especialização da classe RespostaMover, pois a sua 
ação também é um movimento numa determinada direção. No entanto, o que a diferencia 
é que esta classe dá override ao método activar da classe Resposta, pois esta resposta 
só é ativada caso haja contacto com um obstáculo. Se houver, procura-se uma direção livre, 
com o auxílio do método direccao_livre, altera-se a direção da ação para essa direção livre 
e só depois se ativa a resposta. O método direccao_livre cria uma lista de direções onde não 
existe contacto com um obstáculo e retorna uma delas aleatoriamente.
"""
class RespostaEvitar(RespostaMover):

    def __init__(self, dir_inicial=Direccao.ESTE):
        self.__direccoes = list(Direccao)
        super().__init__(dir_inicial)
    
    def activar(self, percepcao, intensidade):
        #if percepcao.contacto_obst(percepcao.direccao): , induzido em erro pela resposta do professor a uma questão
        if percepcao.contacto_obst(self._accao.direccao):    
            #direccao_livre = self.__direccao_livre(percepcao) , versao anterior correta mas alterada para fazer
            # uso do operador :=, que afeta e testa na mesma linha
            if direccao_livre := self.__direccao_livre(percepcao):
                self._accao.direccao = direccao_livre
            else:
                return None
        
        return super().activar(percepcao, intensidade)
    
    def __direccao_livre(self, percepcao):
        """
        Versao anterior:
        for direccao in self.__direccoes:
            if not percepcao.contacto_obst(direccao):
                return direccao

        versao menos eficiente e que nao respeitava a arquitetura pois esta refere que 
        a direçao livre deve ser escolhida de forma aleatoria
        """
        direccoes_livres = [direccao for direccao in self.__direccoes 
                            if not percepcao.contacto_obst(direccao)]
        if direccoes_livres:
            return choice(direccoes_livres)
        
