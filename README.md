# WeatherApp

## Project Overview

**WeatherApp** is a Java Spring Boot web application that fetches and displays the current weather for any city using the Open-Meteo API. The app provides a web interface for users to select up to 5 cities, retrieves their geographic coordinates, and then queries for real-time weather data, presenting the temperature and a human-readable weather description.

---

## Installation Instructions

1. **Clone or Download the Repository**
   ```sh
   git clone <your-repo-url>
   cd weather-app
   ```

2. **Build the Application with Maven**
   - Make sure you have Java 21+ and Maven installed.
   - Build the project:
     ```sh
     mvn clean package
     ```

---

## Usage Guide

1. **Run the Application**
   ```sh
   java -jar target/weather-app-1.0.0.jar
   ```
   Or, for development:
   ```sh
   mvn spring-boot:run
   ```

2. **Access the Web Interface**
   - Open your browser and go to: [http://localhost:8080/](http://localhost:8080/)
   - Select 1 to 5 cities, enter their names, and submit to view the weather forecast.

---

## Example Output

```
+-----------+---------------------+-------------------+
|   City    | Temperature (°C)    |   Description     |
+-----------+---------------------+-------------------+
| Singapore | 31.2                | Mainly clear      |
| London    | 18.5                | Cloudy            |
| Paris     | 20.1                | Partly cloudy     |
+-----------+---------------------+-------------------+
```

---

## Features

- User-friendly web interface for selecting and entering city names.
- Fetches real-time weather data for up to 5 cities using Open-Meteo APIs.
- Maps weather codes to human-readable descriptions (e.g., "Mainly clear", "Thunderstorm").
- Handles invalid city names, missing data, and network errors gracefully.
- Returns output as a JSON string for each city and displays results in a responsive table.
- Modular code structure with helper methods for API requests, validation, and JSON parsing.
- Easily extensible for more weather parameters or additional features.

---

## Error Handling

- **Invalid or empty city name:** Returns `{"error": "City not found."}` or `{"error": "Invalid city name."}`
- **City not found in API:** Returns `{"error": "City not found."}`
- **Network or parsing errors:** Returns `{"error": "Network or parsing error."}` or `{"error": "Invalid response format."}`
- **Missing weather data:** Returns `{"error": "Weather data not found."}`
- **Unknown weather codes:** Returns `"description": "Unknown"` in the output.
- **Too many cities:** If more than 5 cities are entered, returns an error message.

---

## API Information

- **Geocoding:** [Open-Meteo Geocoding API](https://open-meteo.com/en/docs/geocoding-api)
  - Used to convert city names to latitude and longitude.
- **Weather:** [Open-Meteo Weather API](https://open-meteo.com/en/docs)
  - Used to fetch current weather data based on coordinates.

---

## Licensing and Third-Party Libraries

This project uses the following libraries and APIs:

- **Spring Boot** ([Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0))
- **Gson** ([Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0))
- **Mockito** ([MIT License](https://opensource.org/licenses/MIT))
- **JUnit** ([Eclipse Public License 2.0](https://www.eclipse.org/legal/epl-2.0/))
- **Open-Meteo API** ([Terms of Service](https://open-meteo.com/en/terms))

You may use this project in open source and commercial projects, provided you comply with the licenses above and the Open-Meteo API terms.

**Attribution:**  
This app uses the Open-Meteo API ([https://open-meteo.com/](https://open-meteo.com/)). Please review their [terms of service](https://open-meteo.com/en/terms) for usage and attribution requirements.

---

## Security and Compliance

- All user input is validated on the backend.
- Only HTTPS is used for API calls.
- No secrets or API keys are stored in the source code.
- Error messages do not expose internal details.
- All third-party libraries are open source and compatible with commercial use.
- See the `LICENSE` or `NOTICE` file for third-party license details.

---

```markdown
# WeatherApp

## Project Overview

**WeatherApp** is a Java Spring Boot web application that fetches and displays the current weather for any city using the Open-Meteo API. The app provides a web interface for users to select up to 5 cities, retrieves their geographic coordinates, and then queries for real-time weather data, presenting the temperature and a human-readable weather description.

---

## Installation Instructions

1. **Clone or Download the Repository**
   ```sh
   git clone <your-repo-url>
   cd weather-app
   ```

2. **Build the Application with Maven**
   - Make sure you have Java 21+ and Maven installed.
   - Build the project:
     ```sh
     mvn clean package
     ```

---

## Usage Guide

1. **Run the Application**
   ```sh
   java -jar target/weather-app-1.0.0.jar
   ```
   Or, for development:
   ```sh
   mvn spring-boot:run
   ```

2. **Access the Web Interface**
   - Open your browser and go to: [http://localhost:8080/](http://localhost:8080/)
   - Select 1 to 5 cities, enter their names, and submit to view the weather forecast.

---

## Example Output

```
+-----------+---------------------+-------------------+
|   City    | Temperature (°C)    |   Description     |
+-----------+---------------------+-------------------+
| Singapore | 31.2                | Mainly clear      |
| London    | 18.5                | Cloudy            |
| Paris     | 20.1                | Partly cloudy     |
+-----------+---------------------+-------------------+
```

---

## Features

- User-friendly web interface for selecting and entering city names.
- Fetches real-time weather data for up to 5 cities using Open-Meteo APIs.
- Maps weather codes to human-readable descriptions (e.g., "Mainly clear", "Thunderstorm").
- Handles invalid city names, missing data, and network errors gracefully.
- Returns output as a JSON string for each city and displays results in a responsive table.
- Modular code structure with helper methods for API requests, validation, and JSON parsing.
- Easily extensible for more weather parameters or additional features.

---

## Error Handling

- **Invalid or empty city name:** Returns `{"error": "City not found."}` or `{"error": "Invalid city name."}`
- **City not found in API:** Returns `{"error": "City not found."}`
- **Network or parsing errors:** Returns `{"error": "Network or parsing error."}` or `{"error": "Invalid response format."}`
- **Missing weather data:** Returns `{"error": "Weather data not found."}`
- **Unknown weather codes:** Returns `"description": "Unknown"` in the output.
- **Too many cities:** If more than 5 cities are entered, returns an error message.

---

## API Information

- **Geocoding:** [Open-Meteo Geocoding API](https://open-meteo.com/en/docs/geocoding-api)
  - Used to convert city names to latitude and longitude.
- **Weather:** [Open-Meteo Weather API](https://open-meteo.com/en/docs)
  - Used to fetch current weather data based on coordinates.

---

## Licensing and Third-Party Libraries

This project uses the following libraries and APIs:

- **Spring Boot** ([Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0))
- **Gson** ([Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0))
- **Mockito** ([MIT License](https://opensource.org/licenses/MIT))
- **JUnit** ([Eclipse Public License 2.0](https://www.eclipse.org/legal/epl-2.0/))
- **Open-Meteo API** ([Terms of Service](https://open-meteo.com/en/terms))

You may use this project in open source and commercial projects, provided you comply with the licenses above and the Open-Meteo API terms.

**Attribution:**  
This app uses the Open-Meteo API ([https://open-meteo.com/](https://open-meteo.com/)). Please review their [terms of service](https://open-meteo.com/en/terms) for usage and attribution requirements.

---

## Security and Compliance

- All user input is validated on the backend.
- Only HTTPS is used for API calls.
- No secrets or API keys are stored in the source code.
- Error messages do not expose internal details.
- All third-party libraries are open source and compatible with commercial use.
- See the `LICENSE` or `NOTICE` file for third-party license details.

---

## Development Tools and AI Assistance

This project was developed using [Qodo Gen](https://qodo.co/) and [GitHub Copilot](https://github.com/features/copilot) for AI-assisted code generation and productivity.

- **Qodo Gen** and **GitHub Copilot** are AI-powered tools that help accelerate software development by providing code suggestions, refactoring, and documentation assistance.
- Please review the [GitHub Copilot Terms of Service](https://github.com/github/copilot/blob/main/docs/terms.md) and [Qodo Gen Terms](https://qodo.co/terms) for licensing and responsible use.

---

## Future Improvements

- Add support for multi-day forecasts.
- Enhance error messages with more specific details.
- Support for additional weather parameters (humidity, wind, etc.).
- Add localization and support for multiple languages.
- Improve unit test coverage for edge cases and concurrency.

---

**Enjoy using WeatherApp!**


## Future Improvements

- Add support for multi-day forecasts.
- Enhance error messages with more specific details.
- Support for additional weather parameters (humidity, wind, etc.).
- Add localization and support for multiple languages.
- Improve unit test coverage for edge cases and concurrency.

---

**Enjoy using WeatherApp!**