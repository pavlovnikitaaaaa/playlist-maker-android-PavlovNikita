# Playlist Maker

Android-приложение для поиска музыкальных треков, ведения списка избранного и
создания собственных плейлистов.

## Возможности

- Поиск треков (с историей последних запросов).
- Экран деталей трека: добавление в избранное и в плейлист.
- Создание плейлистов с обложкой, названием и описанием.
- Просмотр плейлиста со списком треков; удаление трека долгим нажатием.
- Экран «Избранное» с удалением долгим нажатием.
- Настройки: смена темы, поделиться приложением, написать в поддержку,
  пользовательское соглашение.

## Архитектура

Проект разделён на слои **Data / Domain / UI**:

- **Domain** — модели (`Track`, `Playlist`) и интерфейсы репозиториев
  (`TracksRepository`, `FavoritesRepository`, `PlaylistsRepository`, `NetworkClient`).
- **Data** — Room (`AppDatabase`, entities, DAO), DataStore (история поиска),
  эмулятор сервера (`Storage`) и реализации репозиториев.
- **UI** — экраны на Jetpack Compose, `ViewModel` для каждого экрана,
  навигация через Navigation Compose (`PlaylistHost`).

## Технологии

- Jetpack Compose, Navigation Compose
- Room (хранение треков и плейлистов)
- DataStore Preferences (история поиска)
- Coil (загрузка изображений)
- Kotlin Coroutines / Flow

## Версии

| Инструмент | Версия |
|------------|--------|
| Kotlin | 2.0.21 |
| Android Gradle Plugin | 8.13.2 |
| compileSdk / targetSdk | 36 |
| minSdk | 29 |
| JDK | 11 |
| Android Studio | совместимая с AGP 8.13.2 (Meerkat / 2024.3.1+) |

## Сборка и запуск

1. Склонируйте репозиторий и откройте проект в Android Studio.
2. Дождитесь завершения **Gradle Sync** (на первой сборке KSP сгенерирует код Room).
3. Соберите проект: `Build → Make Project` или из терминала:
   ```bash
   ./gradlew assembleDebug
   ```
4. Запустите на эмуляторе или устройстве (Android 10 / API 29 и выше):
   `Run → Run 'app'`.
