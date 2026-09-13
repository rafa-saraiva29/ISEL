from model.embeddings.embedder import Embedder
from model.embeddings.deal_embed_repo import DealEmbedRepo
from model.embeddings.creator_embed_repo import CreatorEmbedRepo

class EmbedManager:
    def __init__(self):
        self.embedder = Embedder()
        self.deal_embed_repo = DealEmbedRepo()
        self.creator_embed_repo = CreatorEmbedRepo()

    def create_deal_embed(self, deal_list):
        self.deal_embed_repo.deal_embeddings = []
        embeddings = self.embedder.generate_embeddings(deal_list)
        for embed in embeddings:
            self.deal_embed_repo.store_embed(embed)
        

    def create_creator_embed(self, creator_list):
        self.creator_embed_repo.creator_embeddings = []
        embeddings = self.embedder.generate_embeddings(creator_list)
        for embed in embeddings:
            self.creator_embed_repo.store_embed(embed)
    
    def add_deal_embed(self, deal):
        deal_embed = self.embedder.generate_deal_embed(deal)
        self.deal_embed_repo.store_embed(deal_embed)
    
    def remove_deal_embed(self, deal_id):
        self.deal_embed_repo.remove_embed(deal_id)
    
    def update_deal_embed(self, deal):
        deal_embed = self.embedder.generate_deal_embed(deal)
        self.deal_embed_repo.update_embed(deal_embed)
    
    def get_deal_embeddings(self):
        return self.deal_embed_repo.deal_embeddings
    
    def get_creator_embeddings(self):
        return self.creator_embed_repo.creator_embeddings

