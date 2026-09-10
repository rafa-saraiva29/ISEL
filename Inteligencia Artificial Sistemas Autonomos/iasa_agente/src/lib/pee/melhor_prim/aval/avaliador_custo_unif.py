from .avaliador import Avaliador

"""
Classe AvaliadorCustoUnif é uma realização da interface Avaliador. Este avaliador
representa a função de avaliação que será utilizada na procura de custo uniforme,
que depende apenas do custo nó.
"""
class AvaliadorCustoUnif(Avaliador):

    def prioridade(self, no):
        return no.custo