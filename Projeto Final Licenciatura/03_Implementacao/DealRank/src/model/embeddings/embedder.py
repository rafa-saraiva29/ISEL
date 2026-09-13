from model.embeddings.embedding import Embedding
from model.embeddings.ai.ollama_dao import OllamaDAO

class Embedder:

    def generate_deal_embed(self, deal):
        prompt = deal.format_prompt()
        vector = OllamaDAO().embed_one(prompt)

        return Embedding(int(deal.id), vector)
    
    def generate_embeddings(self, list):
        prompts = []
        for elem in list:
            prompt = elem.format_prompt()
            prompts.append(prompt)
        vectors = OllamaDAO.embed_many(prompts)
        return [Embedding(list[i].id, vectors[i]) for i in range(len(vectors))]
