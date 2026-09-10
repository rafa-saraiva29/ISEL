from ..mec_proc.fronteira import Fronteira

'''
Classe FronteiraFIFO (FIFO = First In First Out) é uma especialização 
da classe Fronteira, responsavel por definir uma fronteira do tipo FIFO, 
ou seja, em que os nós sao inseridos no fim da lista para que os primeiros 
nós a sair sejam os mais antigos. Esta fronteira é utilizada na procura em
largura pois, nesta procura, os primeiros nós a ser expandidos são os mais 
antigos, os primeiros a ser gerados, levando à exploração exaustiva de cada 
nível de procura antes da exploração de nós a um nível de maior profundidade
'''
class FronteiraFIFO(Fronteira):

    def inserir(self, no):
        self._nos.append(no)