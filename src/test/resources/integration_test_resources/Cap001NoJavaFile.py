import os

directorio = 'src\\test\\java\\com\\proy\\integration'  # Cambia esta ruta por la tuya

texto_a_insertar = "package com.proy.integration;\n\n"

for archivo in os.listdir(directorio):
    ruta_archivo = os.path.join(directorio, archivo)
    
    if os.path.isfile(ruta_archivo) and archivo.endswith('.java'):
        with open(ruta_archivo, 'r', encoding='utf-8') as f:
            contenido_original = f.read()
        
        with open(ruta_archivo, 'w', encoding='utf-8') as f:
            f.write(texto_a_insertar + contenido_original)

        print(f"Modificado: {archivo}")

print("✅ Proceso finalizado.")
