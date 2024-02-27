/**
 * This is the GUI based application for querying the OpenWeatherMapAPI. This application provides visual weather data and
 * icon for current weather conditions.
 *
 * @author Oliver McLane
 */
import java.awt.*
import java.net.URL
import javax.swing.*
import kotlin.system.exitProcess

fun main() {
    showIntroduction()
}

/**
 * Basic intro panel that address instructions and application use.
 */
fun showIntroduction() {
    // Create a JFrame for the introduction screen
    val introFrame = JFrame("Welcome to Kotlin Weather App")
    introFrame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    introFrame.layout = BorderLayout()

    // Create a panel for the introduction text
    val introPanel = JPanel()
    introPanel.layout = BoxLayout(introPanel, BoxLayout.Y_AXIS)
    introPanel.border = BorderFactory.createEmptyBorder(20, 20, 20, 20)

    // Create introduction text
    val titleLabel = JLabel("Kotlin Weather App")
    titleLabel.font = Font("Arial", Font.BOLD, 24)
    titleLabel.alignmentX = Component.CENTER_ALIGNMENT
    //Messing with in-line html on JLabels (this is cool)
    val descriptionLabel = JLabel("<html>This application retrieves current weather conditions<br>from OpenWeatherMap API.</html>")
    descriptionLabel.alignmentX = Component.CENTER_ALIGNMENT
    val instructionLabel = JLabel("<html>Please enter the city, state, and country code to get started. <br>To quit the application simply press Quit at the bottom</html>")
    instructionLabel.alignmentX = Component.CENTER_ALIGNMENT

    // Add components to the introduction panel
    introPanel.add(titleLabel)
    introPanel.add(Box.createRigidArea(Dimension(0, 10)))
    introPanel.add(descriptionLabel)
    introPanel.add(Box.createRigidArea(Dimension(0, 10)))
    introPanel.add(instructionLabel)

    // Create a button to start the main application
    val startButton = JButton("Start")
    startButton.alignmentX = Component.CENTER_ALIGNMENT
    startButton.addActionListener {
        introFrame.isVisible = false
        // Call the main function to start the application
        startMainApplication()
    }

    // Add components to the introduction frame
    introFrame.add(introPanel, BorderLayout.CENTER)
    introFrame.add(startButton, BorderLayout.SOUTH)

    // Set frame properties
    introFrame.pack()
    introFrame.setLocationRelativeTo(null) // Center the frame on the screen
    introFrame.isVisible = true
}

/**
 * Starting main application GUI, separate from instructions panel.
 */
