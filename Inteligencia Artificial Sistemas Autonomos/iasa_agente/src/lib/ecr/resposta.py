"""
Classe Resposta que representa a geração de uma resposta
a um estímulo. Uma resposta define a açao a realizar e a sua
respetiva prioridade, em resposta a estimulo 
"""
class Resposta:
    
    def __init__(self, accao):
        self._accao = accao

    def activar(self, percepcao, intensidade = 0):
        if percepcao is not None:
            self._accao.prioridade = intensidade
            return self._accao