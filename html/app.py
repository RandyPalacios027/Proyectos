from flask import Flask, render_template, request, send_file
import os
from pdf2docx import Converter # Usaremos el de Word como ejemplo

app = Flask(__name__)

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/convertir', methods=['POST'])
def convertir():
    archivo = request.files['archivo']
    if archivo:
        ruta_original = os.path.join('img', archivo.filename)
        archivo.save(ruta_original)
        
        ruta_salida = ruta_original.replace('.pdf', '.docx')
        
        # Lógica de conversión
        cv = Converter(ruta_original)
        cv.convert(ruta_salida)
        cv.close()
        
        return send_file(ruta_salida, as_attachment=True)

if __name__ == '__main__':
    app.run(debug=True)