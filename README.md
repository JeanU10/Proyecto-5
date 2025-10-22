 # PROYECTO 5

## Descripción del Proyecto

El Proyecto 5 transforma la educación, pasando de un modelo jerárquico y aislado a uno colectivo y significativo. A diferencia de plataformas tradicionales, permite a los usuarios crear o unirse a comunidades temáticas para compartir conocimientos de manera horizontal (por ejemplo, enseñar lenguaje de señas). Impacta positivamente en jóvenes y estudiantes al reducir el aislamiento, fortalecer el sentido de pertenencia y fomentar una motivación intrínseca para aprender, creando un entorno digital humano, inclusivo y motivador.

## 🧑‍💻 Integrantes
- **Vicente A. Elias Riveros**
- **Jean P. Valenzuela Navarrete**

## Arquitectura

La aplicación sigue un patrón arquitectónico **Modelo-Vista-Controlador (MVC)** con la siguiente estructura:

```
app/src/main/java/com/example/evaluacion2/
├── model/                  # Modelos de Datos
│   ├── User.kt            # Entidad Usuario
│   ├── Community.kt       # Entidad Comunidad
│   ├── Post.kt            # Entidad Publicación
│   ├── Metric.kt          # Métricas de Análisis
│   └── Profile.kt         # Perfil de Usuario
│
├── controller/            # Controladores de Lógica de Negocio
│   ├── UserController.kt
│   ├── AuthController.kt
│   ├── CommunityController.kt
│   ├── PostController.kt
│   ├── PostRepository.kt
│   └── MetricsController.kt
│
├── view/                  # Componentes de UI (Jetpack Compose)
│   ├── MainActivity.kt              # Actividad Principal
│   ├── LoginScreen.kt               # Autenticación
│   ├── RegisterScreen.kt            # Registro de Usuario
│   ├── HomeScreen.kt                # Panel de Feed
│   ├── CommunitiesScreen.kt         # Descubrimiento de Comunidades
│   ├── CreatePostScreen.kt          # Creación de Contenido
│   ├── MetricsListScreen.kt         # Resumen de Análisis
│   ├── CommunityMetricsScreen.kt    # Análisis Detallados
│   ├── ProfileScreen.kt             # Perfil de Usuario
│   ├── EditProfileScreen.kt         # Gestión de Perfil
│   └── components/
│       └── BottomNavBar.kt          # Componente de Navegación
│
├── navigation/
│   └── NavGraph.kt        # Configuración de Navegación
│
└── ui/theme/              # Configuración de Tema UI
    ├── Color.kt
    ├── Theme.kt
    └── Type.kt
```

## Características Principales

### 🔐 Autenticación y Gestión de Usuarios
- **Sistema de Inicio de Sesión Seguro**: Autenticación por email/contraseña con validación
- **Registro de Usuarios**: Incorporación completa de usuarios con creación de perfil
- **Modo Invitado**: Acceso limitado para usuarios no registrados
- **Gestión de Sesiones**: Estado de inicio de sesión persistente y funcionalidad de cierre de sesión

### 🏠 Feed de Inicio
- **Feed de Contenido Dinámico**: Publicaciones y actualizaciones comunitarias en tiempo real
- **Creación de Contenido**: Publicaciones de texto, encuestas y anuncios de eventos
- **Soporte Multimedia**: Capacidades de adjuntar imágenes y videos
- **Elementos Interactivos**: Votación, comentarios y seguimiento de participación

### 👥 Gestión de Comunidades
- **Descubrimiento de Comunidades**: Listados de comunidades categorizados
  - Comunidades Destacadas
  - Comunidades Basadas en Ubicación
  - Comunidades de Usuario
  - Nuevas Comunidades
- **Estados de Comunidad**: Abierta, Autogestionada y Solicitar Acceso
- **Búsqueda y Filtrado**: Herramientas avanzadas de descubrimiento de comunidades
- **Gestión de Membresía**: Solicitudes de unión y asignación de roles

### 📊 Análisis y Métricas
- **Análisis de Comunidades**: Métricas de rendimiento integrales
  - Miembros Activos
  - Adquisición de Nuevos Miembros
  - Tasas de Participación
  - Actividad de Encuestas
