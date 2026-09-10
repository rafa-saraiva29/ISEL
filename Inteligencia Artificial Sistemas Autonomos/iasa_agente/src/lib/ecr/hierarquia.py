from .comport_comp import ComportComp

"""
Classe Hierarquia que representa um comportamento composto (especializaçao da classe comportamento composto)
cujo mecanismo de seleçao de açao é designado por hierarquia
Neste mecanismo, é definida uma hierarquia fixa entre os comportamentos (prioridade fixa)
criando uma relaçao entre eles de subsunçao (suprimir e substituir), ou seja, o comportamento
com mais prioridade (ou acima na hierarquia), suprime e subsititui um comportamento com menor prioridade
e é selecionado 
"""
class Hierarquia(ComportComp):

    """
    Metodo responsavel por selecionar a açao com base numa lista de açoes
    Neste caso, partindo do pressuposto que a lista de açoes é construida de modo a que a lista esteja
    ordenada de acordo com a prioridade das açoes, de forma decrescente, a açao selecionada é a primeira
    da lista, pois é a que tem maior prioridade
    """
    def seleccionar_accao(self, accoes):
        if accoes:
            accao_sel = accoes[0]
            return accao_sel