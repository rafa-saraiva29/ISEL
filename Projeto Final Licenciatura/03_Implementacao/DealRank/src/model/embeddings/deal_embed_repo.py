class DealEmbedRepo:
    def __init__(self):
        self.deal_embeddings = []

    def store_embed(self, embedding):
        self.deal_embeddings.append(embedding)
    
    def remove_embed(self, id):
        original_len = len(self.deal_embeddings)
        self.deal_embeddings = [e for e in self.deal_embeddings if e.id != id]
        if len(self.deal_embeddings) == original_len:
            raise ValueError(f"Embedding with id {id} not found.")
    
    def update_embed(self, embedding):
        embedding_id = int(embedding.id)
        for i, e in enumerate(self.deal_embeddings):
            if e.id == embedding_id:
                self.deal_embeddings[i] = embedding
                return
            
        raise ValueError(f"Embedding with id {embedding.id} not found.")