- **Reportes Basados en Tiempo**: Períodos de 7 días, 30 días y 90 días
- **Seguimiento de Rendimiento**: Clasificación de comunidades y monitoreo de objetivos
- **Paneles Visuales**: Gráficos interactivos y visualización de datos

### 👤 Perfiles de Usuario
- **Perfiles Integrales**: Información detallada del usuario y estadísticas
- **Seguimiento de Actividad**: Interacciones recientes y participación comunitaria
- **Sistema de Logros**: Puntuación y medición de impacto
- **Personalización de Perfil**: Información editable del usuario y preferencias

## Especificaciones Técnicas

### Stack de Desarrollo
- **Lenguaje**: Kotlin
- **Framework de UI**: Jetpack Compose
- **Sistema de Diseño**: Material Design 3
- **Navegación**: Navigation Compose
- **Carga de Imágenes**: Coil
- **Arquitectura**: Patrón MVC
- **SDK Mínimo**: API 24 (Android 7.0)
- **SDK Objetivo**: API 34 (Android 14)

### Dependencias Principales
```kotlin
// Core Android
implementation("androidx.core:core-ktx:1.12.0")
implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
implementation("androidx.activity:activity-compose:1.8.1")

// Compose BOM
implementation(platform("androidx.compose:compose-bom:2023.10.01"))
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.material3:material3")

// Navegación
implementation("androidx.navigation:navigation-compose:2.7.5")

// Carga de Imágenes
implementation("io.coil-kt:coil-compose:2.5.0")

// Iconos Extendidos
implementation("androidx.compose.material:material-icons-extended:1.5.4")
```

## Diseño de Experiencia de Usuario

### Principios de Diseño
- **Material Design 3**: Interfaz moderna y accesible siguiendo las directrices de diseño de Google
- **Esquema de Colores Consistente**: Paleta profesional de colores grises y negros
- **Diseño Adaptativo**: Diseño adaptativo para varios tamaños de pantalla
- **Navegación Intuitiva**: Barra de navegación inferior con jerarquía visual clara

### Flujo de Navegación
```
Flujo de Autenticación:
Inicio de Sesión → Registro → Panel de Inicio
  ↓
Acceso de Invitado → Panel de Inicio (Características Limitadas)

Flujo Principal de la Aplicación:
Inicio ↔ Comunidades ↔ Crear Contenido ↔ Análisis ↔ Perfil
         ↓
    Detalles de Comunidad → Análisis Detallados
```

## Gestión de Datos

### Implementación Actual
La aplicación actualmente utiliza **datos simulados** para propósitos de demostración. Todos los controladores implementan almacenamiento de datos en memoria con datos de muestra predefinidos.

### Integración de Producción
Para integrar con un servicio backend:

1. **Integración API**: Reemplazar métodos de datos simulados con llamadas de cliente HTTP
2. **Librerías Recomendadas**: Retrofit o Ktor para comunicación de red
3. **Gestión de Estado**: Implementar estados de carga apropiados y manejo de errores
4. **Persistencia de Datos**: Agregar soporte de base de datos local (Room) para funcionalidad offline
5. **Autenticación**: Implementar autenticación segura basada en tokens

## Guías de Desarrollo

### Estándares de Código
- **Convenciones de Código Kotlin**: Seguir las directrices de estilo oficiales de Kotlin
- **Mejores Prácticas de Compose**: Gestión apropiada de estado y optimización de recomposición
- **Patrones de Arquitectura**: Mantener separación de responsabilidades entre capas
- **Manejo de Errores**: Manejo integral de errores y retroalimentación del usuario

### Estrategia de Pruebas
- **Pruebas Unitarias**: Validación de lógica de negocio en controladores
- **Pruebas de UI**: Pruebas de componentes de UI de Compose
- **Pruebas de Integración**: Validación de flujos de usuario de extremo a extremo

## Despliegue

### Configuración de Build
- **Tipo de Build**: Configuraciones de Debug y Release
- **ProGuard**: Ofuscación de código para builds de release
- **Gestión de Versiones**: Versionado semántico (actualmente v1.0)

### Distribución
- **Plataformas Objetivo**: Dispositivos Android (API 24+)
- **Instalación**: Distribución APK o despliegue en Google Play Store
