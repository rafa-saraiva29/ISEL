from ..mec_proc.fronteira import Fronteira

'''
Classe FronteiraLIFO (LIFO = Last In First Out) é uma especializaçao 
da classe Fronteira, responsavel por definir uma fronteira do tipo LIFO, 
ou seja, em que os nós sao inseridos no inicio da lista para que os primeiros 
nós a sair sejam os mais recentes. Este é o tipo de fronteira utilizada 
na procura em profundidade pois, nesta procura, os primeiros nós a ser expandidos
são os mais recentes, os ultimos a ser gerados, aumentando a profundidade do ramo
corrente de procura
'''
class FronteiraLIFO(Fronteira):

    def inserir(self, no):
        self._nos.insert(0, no)