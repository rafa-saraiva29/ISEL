from pee.prof.procura_profundidade import ProcuraProfundidade
from pee.larg.procura_largura import ProcuraLargura
from mod_prob.problema_contagem import ProblemaContagem
from pee.melhor_prim.procura_custo_unif import ProcuraCustoUnif
from pee.melhor_prim.procura_aa import ProcuraAA
from mod_prob.heuristica_contagem import HeuristicaContagem
from pee.melhor_prim.procura_sofrega import ProcuraSofrega

"""
Módulo contagem que serve como módulo de teste aos vários métodos de procura
estudados e implementados. Este teste começa por definir o problema que é um 
problema de contagem, ou seja, o valor inicial é um valor inteiro e o objetivo
é atingir outro valor através de incrementos. O VALOR_INICIAL representa o estado
inicial do problema, o VALOR_FINAL representa o objetivo e os INCREMENTOS são
os operadores que podem ser aplicados.
"""

# Configuração do problema 
VALOR_INICIAL = 0
VALOR_FINAL = 9
INCREMENTOS = [1, 2, -1]

# Definir o problema
problema = ProblemaContagem(VALOR_INICIAL, VALOR_FINAL, INCREMENTOS)
heuristica = HeuristicaContagem(VALOR_FINAL)

# Iniciar mecanismo de procura
#mec_proc = ProcuraProfundidade()
#mec_proc = ProcuraLargura()
#mec_proc = ProcuraAA()
mec_proc = ProcuraSofrega()


# Resolver o problema
solucao = mec_proc.procurar(problema, heuristica)

# Mostrar a solução
for passo in solucao:
    print(passo.estado, passo.operador)