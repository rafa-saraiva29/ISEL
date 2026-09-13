import pandas as pd
import re
import ollama

df = pd.read_csv("deals_db.csv")

def clean_text(text):
    if isinstance(text, bytes):
        text = text.decode("UTF-8")
    text = text.replace("<br />", " ")
    text = re.sub(r'[^a-zA-Z\u00C0-\u00FF]+', ' ', text)
    return text.strip()

def translate(text):
    if not text.strip():
        return text
    prompt = (
        f"Translate this text into English preserving its meaning:\n\n{text.strip()}\n\n"
        "Translation:"
    )
    try:
        response = ollama.chat(
            model="thinkverse/towerinstruct",
            messages=[
                {"role": "system", "content": "You are a precise and reliable translator."},
                {"role": "user", "content": prompt}
            ],
            options={"temperature": 0.0}
        )
        translation = response["message"]["content"].strip()
        return translation
    except Exception as e:
        print(f"Translation error: {e}")
        return text

df["title"] = df["title"].astype(str).apply(clean_text)
df["description"] = df["description"].astype(str).apply(clean_text)

df["title"] = df["title"].astype(str).apply(translate)
df["description"] = df["description"].astype(str).apply(translate)

df.to_csv("deals_limpos.csv", index=False)
