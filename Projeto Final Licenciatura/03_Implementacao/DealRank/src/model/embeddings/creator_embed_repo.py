class CreatorEmbedRepo:
    def __init__(self):
        self.creator_embeddings = []
    
    def store_embed(self, embedding):
        self.creator_embeddings.append(embedding)