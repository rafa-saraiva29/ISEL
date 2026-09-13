class CreatorRepo:
    def __init__(self):
        self.deal_rank = {}
        self.distances_rank = {}
    
    def build_deal_rank(self, creator_id, ranked_deals_by_id, distances_by_id):
        self.deal_rank[creator_id] = ranked_deals_by_id
        self.distances_rank[creator_id] = distances_by_id
    
    def get_rank_for_creator(self, creator_id):
        if not self.deal_rank:
            return {"error": "System not initiated"}, 500
        if creator_id in self.deal_rank:
            return self.deal_rank[int(creator_id)], self.distances_rank[int(creator_id)]
        else:
            return f"Error: Creator {creator_id} does not exist."