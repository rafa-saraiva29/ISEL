from abc import ABC, abstractmethod

"""
Interface ModeloPlan que representa o contrato funcional que será utilizado na implementação de 
modelos de representação interna de problemas, tanto para a fase de planeamento, como de deliberação.
Um modelo, numa arquitetura deliberativa, representa a memória do sistema. Esta permite ter em 
consideração a dimensão temporal futuro, para além do passado e presente, como acontece nas arquiteturas
reativas. Esta torna possível ao agente comportar-se da forma mais adequada para atingir os seus
objetivos, pois consegue antecipar estados futuros através da simulação interna de situações e ações 
futuras.
"""
class ModeloPlan(ABC):

    @abstractmethod
    def obter_estado(self):
        """"""

    @abstractmethod
    def obter_estados(self):
        """"""

    @abstractmethod
    def obter_operadores(self):
        """"""
        