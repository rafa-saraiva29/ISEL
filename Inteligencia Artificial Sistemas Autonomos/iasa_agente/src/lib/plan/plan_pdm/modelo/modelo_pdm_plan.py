from ...modelo.modelo_plan import ModeloPlan
from pdm.modelo.modelo_pdm import ModeloPDM

"""
Classe ModeloPDMPlan que é uma realização, tanto da interface ModeloPlan, como da interface ModeloPDM.
Esta classe representa o modelo do mundo num sistema em que são utilizados os PDM.
"""
class ModeloPDMPlan(ModeloPlan, ModeloPDM):

    """
    Na nossa implementação, o espaço de estados é determinista, isto é, para cada estado, se tiver
    uma ação associada, a transição para o novo estado tem probabilidade 1, é certa.
    Adicionalmente, no atributo self.__transicoes, são pré-calculadas todas as transições possíveis,
    de maneira a tornar a implementação mais eficiente, evitando a necessidade de calcular transições
    sempre que se quer descobrir estados sucessores.
    """
    def __init__(self, modelo_plan, objectivos, rmax = 1000.0):
        self.__modelo_plan = modelo_plan
        self.__objectivos = objectivos
        self.__rmax = rmax

        self.__transicoes = {(s, a): a.aplicar(s)
                            for s in self.obter_estados()
                            for a in self.obter_operadores()}
    
    """
    Os métodos obter_estado, obter_estados e obter_operadores satisfazem a realização da interface
    ModeloPlan mas não necessitam de implementação adicional, por isso delegam a sua função para
    a instância __modelo_plan.
    """
    def obter_estado(self):
        return self.__modelo_plan.obter_estado()
    
    def obter_estados(self):
        return self.__modelo_plan.obter_estados()
    
    def obter_operadores(self):
        return self.__modelo_plan.obter_operadores()
    
    """
    Método que devolve todo o espaço de estados
    """
    def S(self):
        return self.__modelo_plan.obter_estados()
    
    """
    Método que devolve todos os operadores possíveis, exceto para o estados que são estados terminais
    """
    def A(self, s):
        return self.__modelo_plan.obter_operadores() if s not in self.__objectivos else []
    
    """
    Método que devolve a probabilidade de uma transição, que é 1 se existir, 0 se não existir
    """
    def T(self, s, a, sn):
        #return 1 if self.suc(s, a) else 0
        return 1 if self.__transicoes.get((s, a)) == sn else 0
    
    """
    Método que retorna a recompensa máxima se atingir um objetivo e mínima se colidir com obstáculo.
    Como, na realidade, os estados que são processados são apenas os estados válidos, devolve a 
    recompensa máxima se for um objetivo, se não for, retorna o custo da aplicação do operador.
    """
    def R(self, s, a, sn):
        return self.__rmax if sn in self.__objectivos else -a.custo(s, sn)
    
    """
    Método que devolve uma lista dos estados sucessores, caso eles existam.
    """
    def suc(self, s, a):
        sn = self.__transicoes.get((s, a))
        return [sn] if sn else []