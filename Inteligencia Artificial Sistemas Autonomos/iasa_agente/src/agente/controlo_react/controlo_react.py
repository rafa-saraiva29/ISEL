from sae import Controlo

"""
Classe ControloReact que representa o processamento interno de um agente reativo.
Esta classe é uma especializaçao da classe Controlo e por isso implementa o metodo
processar. Este metodo é responsavel por associar uma percepcao a uma açao, atraves 
de um comportamento
"""

class ControloReact(Controlo):

    def __init__(self, comportamento):
        self.__comportamento = comportamento
        self.mostrar_per_dir = True

    def processar(self, percepcao):
        return self.__comportamento.activar(percepcao)