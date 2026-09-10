from .mecanismo_procura import MecanismoProcura

"""
Classe ProcuraGrafo que representa uma procura em grafo. Esta classe
é uma especialização da classe MecanismoProcura. Este mecanismo tem a 
particularidade de manter em memória os nós que já foram explorados de
modo a evitar que estes sejam explorados novamente, reduzindo o desperdício
de recursos como o tempo e a memória. Para eliminação de nós correspondentes 
a estados repetidos, é necessário verificar se um novo nó sucessor corresponde 
a um estado que já foi anteriormente explorado, se isso acontecer, apenas o nó 
que corresponde ao percurso com menor custo deve ser mantido, o outro nó 
correspondente ao mesmo estado, mas num percurso com maior custo deve ser eliminado.
"""
class ProcuraGrafo(MecanismoProcura):

    def _iniciar_memoria(self):
        super()._iniciar_memoria()
        self._explorados = {}
    
    """
    Método responsável por memorizar um nó na fronteira de exploração e no dicionário de explorados.
    Um nó só é memorizado caso seja para manter, ou seja, ainda não tenha sido explorado.
    """
    def _memorizar(self, no):
        estado = no.estado
        if self._manter(no):
            self._fronteira.inserir(no)
            self._explorados[estado] = no
            
    """
    Método utilizado para informar o se o nó é para manter ou não. Se existir no dicionário
    de explorados não será para manter (retorna False), se não existir será para manter (retorna True)
    """
    def _manter(self, no):
        return no.estado not in self._explorados