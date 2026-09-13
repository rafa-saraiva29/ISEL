import pandas as pd

df = pd.read_csv("creators_anonymized.csv")

df_filtrado = df[['id', 'description']]

df_filtrado.to_csv("creators_db.csv", index=False)