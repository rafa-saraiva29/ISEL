from .passo_solucao import PassoSolucao

"""
Classe Solucao que representa a solução para o problema, ou seja,
os passos necessarios para chegar ao objetivo, partindo do nó inicial.
Cada passo é guardado como uma instancia de PassoSolucao que sao colocados
numa lista de passos
"""
class Solucao:

    def __init__(self, no_final):
        self.__no_final = no_final
        self.__passos = []
        no = no_final
        while no.antecessor:
            passo = PassoSolucao(no.antecessor.estado, no.operador)
            self.__passos.insert(0, passo)
            no = no.antecessor

    @property
    def dimensao(self):
        return self.__no_final.profundidade
    
    @property
    def custo(self):
        return self.__no_final.custo
    
    def __iter__(self):
        return iter(self.__passos)
    
    def __getitem__(self, index):
        return self.__passos[index]