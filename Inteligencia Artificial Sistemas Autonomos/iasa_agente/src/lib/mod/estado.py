from abc import ABC, abstractmethod

'''
Classe Estado que representa um estado no contexto de um problema
com raciocinio automatico. Um estado representa uma situaçao ou
configuração de um determinado problema e este possui uma identificaçao
única para ser possivel a distinçao entre diferentes estados
'''
class Estado(ABC):

    '''
    Metodo abstrato que ira permitir definir a identidade do estado
    baseado no seu conteúdo
    '''
    @abstractmethod
    def id_valor(self):
        """"""

    '''
    Metodo responsavel por alterar a representação de uma instancia
    da classe Estado para a sua identidade utilizando o metodo id_valor, 
    pois por omissao a representação de uma instancia de uma classe é o 
    seu endereço em memória
    '''
    def __hash__(self):
        return self.id_valor()
    
    '''
    Metodo que permite fazer a comparaçao entre dois estados, usando a
    identidade de cada um, fazendo uso do metodo __hash__
    '''
    def __eq__(self, other):
        if isinstance(other, Estado):
            return self.__hash__() == other.__hash__()