
# 🌤️ WeatherAPP
**WeatherAPP** — is an Android mobile application written in Kotlin that provides real-time weather information based on the user's current location or a selected city name, and stores data for offline use.
---
## 📱 Key Features
- 🔍 Search weather by city name
- 📍 Weather based on the user's current location
- 🌡️ Real-time data including temperature, pressure, humidity, and wind speed
- 🕐 **Hourly forecast** (next 24 hour)
- 📅 **Weekly forecast** (7-days)
- ☀️ Weather icons and animations
- 🔄 Refresh button to fetch updated data
- 🧭 Android permissions (for location access)
- Remember the last searched city
---
## 🛠 Technologies & Libraries
- **Kotlin**
- **MVVM arxitekturasi**
- **ViewModel**, **Flow**
- **SharedPreferences**
- **View Binding**
- **Retrofit + Gson** — for fetching weather data via API
- **OpenWeatherMap API**
- **FusedLocationProviderClient** — for location detection
- **Room**
- **Dagger Hilt**
---
## 📦 Installation (For Development)
1. Clone the repository:
   ```bash
   git clone https://github.com/mrsolijon/WeatherAPP.git
2. Open the project in Android Studio
3. Add the following line to your local.properties file (replace with your own API key):
   OPENWEATHERMAP_API_KEY=your_api_key_here
   
4.Run the app on your device or emulator.
