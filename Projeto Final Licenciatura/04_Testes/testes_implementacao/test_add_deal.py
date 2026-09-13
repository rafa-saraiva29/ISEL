from src.model.deal.deal import Deal
from src.model.system_manager import SystemManager


def test_start_add_deal():
    system = SystemManager()

    print("====> A iniciar sistema...")
    system.start()
    print("Sistema iniciado com sucesso!\n")

    print("====> Adicionar novo Deal\n")
    novo_deal = Deal(1, "Expedicao Fotografica as Cores de Marraquexe", "Descobre a magia de Marrocos numa viagem de 7 dias feita para despertar o artista que há em ti. Passeia pelos souks vibrantes de Marraquexe, fotografa os contrastes de luz no deserto de Agafay ao pôr do sol e mergulha em sabores exóticos com uma aula privada de culinária marroquina.Inclui guia local para spots fotográficos únicos, alojamento riad com design tradicional e momentos livres para te perderes na espontaneidade da cidade. Para quem vive o agora com intensidade, esta experiência é uma tela viva à espera da tua lente")
    system.add_deal(novo_deal)
    print("Deal adicionado com sucesso!\n")

    creator_id = 44
    print(f"====> A obter ranking de deals para o creator com ID: {creator_id}")
    ranked_deals = system.get_deal_rank(creator_id)

    print(f"Deals rankeados para o creator {creator_id}:")
    print(ranked_deals)

if __name__ == "__main__":
    test_start_add_deal()