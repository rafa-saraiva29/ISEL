from abc import ABC, abstractmethod

'''
Classe Fronteira é uma classe abstrata que representa uma fronteira de exploração 
(estrutura de dados) que contem nós que já foram gerados mas que ainda não foram
expandidos (explorados)
'''
class Fronteira(ABC):

    '''
    Construtor da classe responsavel por inicializar a fronteira
    atraves do metodo iniciar
    '''
    def __init__(self):
        self.iniciar()

    '''
    Propriedade da classe que indica se a lista de nós está vazia
    ou nao
    '''
    @property
    def vazia(self):
        return len(self._nos) == 0
    
    '''
    Metodo responsavel por criar um atributo que representa uma lista
    vazia de nós
    '''
    def iniciar(self):
        self._nos = []
    
    '''
    Metodo inserir, responsavel por inserir um nó na fronteira, dependendo
    do tipo de fronteira
    '''
    @abstractmethod
    def inserir(no):
        """"""

    '''
    Metodo remover que permite remover o primeiro elemento da lista de nós
    '''
    def remover(self):
        return self._nos.pop(0)