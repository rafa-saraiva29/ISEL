from sae import Agente, Simulador
from agente.controlo_react.controlo_react import ControloReact
from agente.controlo_react.reaccoes.comport_teste import ComportTeste

"""
Classe de teste que representa um agente e por isso é uma especializaçao
da classe Agente. Esta classe tem que definir no seu construtor o seu 
comportamento e o seu controlo
"""
class AgenteTeste(Agente):

    def __init__(self):
        comportamento = ComportTeste()
        controlo = ControloReact(comportamento)
        super().__init__(controlo)

# Executar simulação
if __name__ == '__main__':
    Simulador(1, AgenteTeste()).executar()
