# 🌤️ WeatherAPP

**WeatherAPP** — bu Android mobil qurilmalari uchun Kotlin tilida yozilgan ob-havo ilovasi bo‘lib, foydalanuvchining joylashuviga yoki tanlangan shahar nomiga qarab real vaqtdagi ob-havo ma'lumotlarini taqdim etadi va offline holda foydalanish uchunt xotirada saqlaydi.

---

## 📱 Asosiy imkoniyatlar

- 🔍 Shahar bo‘yicha ob-havo qidiruvi
- 📍 Hozirgi joylashuvdan foydalanish
- 🌡️ Harorat, bosim, namlik, shamol tezligi
- ☀️ Ob-havo ikonkalari va yangilanish funksiyasi
- 🧭 Android permission (joylashuv uchun)

---

## 🛠 Texnologiyalar va kutubxonalar

- **Kotlin**
- **MVVM arxitekturasi**
- **ViewModel**, **Flow**
- **View Binding**
- **Retrofit + Gson** — API orqali ob-havo ma’lumotlarini olish
- **OpenWeatherMap API**
- **FusedLocationProviderClient** — joylashuvni aniqlash uchun
- **Material Design**
- **Room**
- **Dagger Hilt**

---

## 📦 O‘rnatish (Development uchun)

1. Repository’ni klon qiling:
   ```bash
   git clone https://github.com/mrsolijon/WeatherAPP.git
2. Android studioda oching
3. local.properties fayliga quyidagicha o‘zgartirish kiriting (API kalit o‘zingizniki bo‘lishi kerak):
   OPENWEATHERMAP_API_KEY=your_api_key_here
   
4.Telefoningizda yoki emulatorda ishga tushiring.
