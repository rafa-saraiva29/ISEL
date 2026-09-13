import pandas as pd

df = pd.read_csv("deals_anonymized.csv")

df_filtrado = df[['id', 'title', 'description']]

df_filtrado.to_csv("deals_db.csv", index=False)