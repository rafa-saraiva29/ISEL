from sae import Agente, Simulador
from controlo_delib.controlo_delib import ControloDelib
from plan.plan_pdm.planeador_pdm import PlaneadorPDM

class AgenteDelibPDM(Agente):

    def __init__(self):
        planeador = PlaneadorPDM(gama=0.98)
        controlo = ControloDelib(planeador)
        super().__init__(controlo)

# Executar simulação
if __name__ == '__main__':
    Simulador(4, AgenteDelibPDM()).executar()
