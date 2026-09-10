from ecr.prioridade import Prioridade
from .aproximar_dir import AproximarDir
from sae import Direccao

"""
Classe AproximarAlvo que representa um comportamento de um agente reativo, cujo objetivo
é aproximar-se de um alvo. Este comportamento é um comportamento composto cujo mecanismo de
seleçao de açao é a prioridade. Por isso, esta classe é uma especialização da classe Prioridade.
Este comportamento terá 4 sub-comportamentos, cada um deles uma instancia da classe AproximarDir,
para cada uma das direçoes possiveis do movimento do agente. Neste caso, a ordem da lista de 
comportamentos é indiferente pois o mecanismo de seleçao é a prioridade e esta vai ser definida
pela distancia do agente ao alvo
"""

class AproximarAlvo(Prioridade):
    __comportamentos = [AproximarDir(Direccao.NORTE), 
                        AproximarDir(Direccao.SUL), 
                        AproximarDir(Direccao.ESTE),
                        AproximarDir(Direccao.OESTE)]
    
    """
    Construtor da classe que ativa o contrutor da super classe ComportComp. A super classe necessita de receber
    uma lista de comportamentos e, como esta classe é uma prioridade, a ordem da lista de 
    comportamentos é indiferente pois o mecanismo de seleçao é a prioridade e esta vai ser definida
    pela distancia do agente ao alvo. Essa lista é definida no atributo da classe __comportamentos.
    """
    def __init__(self):
        super().__init__(self.__comportamentos)