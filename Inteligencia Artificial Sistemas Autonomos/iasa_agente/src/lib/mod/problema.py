from abc import ABC, abstractmethod

'''
Classe abstrata Problema que representa uma situaçao que se pretende
resolver com recurso a racionio automatico. Um problema é definido por: 
- um estado inicial, a primeira configuração do problema;
- um conjunto de operadores, conjunto de açoes que permitem fazer 
transformaçoes de estado; 
- objetivo ou função objetivo, configuração do problema que se quer atingir
'''
class Problema(ABC):

    def __init__(self, estado_inicial, operadores):
        self.__estado_inicial = estado_inicial
        self.__operadores = operadores

    '''
    Definiçao do atributo estado_inicial do problema como propriedade,
    disponivel apenas para leitura
    '''
    @property
    def estado_inicial(self):
        return self.__estado_inicial
    
    '''
    Definiçao do atributo operadores do problema como propriedade,
    disponivel apenas para leitura
    '''
    @property
    def operadores(self):
        return self.__operadores
    
    '''
    Metodo que, recebendo um estado, irá verificar se este é um estado
    objetivo
    '''
    @abstractmethod
    def objectivo(self, estado):
        """"""