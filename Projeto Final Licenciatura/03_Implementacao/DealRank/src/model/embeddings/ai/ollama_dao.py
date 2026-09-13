import ollama
import numpy as np
from typing import List

class OllamaDAO:
    _DEFAULT_MODEL = "nomic-embed-text:v1.5"

    @staticmethod
    def embed_one(prompt, model = None):
        model = model or OllamaDAO._DEFAULT_MODEL

        resp = ollama.embed(model=model, input=prompt)
        vec = resp.get("embedding") or resp.get("embeddings")
        if vec is None:
            raise ValueError(f"Ollama returned no embedding: {resp}")

        arr = np.asarray(vec, dtype=np.float32)
        arr /= np.linalg.norm(arr) + 1e-12
        return arr

    @staticmethod
    def embed_many(prompts, model = None):
        model = model or OllamaDAO._DEFAULT_MODEL

        resp = ollama.embed(model=model, input=prompts)
        vecs = resp.get("embeddings")
        if vecs is None:
            raise ValueError(f"Ollama returned no embeddings: {resp}")

        arr = np.asarray(vecs, dtype=np.float32)
        arr /= np.linalg.norm(arr, axis=1, keepdims=True) + 1e-12
        return arr
