from abc import ABC, abstractmethod
from .no import No
from .solucao import Solucao

"""
Classe MecanismoProcura que representa uma implementaçao geral de um mecanismo
de procura num espaço de estados. Este é um mecanismo tipicamente utilizado em 
problemas de planeamento, nos quais se pretende encontrar uma sequência de situações 
e de ações que levem de uma situação inicial a uma situação final.
O processo de procura consiste em explorar sucessivamente cada nó, verificando se o 
estado atual corresponde ao objetivo, se nao corresponder, esse estado é expandido
gerando todos os estados sucessores por aplicação dos operadores disponíveis e para 
cada estado sucessor é repetido o processo. Caso um estado corresponda ao objetivo,
o processo termina e é retornado o percurso do estado inicial ao objetivo. Se o
não existirem sucessores, o processo termina sem solução
"""
class MecanismoProcura(ABC):

    def __init__(self, fronteira):
        self._fronteira = fronteira

    def _iniciar_memoria(self):
        self._fronteira.iniciar()
    
    """
    Método responsavel por memorizar um nó na fronteira, o metodo é abstrato para que
    seja definido por cada mecanismo de procura, consoante a forma indicada de memorização
    de nós na sua fronteira
    """
    @abstractmethod
    def _memorizar(self, no):
        """"""

    """
    Método procurar() que implementa o algoritmo base de procura em espaço de estados
    - É iniciada a fronteira e nela é memorizado o nó inicial
    - Enquanto a fronteira não estiver vazia, é removido o primeiro nó da fronteira para 
      este ser expandido
    - Se o estado do nó for objetivo é retornada a solução
    - Se não for, o nó é expandido e cada nó sucessor é memorizado na fronteira
    """
    def procurar(self, problema):
        self._iniciar_memoria()
        no = No(problema.estado_inicial)
        self._memorizar(no)

        while not self._fronteira.vazia:
            no = self._fronteira.remover()

            if problema.objectivo(no.estado):
                return Solucao(no)
            
            for no_sucessor in self._expandir(problema, no):
                self._memorizar(no_sucessor)
    
    def _expandir(self, problema, no):
        sucessores = []
        estado = no.estado

        for operador in problema.operadores:
            estado_suc = operador.aplicar(estado)

            if estado_suc is not None:
                custo = no.custo + operador.custo(estado, estado_suc)
                no_sucessor = No(estado_suc, operador, no, custo)
                sucessores.append(no_sucessor)
        
        return sucessores