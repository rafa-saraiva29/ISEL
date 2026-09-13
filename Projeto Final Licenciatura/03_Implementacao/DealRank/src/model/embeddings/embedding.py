from dataclasses import dataclass
import numpy as np


@dataclass
class Embedding:
    id: int
    vector: np.array
