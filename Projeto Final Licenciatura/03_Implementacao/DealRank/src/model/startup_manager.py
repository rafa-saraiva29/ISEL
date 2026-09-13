from model.creator.creator_manager import CreatorManager
from model.deal.deal_manager import DealManager
from model.embeddings.embed_manager import EmbedManager


class StartupManager:

    def __init__(self, embed_manager, creator_manager, deal_manager):
        self.embed_manager = embed_manager
        self.creator_manager = creator_manager
        self.deal_manager = deal_manager


    def get_deals_from_barter(self):
        self.deal_manager.build_deals_list()
    
    def get_creators_from_barter(self):
        self.creator_manager.build_creators_list()
    
    def create_embeddings(self):
        self.embed_manager.create_deal_embed(self.deal_manager.get_deals())
        self.embed_manager.create_creator_embed(self.creator_manager.get_creators())