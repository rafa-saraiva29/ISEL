import mysql.connector

class MySqlDAO:

    def connect_to_db(self):
        conn = mysql.connector.connect(
                host="localhost",
                user="barter_user",
                password="root",
                database="barter"
            )
        return conn

    def select_deals_from_db(self):
        try:
            conn = self.connect_to_db()
            cursor = conn.cursor(dictionary=True)

            query = "SELECT id, title, description FROM deals"
            cursor.execute(query)

            results = cursor.fetchall()
            conn.close()
            return results
        
        except Exception as e:
            print(f"Error on select_deal_from_db(): {e}")
    
    def select_creators_from_db(self):
        try:
            conn = self.connect_to_db()
            cursor = conn.cursor(dictionary=True)
            
            query = "SELECT id, description FROM creators"
            cursor.execute(query)

            results = cursor.fetchall()
            conn.close()
            return results
        
        except Exception as e:
            print(f"Error on select_creators_from_db(): {e}")
    
    def add_deal_to_db(self, deal):

        try:
            conn = self.connect_to_db()
            cursor = conn.cursor()

            query = "INSERT INTO deals (id, title, description) VALUES (%s, %s, %s)"
            valores = (deal.id, deal.title, deal.description)

            cursor.execute(query, valores)
            conn.commit()

        except Exception as e:
            print(f"Error on add_deal_to_db(): {e}")
        finally:
            if conn.is_connected():
                cursor.close()
                conn.close()
    
    def remove_deal_from_db(self, deal_id):
        try:
            conn = self.connect_to_db()
            cursor = conn.cursor()

            query = "DELETE FROM deals WHERE id = %s"

            cursor.execute(query, (deal_id,))
            conn.commit()

        except Exception as e:
            print(f"Error on remove_deal_to_db(): {e}")

        finally:
            if conn:
                cursor.close()
                conn.close()
    
    def update_deal_in_db(self, deal):
        try:
            conn = self.connect_to_db()
            cursor = conn.cursor()

            query = "UPDATE deals SET title = %s, description = %s WHERE id = %s"

            cursor.execute(query, (
                deal.title,
                deal.description,
                deal.id
            ))
            conn.commit()

        except Exception as e:
            print(f"Error on update_deal_to_db(): {e}")

        finally:
            if conn:
                cursor.close()
                conn.close()

    

