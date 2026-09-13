from dataclasses import dataclass


@dataclass
class Creator:
    id: int
    description: str

    def format_prompt(self):
        prefix = "search_query:"
        return f"{prefix} {self.description}"
