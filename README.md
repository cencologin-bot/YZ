# YZ Music — Android only

Copia del proyecto YZ-Music con solo la app Android.

Módulos: `:app` (aplicación), `:shared` (lógica KMP), `:sharedUi` (UI Compose),
`native/` (C++ del analizador, usado por CMake) y `wearos/protocol` (protocolo
teléfono-reloj, compilado como fuente dentro de `:app`).

## Requisitos
- Android Studio reciente, JDK 17
- SDK Android 37 (compileSdk), NDK + CMake 3.22.1
- Conexión a internet (JitPack para NewPipeExtractor / InnerTubeX)

## Compilar
    ./gradlew assembleDevDebug     # com.dev.yzmusic
    ./gradlew assembleProdDebug    # com.music.yzmusic

APK en `app/build/outputs/apk/`.

## Opcional (local.properties)
    LASTFM_API_KEY=...
    LASTFM_SECRET=...
    LISTEN_TOGETHER_SERVER=https://tu-servidor

## Rendimiento por tipo de dispositivo

`ui/performance/DeviceTier.kt` clasifica el equipo (LOW / MID / HIGH) por RAM,
núcleos, versión de Android y `MEDIA_PERFORMANCE_CLASS`. Solo decide los valores
**por defecto** de los efectos para quien no los ha cambiado en Ajustes:

| Efecto                          | LOW  | MID  | HIGH |
|---------------------------------|------|------|------|
| Liquid glass (barra flotante)   | off  | off  | on   |
| Blur dinámico (barras, mini)    | off  | off  | on   |
| Blur de letras no activas       | off  | off  | on   |
| Canvas animado (video)          | off  | off  | on   |
| Congelar degradado / snap tabs  | on   | off  | off  |
| Caché de portadas (memoria)     | 10 % | 15 % | 20 % |
| Fundido de portadas             | 80ms | 120ms| 200ms|

Los iconos y el layout son idénticos en todos los niveles. Lo que el usuario ya
eligió en Ajustes siempre tiene prioridad.

Para medir fluidez usa un build `release` (o `benchmark`), no `debug`.

## Compilar en GitHub

El workflow está en `.github/workflows/android.yml`.

1. Sube el proyecto a tu repositorio:

        git init && git add . && git commit -m "YZ Music Android"
        git branch -M main
        git remote add origin https://github.com/<usuario>/<repo>.git
        git push -u origin main

2. Entra en **Actions → Android APK**. Se ejecuta solo en cada push a `main`
   y a mano con **Run workflow** (`prodRelease`, `devDebug` o `both`).
3. Descarga el APK en **Artifacts** al terminar (≈15–25 min la primera vez;
   las siguientes usan caché).

Sin secretos configurados compila igual, y el release sale firmado con la clave
debug (instalable para pruebas). Para firmarlo con tu clave, en
**Settings → Secrets and variables → Actions** crea:

| Secreto             | Contenido                                   |
|---------------------|---------------------------------------------|
| `KEYSTORE_BASE64`   | `base64 -w0 yzmusic-release.jks`            |
| `KEYSTORE_PASSWORD` | contraseña del keystore                     |
| `KEY_ALIAS`         | alias de la clave                           |
| `KEY_PASSWORD`      | contraseña de la clave (opcional)           |
| `LASTFM_API_KEY` / `LASTFM_SECRET` | opcionales                   |
| `LISTEN_TOGETHER_SERVER` | opcional, URL de tu servidor           |

Al crear un tag `vX.Y.Z` el workflow además publica el APK como GitHub Release.
Conserva siempre el mismo keystore: Android no deja actualizar una app firmada
con otra clave.
