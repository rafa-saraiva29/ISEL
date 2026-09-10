from mod.estado import Estado

"""
Classe EstadoContagem é uma realizaçao da interface Estado. Esta classe
representa um estado no contexto do problema da contagem. Neste caso, 
esta classe tem uma propriedade que guarda o valor numérico que representa
o estado no contexto do problema. O id deste estado é definido pelo hash code
da sua propriedade valor.
"""
class EstadoContagem(Estado):

    def __init__(self, valor):
        self.__valor = valor

    @property
    def valor(self):
        return self.__valor

    def id_valor(self):
        return hash(self.__valor)
    
    """
    Método utilizado para redifinir a forma como é realizada a visualização de um estado
    na consola
    """
    def __repr__(self):
        return f'EstadoContagem({self.__valor})'