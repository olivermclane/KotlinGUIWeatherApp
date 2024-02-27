import java.io.File
import java.io.FileWriter
import java.io.IOException
import javax.swing.JOptionPane
/**
 * Utility class for exporting weather data to a CSV file.
 */
class WeatherExport {

    /**
     * Companion object containing the static method for exporting weather data to CSV.
     */
    companion object {

        /**
         * Exports weather data to a CSV file.
         *
         * @param weatherInfo The weather data to be exported.
         */
        @JvmStatic
        fun exportWeatherDataToCSV(weatherInfo: String) {
            // Get the path to the user's Downloads directory
            val downloadsDirPath = System.getProperty("user.home") + File.separator + "Downloads"
            val downloadsDir = File(downloadsDirPath)

            // Check if Downloads directory exists
            if (!downloadsDir.exists()) {
                // Display error message if Downloads directory does not exist
                JOptionPane.showMessageDialog(
                    null,
                    "Downloads directory not found. Unable to export file.",
                    "Export Error",
                    JOptionPane.ERROR_MESSAGE
                )
                return
            }

            // Create the output file in the Downloads directory
            val outputFile = File(downloadsDir, "weather_data.csv")

            try {
                // Write weather data to the output file
                FileWriter(outputFile).use { writer ->
                    writer.write(weatherInfo)
                }
                // Display success message upon successful export
                JOptionPane.showMessageDialog(
                    null,
                    "Weather data exported to ${outputFile.absolutePath}",
                    "Export Successful",
                    JOptionPane.INFORMATION_MESSAGE
                )
            } catch (ex: IOException) {
                // Display error message if an exception occurs during export
                JOptionPane.showMessageDialog(
                    null,
                    "Error exporting weather data: ${ex.message}",
                    "Export Error",
                    JOptionPane.ERROR_MESSAGE
                )
            }
        }
    }
}
