from model.deal.deal import Deal

class SystemController:
    def __init__(self, system_manager):
        self.system_manager = system_manager


    def add_deal(self, deal_data):
        deal = Deal(
            id = int(deal_data['id']),
            title = deal_data['title'],
            description = deal_data['description']
        )
        return {"message": self.system_manager.add_deal(deal)}, 200
    
    def remove_deal(self, deal_id):
        return {"message": self.system_manager.remove_deal(int(deal_id))}, 200
       
    def update_deal(self, deal_data):
        deal = Deal(
            id = int(deal_data['id']),
            title = deal_data['title'],
            description = deal_data['description']
        )
        return {"message": self.system_manager.update_deal(deal)}, 200

    def get_deal_rank(self, creator_id):
        creator_id = int(creator_id)
        ranked_deals, ranked_distances = self.system_manager.get_deal_rank(creator_id)

        deals_info = []

        if ranked_deals == f"Error: Creator {creator_id} does not exist.":
            return {"message": ranked_deals}, 200
        
        creator_description = self.system_manager.get_creator_by_id(creator_id).description
        
        for i in range(len(ranked_deals)):
            deal = self.system_manager.get_deal_by_id(int(ranked_deals[i]))
            distance = ranked_distances[i]
            if deal:
                deals_info.append({
                    "id": int(deal.id),
                    "title": deal.title,
                    "description": deal.description,
                    "distance": str(distance)
                })
            else:
                deals_info.append({
                    "id": int(ranked_deals[i]),
                    "message": "Deal not found"
                })

        return {"creator_id": creator_id, "creator_description": creator_description, "ranked_deals": deals_info}, 200

