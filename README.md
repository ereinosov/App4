# App4 — Contenedores UI con SDK de Supabase en Android

![Android](https://img.shields.io/badge/Android-API%2029%2B-3DDC84?style=flat&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-2.3.21-7F52FF?style=flat&logo=kotlin&logoColor=white)
![Supabase](https://img.shields.io/badge/Supabase-postgrest--kt%203.6.0-3ECF8E?style=flat&logo=supabase&logoColor=white)
![Glide](https://img.shields.io/badge/Glide-4.16.0-18A303?style=flat)
![Android Studio](https://img.shields.io/badge/Android%20Studio-Panda%204-3DDC84?style=flat&logo=androidstudio&logoColor=white)

**Universidad:** Universidad Técnica Estatal de Quevedo (UTEQ)  
**Facultad:** Facultad de Ciencias de la Computación (FCC)  
**Carrera:** Software  
**Asignatura:** Aplicaciones Móviles "A"  
**Actividad:** Contenedores UI con SDK de Supabase  
**Estudiante:** Eduardo Reinoso Vélez  
© 2026

---

## Objetivo

Implementar una interfaz Android en Kotlin que utilice contenedores (`ListView`) para mostrar una lista de alumnos obtenida desde Supabase mediante el SDK oficial `postgrest-kt`, con carga dinámica de imágenes usando Glide y navegación en cascada entre niveles, materias y alumnos.

---

## Pantallas

| Pantalla | Descripción |
|---|---|
| **MainActivityContenedoresImagen** | Dropdown de semestre, dropdown de materias (carga según el semestre seleccionado) y `ListView` de alumnos con foto circular, nombre, correo y teléfono |

## Captura

![Contenedores UI](app/screenshots/img.png)
---


## Tecnologías

| Tecnología | Versión | Rol |
|---|---|---|
| Android Studio | Panda 4 | IDE de desarrollo |
| Kotlin | 2.3.21 | Lenguaje de programación principal |
| Supabase (postgrest-kt) | 3.6.0 | Consultas asíncronas a la base de datos |
| Ktor Client Android | 3.5.0 | Motor HTTP interno del SDK de Supabase |
| Glide | 4.16.0 | Carga y transformación circular de imágenes desde URL |
| ConstraintLayout | 2.2.1 | Posicionamiento de vistas en el layout principal |
| Material Design | 1.14.0 | Componentes UI (`TextInputLayout`, `ExposedDropdownMenu`) |
| Gradle | 9.2.1 | Gestión de dependencias |

---

## Arquitectura

- **Models** — data classes serializables (`Alumno`, `Materia`) mapeadas desde la respuesta JSON de Supabase.
- **Adapters** — `AlumnoAdapter` extiende `ArrayAdapter<Alumno>` e infla `item_alumno.xml`, con carga de imágenes delegada a Glide con transformación `circleCrop()`.
- **Services** — `SupabaseManager` inicializa el cliente Supabase con credenciales desde `BuildConfig`. Las consultas se ejecutan en `lifecycleScope.launch`.

---

## Estructura del proyecto

```
App4/
├── app/
│   └── src/
│       └── main/
│           ├── java/com/uteq/software/app4/
│           │   ├── Adapters/
│           │   │   └── AlumnoAdapter.kt
│           │   ├── Models/
│           │   │   ├── Alumno.kt
│           │   │   └── Materia.kt
│           │   ├── services/
│           │   │   ├── SupabaseManager.kt
│           │   │   └── SupabaseErrorHandler.kt
│           │   ├── MainActivity.kt
│           │   ├── MainActivityContenedoresImagen.kt
│           │   └── MainContenedor.kt
│           ├── res/
│           │   ├── layout/
│           │   │   ├── activity_main_contenedores_imagen.xml
│           │   │   ├── activity_main_contenedor.xml
│           │   │   ├── activity_main.xml
│           │   │   └── item_alumno.xml
│           │   └── drawable/
│           │       ├── logouteq.png
│           │       ├── mail.png
│           │       └── call.png
│           └── AndroidManifest.xml
├── gradle/
│   └── libs.versions.toml
├── local.properties         # No incluido en el repositorio
└── build.gradle.kts
```

---

## Dependencias principales

```toml
postgrest-kt             = "io.github.jan-tennert.supabase:postgrest-kt:3.6.0"
ktor-client-android      = "io.ktor:ktor-client-android:3.5.0"
glide                    = "com.github.bumptech.glide:glide:4.16.0"
kotlinx-serialization-json = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.11.0"
```

---

## Configuración de credenciales

Las credenciales de Supabase se almacenan en `local.properties` (excluido del repositorio por `.gitignore`) e inyectadas en tiempo de compilación mediante `BuildConfig`:

```properties
SUPABASE_URL=https://<tu-proyecto>.supabase.co
SUPABASE_KEY=<tu-anon-key>
```

---

## Requisitos previos

- Android Studio Panda 4 o superior
- JDK 11
- Dispositivo o emulador con Android 10+ (API 29)

---

## Instalación y ejecución

1. Clonar el repositorio:

```bash
git clone https://github.com/ereinosov/App4.git
```

2. Abrir en Android Studio: **File → Open → seleccionar carpeta `App4`**
3. Agregar credenciales en `local.properties`
4. Sincronizar Gradle: **File → Sync Project with Gradle Files**
5. Ejecutar en dispositivo: **Run → Run 'app'**

---

## Repositorio

https://github.com/ereinosov/App4
