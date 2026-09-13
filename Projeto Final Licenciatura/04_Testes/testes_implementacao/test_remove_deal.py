from src.model.deal.deal import Deal
from model.system_manager import SystemManager


def test_start_add_deal():
    system = SystemManager()

    print("====> A iniciar sistema...")
    system.start()
    print("Sistema iniciado com sucesso!\n")

    print("====> Remover Deal\n")
    deal_id = 1
    system.remove_deal(deal_id)
    print("Deal removido com sucesso!\n")

    creator_id = 44
    print(f"====> A obter ranking de deals para o creator com ID: {creator_id}")
    ranked_deals = system.get_deal_rank(creator_id)

    print(f"Deals rankeados para o creator {creator_id}:")
    print(ranked_deals)

if __name__ == "__main__":
    test_start_add_deal()