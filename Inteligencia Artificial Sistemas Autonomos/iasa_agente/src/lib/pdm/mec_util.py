"""
Classe MecUtil que representa um mecanismo baseado na utilidade.
"""
class MecUtil:

    def __init__(self, modelo, gama, delta_max):
        self.__modelo = modelo
        self.__delta_max = delta_max 
        self.__gama = gama

    """
    Método responsável por associar a cada estado a utilidade da sua ação com maior utilidade.
    Esta associação corresponde ao dicionário U, que é inicializado com utilidade zero para todos 
    os estados. Em seguida, num ciclo while com break, de modo a replicar um ciclo do-while, que não
    existe na linguagem Python, é criada uma shallow copy de U, para guardar os valores de utilidade
    anteriores e é iniciado o valor do delta a zero. Ainda dentro do ciclo while, dentro de um ciclo
    for que irá percorrer o espaço de estados, é calculada a utilidade desse estado, que corresponde
    à maior utilidade das suas ações, com recurso ao método util_accao, e é atualizado o delta, com
    o maior valor entre o delta anterior e o resultado do módulo da diferença entre a utilidade atual
    do estado e a utilidade anterior. Finalmente, é verificada a condição de saída do ciclo while, 
    que realiza um break caso o delta atualizado seja menor ou igual ao delta máximo definido no 
    construtor.
    """
    def utilidade(self):
        S, A = self.__modelo.S, self.__modelo.A
        U = {s: 0.0 for s in S()}

        while True:
            Uant = U.copy()
            delta = 0

            for s in S():
                U[s] = max([self.util_accao(s, a, Uant) for a in A(s)], default=0)
                delta = max(delta, abs(U[s] - Uant[s]))
            
            if delta <= self.__delta_max:
                break
        
        return U
    """
    Método que calcula a utilidade de uma ação. A utilidade é calculada através de um somatório em
    que, para cada estado sucessor, é feito o produto entre a probabilidade de transição e a soma
    entre a recompensa imediata e a utilidade descontada pelo gama.
    """
    def util_accao(self, s, a, U):
        T, R, suc = self.__modelo.T, self.__modelo.R, self.__modelo.suc
        somatorio = sum([T(s, a, sn) * (R(s, a, sn) + self.__gama * U[sn]) for sn in suc(s, a)])
        return somatorio