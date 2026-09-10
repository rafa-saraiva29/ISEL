from sae import Controlo
from .modelo.modelo_mundo import ModeloMundo
from .mec_delib import MecDelib

"""
Classe ControloDelib é uma especialização da classe Controlo para o contexto de uma arquitetura
deliberativa. Este permite organizar de forma modular, o modelo do mundo e os mecanismos de 
raciocínio prático envolvidos no problema. O controlo deliberativo realiza um processamento interno
que, perante perceções obtidas do ambiente, gera ações a realizar. Este processamento corresponde a
um ciclo de tomada de decisão e ação definido por:
1. Observar o mundo, corresponde a obter perceções vindas do ambiente
2. Atualizar o modelo do mundo, com base nas perceções atualiza o modelo caso tenham havido alterações
3. Se Reconsiderar:
    4. Deliberar, corresponde a gerar objetivos, que no caso da aplicação desenvolvida, são estados 
    em que o elemento correspondente é um alvo
    5. Planear, corresponde a gerar o plano de ação para atingir um objetivo
6. Executar o plano de ação
Um dos problemas que podem ocorrer durante o raciocínio prático é que o ambiente pode alterar-se a
meio do processo, fazendo com que o resultado do raciocínio deixe de ser consistente com a situção
do ambiente. É por esta razão que é crucial o passo 3. Reconsideração, para que tanto os objetivos, 
como os planos de ação, possam ser ajustados conformemente.
"""
class ControloDelib(Controlo):

    def __init__(self, planeador):
        self.__planeador = planeador
        self.__objectivos = None
        self.__modelo_mundo = ModeloMundo()
        self.__mec_delib = MecDelib(self.__modelo_mundo)
        self.__plano = None

    """
    Ciclo de tomada de decisão e ação
    """
    def processar(self, percepcao):
        self.__assimilar(percepcao)
        if self.__reconsiderar():
            self.__deliberar()
            self.__planear()
        accao = self.__executar()
        self.__mostrar()
        return accao

    """
    Passo 2. Atualizar modelo do mundo
    """
    def __assimilar(self, percepcao):
        self.__modelo_mundo.actualizar(percepcao)
    
    """
    Passo 3. Reconsiderar
    """
    def __reconsiderar(self):
        return not self.__plano or self.__modelo_mundo.alterado
    
    """
    Passo 4. Deliberar
    """
    def __deliberar(self):
        self.__objectivos = self.__mec_delib.deliberar()
    
    """
    Passo 5. Planear
    """
    def __planear(self):
        self.__plano = self.__planeador.planear(self.__modelo_mundo, self.__objectivos)
    
    """
    Passo 6. Executar o plano de ação
    """
    def __executar(self):
        if self.__plano:
            operador = self.__plano.obter_accao(self.__modelo_mundo.obter_estado())
            if operador:
                return operador.accao
            else:
                self.__plano = None
    
    def __mostrar(self):
        self.vista.limpar()
        self.__modelo_mundo.mostrar(self.vista)
        if self.__plano:
            self.__plano.mostrar(self.vista)
        if self.__objectivos:
            for objectivo in self.__objectivos:
                self.vista.marcar_posicao(objectivo.posicao)