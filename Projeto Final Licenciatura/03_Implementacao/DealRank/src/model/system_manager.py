import time
from barter.barter_repo import BarterRepo
from model.embeddings.embed_manager import EmbedManager
from model.creator.creator_manager import CreatorManager
from model.deal.deal_manager import DealManager
from model.startup_manager import StartupManager

class SystemManager:

    def __init__(self):
        self.embed_manager = EmbedManager()
        self.barter_repo = BarterRepo()
        self.creator_manager = CreatorManager(self.barter_repo)
        self.deal_manager = DealManager(self.barter_repo)
        self.startup_manager = StartupManager(self.embed_manager, self.creator_manager, self.deal_manager)
    
    def start(self):
        self.startup_manager.get_deals_from_barter()
        self.startup_manager.get_creators_from_barter()
        self.startup_manager.create_embeddings()
        deal_embeddings = self.embed_manager.get_deal_embeddings()
        creator_embeddings = self.embed_manager.get_creator_embeddings()
        self.creator_manager.index_and_rank_deals_for_creators(deal_embeddings, creator_embeddings)

    def add_deal(self, deal):
        message = self.deal_manager.add_deal(deal)

        if message == f"Deal {deal.id} added successfully.":
            self.embed_manager.add_deal_embed(deal)
            deal_embeddings = self.embed_manager.get_deal_embeddings()
            creator_embeddings = self.embed_manager.get_creator_embeddings()
            self.creator_manager.index_and_rank_deals_for_creators(deal_embeddings, creator_embeddings)

        return message
    
    def remove_deal(self, deal_id):
        message = self.deal_manager.remove_deal(deal_id)

        if message == f"Deal {deal_id} removed successfully.":
            self.embed_manager.remove_deal_embed(deal_id)
            deal_embeddings = self.embed_manager.get_deal_embeddings()
            creator_embeddings = self.embed_manager.get_creator_embeddings()
            self.creator_manager.index_and_rank_deals_for_creators(deal_embeddings, creator_embeddings)
        
        return message
    
    def update_deal(self, deal):
        message = self.deal_manager.update_deal(deal)

        if message == f"Deal {deal.id} updated successfully.":
            self.embed_manager.update_deal_embed(deal)
            deal_embeddings = self.embed_manager.get_deal_embeddings()
            creator_embeddings = self.embed_manager.get_creator_embeddings()
            self.creator_manager.index_and_rank_deals_for_creators(deal_embeddings, creator_embeddings)
            
        return message
    
    def get_deal_rank(self, creator_id):
        return self.creator_manager.get_rank_for_creator(creator_id)
    
    def get_deal_by_id(self, deal_id):
        return self.deal_manager.get_deal_by_id(deal_id)
    
    def get_creator_by_id(self, creator_id):
        return self.creator_manager.get_creator_by_id(creator_id)