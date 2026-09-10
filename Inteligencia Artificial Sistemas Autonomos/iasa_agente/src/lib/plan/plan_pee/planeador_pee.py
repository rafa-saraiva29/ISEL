from .mod_prob.heur_dist import HeurDist
from ..planeador import Planeador
from pee.melhor_prim.procura_aa import ProcuraAA
from .mod_prob.problema_plan import ProblemaPlan
from .plano_pee import PlanoPEE

"""
Classe PlaneadorPEE é uma realização da interface Planeador. Esta classe é um planeador que será 
utilizado num problema que será resolvido com procura em espaço de estados. Neste caso, é necessário
que o planeador defina o modelo do problema de planeamento, o mecanismo de procura e a heurística a
utilizar, caso seja necessária.
"""
class PlaneadorPEE(Planeador):

    def __init__(self):
        self.__mec_pee = ProcuraAA()
    
    """
    Método que realiza o planeamento. Caso haja objetivos, define o estado final como um dos objetivos,
    neste caso, o primeiro da lista, define o problema, problema de planeamento, define a heurística,
    pois o mecanismo de procura é a Procura A* e procura uma solução. Se existir, retorna o plano de 
    ação referente à solução.
    """
    def planear(self, modelo_plan, objectivos):
        if objectivos:
            estado_final = objectivos[0]
            problema = ProblemaPlan(modelo_plan, estado_final)
            heuristica = HeurDist(estado_final)

            solucao = self.__mec_pee.procurar(problema, heuristica)
            if solucao:
                return PlanoPEE(solucao)