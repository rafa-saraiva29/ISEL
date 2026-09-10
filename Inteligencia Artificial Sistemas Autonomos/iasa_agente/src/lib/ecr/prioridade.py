from .comport_comp import ComportComp

"""
Classe Prioridade que representa um comportamento composto (especializaçao da classe comportamento composto)
cujo mecanismo de seleçao de açao designado por prioridade
Este mecanismo consiste em selecionar uma açao dentro de um conjunto de açoes, de acordo
com uma prioridade que lhes está associada e que pode variar ao longo do tempo de execuçao
É uma forma de prioridade dinamica
"""

class Prioridade(ComportComp):

    '''
    metodo responsavel por selecionar a açao, com base numa lista de açoes
    utiliza a funçao max, que retorna o valor maximo num conjunto de valores, mas 
    neste caso, com auxilio da funçao lambda, vai retornar a açao com maior prioridade
    funçao lambda: funçao anonima inline, usada em situaçoes onde é necessario uma 
    pequena funçao para auxiliar uma funçao maior, útil para implementar codigo conciso
    evitando a definiçao de uma funçao inteira
    requer a utilizaçao da keyword 'lambda' e recebe um argumento (neste caso, uma açao) e
    uma expressao para ser avaliada (neste caso, a prioridade da açao)
    '''
    def seleccionar_accao(self, accoes):
        if accoes:
            accao_sel = max(accoes, key=lambda accao: accao.prioridade)
            return accao_sel