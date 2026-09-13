from barter.barter_repo import BarterRepo


class DealManager:

    def __init__(self, barter_repo):
        self.barter_repo = barter_repo

    def build_deals_list(self):
        self.barter_repo.build_deals_list()

    def add_deal(self, deal):
        return self.barter_repo.add_deal(deal)
    
    def remove_deal(self, deal_id):
        return self.barter_repo.remove_deal(deal_id)
    
    def update_deal(self, deal):
        return self.barter_repo.update_deal(deal)
    
    def get_deals(self):
        return self.barter_repo.deals_list
    
    def get_deal_by_id(self, deal_id):
        deals_list = self.get_deals()

        for deal in deals_list:
            if str(deal.id).strip() == str(deal_id).strip():
                return deal
        return None

