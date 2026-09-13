from src.model.system_manager import SystemManager

def test_start_and_get_rank():
    system = SystemManager()

    print("====> A iniciar sistema...")
    system.start()
    print("Sistema iniciado com sucesso!\n")

    creator_id = 44
    print(f"====> A obter ranking de deals para o creator com ID: {creator_id}")
    ranked_deals = system.get_deal_rank(creator_id)

    print(f"Deals rankeados para o creator {creator_id}:")
    print(ranked_deals)

if __name__ == "__main__":
    test_start_and_get_rank()