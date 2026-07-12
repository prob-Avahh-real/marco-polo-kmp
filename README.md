# Marco Polo Moto — KMP

A motorcycle travel log / journey tracking app built with Kotlin Multiplatform + Compose Multiplatform.

Track rides, log waypoints, calculate distances using the Haversine formula, estimate fuel costs and travel times.

## Architecture

```
model/          → Journey, Waypoint, Achievement
engine/         → JourneyEngine (Haversine distance, stats, fuel cost, search)
data/           → IJourneyRepository + InMemoryJourneyRepository
composeApp/     → JourneyScreen (stats banner, journey cards, empty state)
```

## Features
- Journey tracking with distance, waypoints, photos
- Haversine distance calculation
- Fuel cost estimation
- Travel time estimation
- Journey search and filtering

## Build

```bash
./gradlew :shared:allTests
./gradlew :composeApp:assembleRelease
./gradlew :shared:linkDebugFrameworkIosArm64
```

## Tech Stack
- Kotlin 2.1.10, Compose Multiplatform 1.7.3
- Material3, kotlinx-coroutines, kotlinx-serialization
