from dataclasses import dataclass
from mod.estado import Estado
from mod.operador import Operador

"""
Classe de dados cujo objetivo é guardar informação relativamente
a qual operador aplicar num determinado estado para chegar à solução
"""
@dataclass
class PassoSolucao:
    estado: Estado
    operador: Operador