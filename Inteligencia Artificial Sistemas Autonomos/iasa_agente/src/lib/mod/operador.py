from abc import ABC, abstractmethod

'''
Classe abstrata Operador que representa um operador no contexto de um problema
com raciocinio automatico. Um operador representa uma açao que produz
uma mudança de estado, isto é, operam sobre as representações internas 
de estado, produzindo transições de estado que correspondem à geração 
de novos estados.
'''
class Operador(ABC):

    '''
    Metodo abstrato que será utilizado para aplicar um operador
    a um estado, para transformar este num novo estado
    '''
    @abstractmethod
    def aplicar(self, estado):
        """"""

    '''
    Metodo abstrato que será responsavel por obter o custo associado
    a transicao de estado, pode ser em tempo, dinheiro, energia, etc, 
    dependendo da situaçao
    '''
    @abstractmethod
    def custo(self, estado, estado_suc):
        """"""