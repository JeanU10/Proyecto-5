const express = require('express');
const sql = require('mssql');
const cors = require('cors');
const { v4: uuidv4 } = require('uuid'); // Para generar IDs únicos
require('dotenv').config();

const app = express();

// Middleware: Permite que tu app Android (o cualquiera) envíe JSON
app.use(express.json());
app.use(cors());

// Configuración de la conexión a Azure SQL
const dbConfig = {
    user: process.env.DB_USER,
    password: process.env.DB_PASS,
    server: process.env.DB_SERVER, 
    database: process.env.DB_NAME,
    options: {
        encrypt: true, // Obligatorio para Azure
        trustServerCertificate: false
    }
};

// --- RUTA 1: OBTENER POSTS (GET) ---
// Esto es lo que llama tu app para llenar el muro
app.get('/api/posts', async (req, res) => {
    try {
        // 1. Conectar
        let pool = await sql.connect(dbConfig);
        
        // 2. Consultar
        let result = await pool.request().query('SELECT * FROM Posts ORDER BY fecha_creacion DESC');
        
        // 3. Responder a Android
        res.json(result.recordset);
        
    } catch (err) {
        console.error("Error leyendo posts:", err);
        res.status(500).send('Error en el servidor al leer datos');
    }
});

// --- RUTA 2: CREAR POST (POST) ---
// Esto recibe el objeto Post de tu Android y lo guarda
app.post('/api/posts', async (req, res) => {
    try {
        const post = req.body;
        
        // Generamos un ID único si viene vacío desde Android
        const nuevoId = post.id || uuidv4(); 

        // 1. Conectar
        let pool = await sql.connect(dbConfig);
        
        // 2. Insertar (Usamos inputs para evitar hackeos/inyección SQL)
        await pool.request()
            .input('id', sql.NVarChar, nuevoId)
            .input('autor', sql.NVarChar, post.autor)
            .input('autorImagen', sql.NVarChar, post.autorImagen)
            .input('tiempo', sql.NVarChar, post.tiempo) // En un futuro podrías calcular esto en el server
            .input('tipo', sql.NVarChar, post.tipo)     // Guardará "PUBLICACION", "ENCUESTA", etc.
            .input('contenido', sql.NVarChar, post.contenido)
            .input('comunidad', sql.NVarChar, post.comunidad)
            .query(`
                INSERT INTO Posts (id, autor, autorImagen, tiempo, tipo, contenido, comunidad)
                VALUES (@id, @autor, @autorImagen, @tiempo, @tipo, @contenido, @comunidad)
            `);

        // 3. Responder éxito y devolver el objeto con el ID real
        res.status(201).json({ ...post, id: nuevoId, mensaje: "Guardado en Azure" });

    } catch (err) {
        console.error("Error guardando post:", err);
        res.status(500).send('Error al guardar el post');
    }
});

// Ruta base para verificar que el server vive
app.get('/', (req, res) => res.send('API Red Social v1.0 Online 🚀'));

// Exportar para Vercel
module.exports = app;

// Si lo corres localmente con 'node index.js'
if (require.main === module) {
    const port = process.env.PORT || 3000;
    app.listen(port, () => {
        console.log(`Servidor corriendo en puerto ${port}`);
    });
}