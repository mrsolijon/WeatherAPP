🌤️ WeatherAPP
WeatherAPP is an Android mobile application written in Kotlin that provides real-time weather information based on the user’s current location or a selected city name. The app also stores the latest data locally for offline use.

📱 Key Features
🔍 Search weather by city name
📍 Weather based on the user’s current location
🌡️ Real-time data: temperature, pressure, humidity, wind speed
🕐 Hourly forecast (next 24 hours)
📅 Weekly forecast (7 days)
☀️ Weather icons and animations
🔄 Refresh button to update data
🧭 Android permission handling (for location)
💾 Remembers the last selected city

🛠 Technologies & Libraries

Kotlin

MVVM Architecture

ViewModel, Flow

SharedPreferences

View Binding

Retrofit + Gson — for fetching weather data via API

OpenWeatherMap API

FusedLocationProviderClient — for location detection

Room Database

Dagger Hilt


📦 Installation (For Development)

1. Clone the repository:

git clone https://github.com/mrsolijon/WeatherAPP.git


2. Open the project in Android Studio


3. In the local.properties file, add your API key:

OPENWEATHERMAP_API_KEY=your_api_key_here


4. Run the app on your device or emulator



