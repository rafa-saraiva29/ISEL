import time
import ollama

start_time = time.time()
response = ollama.chat(
    model="thinkverse/towerinstruct",
    messages=[
        {"role": "system", "content": "You are a precise translator."},
        {"role": "user", "content": "Translate to English: Ik ben een voedingsdeskundige met jarenlange ervaring uit diverse samenwerkingsprojecten. Het creÃ«ert een warm gevoel binnen me wanneer ik gezonde, smakelijke recepten ontwerp en op maat gemaakte fotografieÃ«n van mijn gerechten kan bewerken!"}
    ]
)
end_time = time.time()
duration = end_time - start_time
print("A tradução de uma descrição demorou", duration, "s.")
print(response)
