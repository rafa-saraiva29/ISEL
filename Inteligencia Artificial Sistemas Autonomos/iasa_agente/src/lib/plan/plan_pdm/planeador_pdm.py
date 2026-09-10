from ..planeador import Planeador
from .modelo.modelo_pdm_plan import ModeloPDMPlan
from pdm.pdm import PDM
from .plano_pdm import PlanoPDM

"""
Classe PlaneadorPDM é uma realização da interface Planeador utilizada no contexto de PDM. O objetivo
desta classe é gerar um plano, partindo de um modelo de planeamento e de uma estratégia de ação
(política).
"""
class PlaneadorPDM(Planeador):

    def __init__(self, gama = 0.85, delta_max = 1.0):
        self.__gama = gama
        self.__delta_max = delta_max

    """
    Método responsável por realizar o planeamento. Caso haja objetivos, é necessário definir o modelo
    do mundo e resolver os processos de decisão de Markov, de modo a obter a utilidade e a política.
    """
    def planear(self, modelo_plan, objectivos):
        if objectivos:
            modelo_pdm = ModeloPDMPlan(modelo_plan, objectivos)
            pdm = PDM(modelo_pdm, self.__gama, self.__delta_max)
            U, pol = pdm.resolver()
            return PlanoPDM(U, pol)