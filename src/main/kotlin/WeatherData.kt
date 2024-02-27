/**
 * Represents weather data obtained from the OpenWeatherMap API.
 *
 * @property temperature The current temperature in degrees Fahrenheit.
 * @property highTemp The highest temperature in the forecast in degrees Fahrenheit.
 * @property lowTemp The lowest temperature in the forecast in degrees Fahrenheit.
 * @property feltTemp The "feels like" temperature in degrees Fahrenheit.
 * @property humidity The humidity level in percentage.
 * @property weatherDescription A description of the weather condition.
 * @property windSpeed The wind speed in miles per hour.
 * @property city The name of the city for which the weather data is obtained.
 * @property countryCode The country code of the location for which the weather data is obtained.
 * @property weatherCode The weather code associated with the current conditions used to pull conditions icon.
 */
data class WeatherData(
    val temperature: Double,
    val highTemp: Double,
    val lowTemp: Double,
    val feltTemp: Double,
    val humidity: Int,
    val weatherDescription: String,
    val windSpeed: Double,
    val city: String,
    val countryCode: String,
    val weatherCode: String
)