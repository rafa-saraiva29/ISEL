from model.creator.creator_repo import CreatorRepo
import numpy as np
import faiss

class CreatorManager:

    def __init__(self, barter_repo):
        self.barter_repo = barter_repo
        self.creator_repo = CreatorRepo()
        self.deal_index_faiss = None
        
    def build_creators_list(self):
        self.barter_repo.build_creators_list()

    def get_rank_for_creator(self, creator_id):
        return self.creator_repo.get_rank_for_creator(creator_id)
    
    def build_deal_rank(self, creator_id, ranked_deals_by_id, distances_by_id):
        self.creator_repo.build_deal_rank(creator_id, ranked_deals_by_id, distances_by_id)
    
    def index_and_rank_deals_for_creators(self, deal_embeddings, creator_embeddings):
        self.deal_index_faiss = None
        deal_vectors = np.vstack([embed.vector for embed in deal_embeddings])
        d = deal_vectors.shape[1]

        
        self.deal_index_faiss = faiss.IndexHNSWFlat(d, 32)
        self.deal_index_faiss.hnsw.efConstruction = 40  
        self.deal_index_faiss.add(deal_vectors)

    
        self.deal_index_faiss.hnsw.efSearch = 64

        for creator_embed in creator_embeddings:
            query_vector = creator_embed.vector.reshape(1, -1)
            distances, indices = self.deal_index_faiss.search(query_vector.astype('float32'), 10)

            ranked_deals_by_id = [deal_embeddings[i].id for i in indices[0]]
            self.build_deal_rank(creator_embed.id, ranked_deals_by_id, distances[0])

    
    def get_creators(self):
        return self.barter_repo.creators_list
    
    def get_creator_by_id(self, creator_id):
        creators_list = self.get_creators()

        for creator in creators_list:
            if str(creator.id).strip() == str(creator_id).strip():
                return creator
        return None




    
