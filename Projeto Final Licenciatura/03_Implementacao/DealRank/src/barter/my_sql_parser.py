from model.creator.creator import Creator
from model.deal.deal import Deal

class MySqlParser:

     def parse_deals(raw_data):
          return [Deal(id=row['id'], title=row['title'], description=row['description']) for row in raw_data]
     
     def parse_creators(raw_data):
          return [Creator(id=row['id'], description=row['description']) for row in raw_data]