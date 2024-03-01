import okhttp3.OkHttpClient
import okhttp3.HttpUrl
import okhttp3.Request
import java.io.IOException
import com.google.gson.Gson
import com.google.gson.JsonObject
import javax.swing.JOptionPane

// Initialize Gson and OkHttpClient instances
private val gson = Gson()
private val client = OkHttpClient()

// API key for accessing the OpenWeatherMap API
private const val OPENWEATHER_API_KEY = ""

/**
 * Retrieves weather data from the OpenWeatherMap API based on the user input.
 *
 * @param userInput The user input containing city, state, and country information.
 * @return weather.WeatherData containing the weather information.
 */
fun getWeatherData(userInput: UserInput): WeatherData? {
    // Build URL for the API request
    val url = buildUrl(userInput)

    // Build HTTP request
    val request = Request.Builder()
        .url(url)
        .build()

    try {
        // Execute the HTTP request
        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()

        // Handle response based on HTTP status code
        return when (response.code) {
            200 -> parseWeatherData(responseBody)
            404 -> {
                JOptionPane.showMessageDialog(
                    null,
                    "Invalid query: City (${userInput.city}), state (${userInput.state}), or country (${userInput.country}) is incorrect. Please try again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                )
                null
            }
            401 -> {
                JOptionPane.showMessageDialog(
                    null,
                    "Invalid API Key: Check config files. Please try again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                )
                null
            }
            else -> {
                JOptionPane.showMessageDialog(
                    null,
                    "Query issue: Contact Provider. Please try again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                )
                null
            }
        }
    } catch (e: IOException) {
        // Handle IO exception
        JOptionPane.showMessageDialog(
            null,
            "Error fetching weather data: ${e.message}. Please try again.",
            "Error",
            JOptionPane.ERROR_MESSAGE
        )
        return null
    }
}

/**
 * Builds the URL for the OpenWeatherMap API request based on the user input.
 *
 * @param userInput The user input containing city, state, and country information.
 * @return The built HttpUrl object representing the API request URL.
 */
private fun buildUrl(userInput: UserInput): HttpUrl {
    return HttpUrl.Builder()
        .scheme("https")
        .host("api.openweathermap.org")
        .addPathSegments("data/2.5/weather")
        .addQueryParameter("q", "${userInput.city},${userInput.state},${userInput.country}")
        .addQueryParameter("appid", OPENWEATHER_API_KEY)
        .addQueryParameter("units", "imperial")
        .build()
}

/**
 * Parses the weather data obtained from the OpenWeatherMap API response.
 *
 * @param responseBody The JSON string containing the weather data.
 * @return weather.WeatherData containing the parsed weather information.
 */
private fun parseWeatherData(responseBody: String?): WeatherData? {
    try {
        val jsonObject = gson.fromJson(responseBody, JsonObject::class.java)
        val main = jsonObject.getAsJsonObject("main")
        val weather = jsonObject.getAsJsonArray("weather").first().asJsonObject
        val wind = jsonObject.getAsJsonObject("wind")
        val sys = jsonObject.getAsJsonObject("sys")

        return WeatherData(
            temperature = main.getAsJsonPrimitive("temp").asDouble,
            highTemp = main.getAsJsonPrimitive("temp_max").asDouble,
            lowTemp = main.getAsJsonPrimitive("temp_min").asDouble,
            feltTemp = main.getAsJsonPrimitive("feels_like").asDouble,
            humidity = main.getAsJsonPrimitive("humidity").asInt,
            weatherDescription = weather.getAsJsonPrimitive("description").asString,
            windSpeed = wind.getAsJsonPrimitive("speed").asDouble,
            city = jsonObject.getAsJsonPrimitive("name").asString,
            countryCode = sys.getAsJsonPrimitive("country").asString,
            weatherCode = weather.getAsJsonPrimitive("icon").asString
        )
    } catch (e: Exception) {
        JOptionPane.showMessageDialog(
            null,
            "Error parsing weather data: ${e.message}. Please try again.",
            "Error",
            JOptionPane.ERROR_MESSAGE
        )
        return null
    }
}
