"""
Classe No que representa um nó de uma arvore de procura. Um nó representa
uma etapa de procura que mantém informaçao relativa a cada transiçao de 
estado explorada. A informação mantida é o estado do nó atual, o nó antecessor,
o operador que foi aplicado ao nó antecessor para chegar ao estado do nó atual,
 o custo do nó atual, que representa o custo de todas as operações desde o 
nó inicial até ao atual e a profundidade do nó. Como um nó mantem toda esta informação, 
nomeadamente o seu antecessor e operador, é criada implicitamente a arvore de procura
"""
class No:
    """
    Construtor da classe, que permite o polimorfismo para o caso do nó inicial, 
    que não tem operador nem antecessor, e o seu custo é zero, então os valores
    sao definidos por omissão
    """
    def __init__(self, estado, operador=None, antecessor=None, custo=0):
        self.__estado = estado
        self.__operador = operador
        self.__antecessor = antecessor
        self.__custo = custo

        if self.__antecessor:
            self.__profundidade = self.__antecessor.profundidade + 1
        else:
            self.__profundidade = 0

    @property
    def estado(self):
        return self.__estado

    @property
    def operador(self):
        return self.__operador

    @property
    def antecessor(self):
        return self.__antecessor

    @property
    def profundidade(self):
        return self.__profundidade

    @property
    def custo(self):
        return self.__custo
    
    """
    Override ao método do Python para tornar a classe no comparavel, ou seja,
    instancias de No podem ser comparadas com outras instancias de No
    """
    def __lt__(self, outro_no):
        return self.custo < outro_no.custo
