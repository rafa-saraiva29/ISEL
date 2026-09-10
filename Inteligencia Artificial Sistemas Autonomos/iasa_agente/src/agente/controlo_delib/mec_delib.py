from sae import Elemento

"""
Classe MecDelib representa uma das principais componentes do raciocínio prático, a deliberação ou
raciocínio sobre fins, que tem por objetivo decidir o que fazer, mais concretamente, obter um
conjunto de objetivos a concretizar.
"""
class MecDelib:

    def __init__(self, modelo_mundo):
        self.__modelo_mundo = modelo_mundo

    """
    Método responsável por realizar a deliberação. Partindo dos estados válido obtidos através do
    modelo do mundo, gera uma lista de objetivos que correspondem a estados cujo elemento é um
    alvo. Adicionalmente, esta lista é ordenada por distância, isto é, os objetivos que estão
    no início da lista são os que estão mais próximos do agente, utilizando a distância euclidiana.
    """
    def deliberar(self):
        estados = self.__modelo_mundo.obter_estados()
        objectivos = [estado for estado in estados if self.__modelo_mundo.obter_elemento(estado) == Elemento.ALVO]
        if objectivos:
            objectivos.sort(key=self.__modelo_mundo.distancia)
            return objectivos 