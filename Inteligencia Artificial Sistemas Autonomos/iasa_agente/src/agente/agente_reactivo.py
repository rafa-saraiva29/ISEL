from sae import Agente, Simulador
from agente.controlo_react.controlo_react import ControloReact
from agente.controlo_react.reaccoes.recolher import Recolher

"""
Classe AgenteReactivo que representa um agente reactivo cujo
objetivo é explorar um ambiente e recolher alvos. O comportamento
deste agente é o Recolher() e o seu controlo é o ControloReact()
"""

class AgenteReactivo(Agente):

    def __init__(self):
        comportamento = Recolher()
        controlo = ControloReact(comportamento)
        super().__init__(controlo)

# Executar simulação
if __name__ == '__main__':
    Simulador(1, AgenteReactivo()).executar()