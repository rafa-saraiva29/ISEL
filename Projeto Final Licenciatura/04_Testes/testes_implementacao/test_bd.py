from src.barter.barter_repo import BarterRepo

if __name__ == "__main__":

    repo = BarterRepo()

    repo.build_deals_list()
    print("Deals carregados:")
    for deal in repo.deals_list:
        print(deal)

    repo.build_creators_list()
    print("Creators carregados:")
    for creator in repo.creators_list:
        print(creator)
