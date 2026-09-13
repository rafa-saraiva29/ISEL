from barter.my_sql_dao import MySqlDAO
from barter.my_sql_parser import MySqlParser

class BarterRepo:

    def __init__(self):
        self.deals_list = []
        self.creators_list = []
        self.my_sql_dao = MySqlDAO()

    def build_deals_list(self):
        self.deals_list = []
        raw_data = self.my_sql_dao.select_deals_from_db()
        self.deals_list = MySqlParser.parse_deals(raw_data)
        return len(self.deals_list)

    def build_creators_list(self):
        self.creators_list = []
        raw_data = self.my_sql_dao.select_creators_from_db()
        self.creators_list = MySqlParser.parse_creators(raw_data)

    def add_deal(self, deal):
        if any(d.id == deal.id for d in self.deals_list):
            return f"Error: Deal {deal.id} already exists."
        self.deals_list.append(deal)
        self.my_sql_dao.add_deal_to_db(deal)
        return f"Deal {deal.id} added successfully."

    def remove_deal(self, deal_id):
        if not any(d.id == deal_id for d in self.deals_list):
            return f"Error: Deal {deal_id} does not exist."
        self.deals_list = [deal for deal in self.deals_list if deal.id != deal_id]
        self.my_sql_dao.remove_deal_from_db(deal_id)
        return f"Deal {deal_id} removed successfully."

    def update_deal(self, deal):
        if not any(d.id == deal.id for d in self.deals_list):
            return f"Error: Deal {deal.id} does not exist."
        self.my_sql_dao.update_deal_in_db(deal)
        self.update_deals_list(deal)
        return f"Deal {deal.id} updated successfully."

    def update_deals_list(self, deal):
        for i, d in enumerate(self.deals_list):
            if d.id == deal.id:
                self.deals_list[i] = deal
                break

