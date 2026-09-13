from dataclasses import dataclass


@dataclass
class Deal:
    id: int
    title: str
    description: str

    def format_prompt(self):
        prefix = "document:"
        return f"{prefix} {self.title} {self.description}"
    