fun startMainApplication() {
    // Create a JFrame
    val frame = JFrame("Kotlin Weather App")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.layout = BorderLayout()
    frame.preferredSize = Dimension(600, 400)
    frame.isResizable = false

    // Create labels, text fields, and buttons
    val cityLabel = JLabel("City:")
    val cityField = JTextField(20)
    val stateLabel = JLabel("State:")
    val stateField = JTextField(20)
    val countryLabel = JLabel("Country Code:")
    val countryField = JTextField(20)
    val queryButton = JButton("Query Current Conditions")
    val displayArea = JTextArea(10, 30)

    // Current Conditions Icon
    val weatherIconLabel = JLabel()
    val weatherIconPanel = JPanel()
    val SkyBlue = Color(135, 206, 235)
    weatherIconPanel.background = SkyBlue // Set desired background color
    weatherIconPanel.add(weatherIconLabel)




    val close = JButton("Quit");


    displayArea.isEditable = false

    // Add components to the frame
    val inputPanel = JPanel(GridBagLayout())
    val gbc = GridBagConstraints()
    gbc.gridx = 0
    gbc.gridy = 0
    gbc.insets = Insets(5, 5, 5, 5)
    inputPanel.add(cityLabel, gbc)
    gbc.gridx = 1
    inputPanel.add(cityField, gbc)
    gbc.gridx = 0
    gbc.gridy = 1
    inputPanel.add(stateLabel, gbc)
    gbc.gridx = 1
    inputPanel.add(stateField, gbc)
    gbc.gridx = 0
    gbc.gridy = 2
    inputPanel.add(countryLabel, gbc)
    gbc.gridx = 1
    inputPanel.add(countryField, gbc)
    gbc.gridx = 0
    gbc.gridy = 3
    gbc.gridwidth = 2
    inputPanel.add(queryButton, gbc)

    frame.add(inputPanel, BorderLayout.NORTH)
    frame.add(JScrollPane(displayArea), BorderLayout.CENTER)
    frame.add(close, BorderLayout.AFTER_LAST_LINE)
    frame.add(weatherIconPanel, BorderLayout.WEST) // Add weather icon label


    // Add action listener to close button
    close.addActionListener{
        exitProcess(0)
    }

    // Add action listener to the query button
    queryButton.addActionListener {
        val city    = cityField.text
        val state = stateField.text
        val country = countryField.text
        //Check if City or Country are empty fields as they are required
        if (city.trim().isEmpty() || country.trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                null,
                "Please provide the city and country code.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            // Check that country code is a valid ISO 3166-1 alpha-2 format
        } else if (!isValidCountryCode(country)) {
            JOptionPane.showMessageDialog(
                null,
                "Please provide a valid country code (e.g., ISO 3166-1 alpha-2 format US, UK, etc).",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            //Validate the city doesn't contain numerics
        } else if (!isValidCityName(city)) {
            JOptionPane.showMessageDialog(
                null,
                "Please provide a valid city name without special characters or numbers.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            //Continue if validation is met
        } else {
            val userInput = UserInput(city, state, country)
            val weatherData = getWeatherData(userInput)
            if (weatherData != null) {
                displayWeatherData(weatherData, displayArea, weatherIconLabel)
            }
            stateField.text = ""
            cityField.text = ""
            countryField.text = ""
        }
    }

    // Set frame properties
    frame.pack()
    frame.isVisible = true
}

/**
 * Displays weather data obtained from the OpenWeatherMap API.
 *
 * @param weatherData The weather data to be displayed.
 * @param displayArea The JTextArea to display the weather data.
 */
fun displayWeatherData(weatherData: WeatherData, displayArea: JTextArea, weatherIconLabel: JLabel) {
    val formattedWeatherInfo = buildString {
        appendLine("Weather Report for: ${weatherData.city}")
        appendLine("Temperature Low: ${weatherData.lowTemp} Fahrenheit")
        appendLine("Temperature High: ${weatherData.highTemp} Fahrenheit")
        appendLine("Temperature: ${weatherData.temperature} Fahrenheit")
        appendLine("Feels Like: ${weatherData.feltTemp} Fahrenheit")
        appendLine("Weather: ${weatherData.weatherDescription}")
        appendLine("Humidity: ${weatherData.humidity}%")
        appendLine("Wind Speed: ${weatherData.windSpeed} mph")
    }

    displayArea.text = formattedWeatherInfo

    val iconUrl = getIconUrl(weatherData.weatherCode) // Assuming it's day
    weatherIconLabel.icon = ImageIcon(iconUrl)
}

/**
 *  Gather icon for current conditions from the conditions status code
 *
 *  @param weatherCode Current conditions code from weatherAPI
 *
 */
fun getIconUrl(weatherCode: String): URL {
    // Construct the URL based on the weather code
    val iconUrl = "https://openweathermap.org/img/wn/${weatherCode}@2x.png"
    return URL(iconUrl)

}

/**
 * Validate the country code against the ISO 3166-1 alpha-2 country codes standards
 *
 * @param countryCode the queried country code
 */
fun isValidCountryCode(countryCode: String): Boolean {
    // Regular expression for ISO 3166-1 alpha-2 country codes
    val countryCodePattern = "^[A-Za-z]{2}$"
    return countryCode.matches(countryCodePattern.toRegex())
}

/**
 * Validates the city name doesn't contain numerics.
 *
 * @param cityName the queried city name
 */
fun isValidCityName(cityName: String): Boolean {
    // Regular expression to allow alphabetic characters and whitespace
    val cityNamePattern = "^[A-Za-z\\s]+$"
    return cityName.matches(cityNamePattern.toRegex())
}
