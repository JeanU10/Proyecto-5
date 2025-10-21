 📂 Estructura del Proyecto

```
app/src/main/java/com/example/evaluacion2/
├── model/                  # Modelos de datos
│   ├── User.kt            # Usuario
│   ├── Community.kt       # Comunidad
│   ├── Post.kt            # Publicación
│   ├── Metric.kt          # Métricas
│   └── Profile.kt         # Perfil de usuario
│
├── controller/            # Controladores (lógica de negocio)
│   ├── UserController.kt
│   ├── AuthController.kt
│   ├── CommunityController.kt
│   ├── PostController.kt
│   └── MetricsController.kt
│
├── view/                  # Vistas (UI con Jetpack Compose)
│   ├── MainActivity.kt              # Activity principal
│   ├── LoginScreen.kt               # Pantalla de login
│   ├── UserScreen.kt                # Pantalla de registro
│   ├── HomeScreen.kt                # Feed de inicio
│   ├── CommunitiesScreen.kt         # Lista de comunidades
│   ├── CreatePostScreen.kt          # Crear publicación
│   ├── MetricsListScreen.kt         # Lista de métricas
│   ├── CommunityMetricsScreen.kt    # Métricas detalladas
│   ├── ProfileScreen.kt             # Perfil de usuario
│   └── components/
│       └── BottomNavBar.kt          # Barra de navegación inferior
│
└── navigation/
    └── NavGraph.kt        # Configuración de navegación
```

## 🎯 Funcionalidades Implementadas

### ✅ Autenticación
- **LoginScreen**: Inicio de sesión con email y contraseña
- **UserScreen**: Registro de nuevos usuarios
- Opción de "Continuar como Invitado"

### ✅ Feed de Inicio (HomeScreen)
- Visualización de publicaciones de comunidades
- Crear publicaciones con texto e imágenes
- Publicaciones con encuestas
- Eventos comunitarios

### ✅ Comunidades (CommunitiesScreen)
- Lista de comunidades con categorías:
  - Destacadas
  - Cerca (por distancia)
  - Mis comunidades
  - Nuevas
- Búsqueda de comunidades
- Estados: Abierta, Autogestión, Solicitar acceso
- Unirse o solicitar acceso a comunidades

### ✅ Crear Publicación (CreatePostScreen)
- Título y contenido
- Añadir imágenes o videos
- Opciones:
  - Activar/desactivar comentarios
  - Configurar visibilidad (Público/Solo miembros)
- Guardar como borrador

### ✅ Métricas (MetricsListScreen & CommunityMetricsScreen)
- Lista de comunidades con categorías
- Métricas detalladas por comunidad:
  - Miembros activos
  - Nuevos ingresos
  - Participación
  - Encuestas activas
- Gráficos de actividad semanal
- Períodos de tiempo: 7d, 30d, 90d
- Top comunidades por interacción
- Objetivos comunitarios con estado

### ✅ Perfil (ProfileScreen)
- Información del usuario
- Estadísticas:
  - Número de comunidades
  - Puntuación
  - Impacto mensual
  - Interacciones últimos 30 días
- Lista de comunidades del usuario
- Actividad reciente
- Cerrar sesión

## 🎨 Diseño

- **Material Design 3** (Material You)
- Tema de colores: Gris y negro
- Componentes modernos de Jetpack Compose
- Navegación con bottom navigation bar

## 🔄 Flujo de Navegación

```
Login → Registro → Home
  ↓
  └─→ Continuar como Invitado → Home

Home ↔ Comunidades ↔ Crear ↔ Métricas ↔ Perfil
         ↓
    Ver Detalles → Métricas Comunitarias
```

## 📊 Datos Mock

La aplicación actualmente usa datos de prueba (mock) en los controladores. Para conectar con un backend real:

1. Reemplaza los métodos en los controladores con llamadas a API
2. Usa Retrofit o Ktor para las peticiones HTTP
3. Implementa manejo de estados de carga y errores

## 🛠️ Tecnologías Utilizadas

- **Kotlin**: Lenguaje de programación
- **Jetpack Compose**: UI moderna y declarativa
- **Material 3**: Design system de Google
- **Navigation Compose**: Navegación entre pantallas
- **MVC Pattern**: Arquitectura del proyecto
