from src.model.deal.deal import Deal
from src.model.system_manager import SystemManager

def main():
    system = SystemManager()

    print("StartingUp...")
    system.start()
    print("StartUp Complete!\n")

    while True:
        print("\nChoose an option:")
        print("1 - Add Deal")
        print("2 - Remove Deal")
        print("3 - Update Deal")
        print("4 - Get Deal Rank for Creator")
        print("0 - Exit")

        escolha = input("Option: ").strip()

        if escolha == "1":
            deal_id = int(input("Deal ID: "))
            title = input("Deal Title: ")
            description = input("Deal Description: ")
            novo_deal = Deal(deal_id, title, description)
            system.add_deal(novo_deal)
            print("Deal added successfully!")

        elif escolha == "2":
            deal_id = int(input("Deal ID: "))
            system.remove_deal(deal_id) 
            print("Deal removed successfully!")

        elif escolha == "3":
            deal_id = int(input("Deal ID: "))
            title = input("Deal Title: ")
            description = input("Deal Description: ")
            deal = Deal(deal_id, title, description)
            system.update_deal(deal)

        elif escolha == "4":
            creator_id = int(input("Creator ID: "))
            creator = system.get_creator_by_id(creator_id)
            print(f"Creators Description: {creator.description}")
            ranked_deals = system.get_deal_rank(creator_id)
            print(f"\nRanking para o creator {creator_id}:\n")

            for i, deal_id in enumerate(ranked_deals, 1):
                deal = system.get_deal_by_id(deal_id) 
                if deal:
                    print(f"{i}. Deal ID: {deal.id}")
                    print(f"   Título: {deal.title}")
                    print(f"   Descrição: {deal.description}\n")
                else:
                    print(f"{i}. Deal ID: {deal_id} (deal não encontrado)\n")

        elif escolha == "0":
            print("Finished")
            break

        else:
            print("Opção inválida, tenta novamente.")

if __name__ == "__main__":
    main()
