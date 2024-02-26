# Kotlin Weather GUI
This program allows users to query current weather conditions using the OpenWeatherMap API based on the state code, country code, and city. Providing the results in a GUI with a icon of the current conditions followed by the different weather stats.

## Prerequisites

Before getting started, ensure you have the following prerequisites installed:

- JDK (Java Development Kit)
- Kotlin
- Gradle

## Installation

### For Mac Users

#### Gradle Installation (using Homebrew)

If you don't have Homebrew installed, you can install it from [https://brew.sh/](https://brew.sh/).

```bash
brew install gradle
```
### For Windows Users
#### Gradle Installation (using Scoop)

If you don't have Scoop installed, you can install it from https://scoop.sh/.

```bash
scoop install gradle
```




## Setup
### Follow these steps to set up and run the project:

#### Clone the repository:
```bash
git clone https://github.com/olivermclane/KotlinGUIWeatherApp
```
```bash
cd KotlinGUIWeatherApp
```
```bash
gradle build
```


## Obtaining OpenWeatherAPI Key

This project uses OpenWeatherAPI to fetch weather data. To obtain an API key:

1. Sign up or log in to OpenWeatherAPI: https://home.openweathermap.org/users/sign_up
2. After logging in, navigate to your API keys section.
3. Generate a new API key if you haven't already.
4. Copy the generated API key.
5. Add the api-key to WeatherService.kt in the OPENWEATHER_API_KEY variable

## Run the project

```bash
gradle run
```
   
