from ecr.hierarquia import Hierarquia
from .explorar.explorar import Explorar
from .aproximar.aproximar_alvo import AproximarAlvo
from .evitar.evitar_obst import EvitarObst

"""
Classe Recolher que representa um comportamento de um agente reativo, cujo objetivo é recolher
alvos. Este comportamento é um comportamento composto cujo o mecanismo de seleçao de açao é 
a hierarquia e por isso, esta classe é uma realização da classe Hierarquia. Existem tres sub-objetivos: 
aproximar alvo, evitar obstaculos e explorar. Por isso, vao existir tambem tres sub-comportamentos, neste caso: 
AproximarAlvo, EvitarObst e Explorar. O sub-comportamento com maior prioridade é o AproximarAlvo e o com menor é o Explorar.
"""
class Recolher(Hierarquia):
    __comportamentos = [AproximarAlvo(), EvitarObst(), Explorar()]

    """
    Construtor da classe que ativa o contrutor da super classe ComportComp. A super classe necessita de receber
    uma lista de comportamentos e, como esta classe é uma hierarquia, essa lista tem que estar ordenada de acordo
    com a prioridade de cada comportamento. Essa lista é definida no atributo da classe __comportamentos.
    """
    def __init__(self):
        super().__init__(self.__comportamentos)