from .mec_util import MecUtil

"""
Classe PDM que representa um processo de decisão sequencial, o processo de decisão de Markov.
Um processo de decisão sequencial é um processo em que a evolução entre estados ocorre por
efeito de ações cujo o seu resultado pode não ser totalmente previsível, existindo incerteza no
resultado da ação. As transições de estado associadas a este processo de decisão, ocorrem por 
efeito de ações com uma probabilidade associada, às quais pode estar associada uma recompensa,
ou seja, um ganhou ou perda associado a essa transição. No processo de decisão de Markov, a
previsão dos estados seguintes só depende do estado presente e a representação do mundo é a 
seguinte:
- S, conjunto de estados
- A(s), conjunto de ações possíveis no estado s pertencente a S
- T(s,a,s'), probabilidade de transição de s para s' através de a
- R(s,a,s'), recompensa esperada na transição de s para s' através de a
- gama, fator de desconto para recompensas diferidas no tempo
Um conceito importante é o conceito de Utilidade. É um efeito cumulativo da evolução do sistema, 
representa a recompensa associada a cada estado, um valor finito positivo ou negativo, que reflete 
a utilidade de cada estado na perspetiva de alcançar um objetivo.
Outro conceito importante é o conceito de Política. A Política Comportamental representa o comportamento
do agente, que define qual a ação que deve ser realizada em cada estado, a estratégia de ação. Uma
política pode ser determinista se para cada estado é conhecida a ação a realizar, ou pode ser não
determinista, se para cada estado existir um conjunto de ações com uma probabilidade associada.
"""
class PDM:

    def __init__(self, modelo, gama, delta_max):
        self.__modelo = modelo
        self.__mec_util = MecUtil(self.__modelo, gama, delta_max)

    """
    Método que determina a política. A política é um dicionário que associa um estado à sua ação com
    maior utilidade.
    """
    def politica(self, U):
        S, A = self.__modelo.S, self.__modelo.A
        pol = {}
        for s in S():
            if A(s):
                pol[s] = max(A(s), key=lambda a: self.__mec_util.util_accao(s, a, U))
        return pol

    def resolver(self):
        U = self.__mec_util.utilidade()
        pol = self.politica(U)
        return U, pol