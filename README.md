# UMD Dining Hall Menu Web Scraper

This Spring Boot application is designed to scrape the University of Maryland (UMD) dining hall menus from the official UMD dining services website. Users can select a dining hall and a meal time to view the available menu options.

## Features

- Dynamically fetches the menu for the current day from the UMD dining services website.
- Allows users to select a dining hall and meal time.
- Displays the scraped menu information on a web page.

## Setup and Installation

Ensure you have Java JDK 11 or newer and Maven installed on your system.

1. Clone the repository to your local machine.
   ```
   git clone https://github.com/JustAStudentAI/Web_Scraper_WebApp
   ```
2. Navigate to the project directory:
   ```
   cd Web_Scraper_WebApp
   ```
3. Build the project using Maven:
   ```
   mvn clean install
   ```
4. Run the application:
   ```
   java -jar target/Web_Scraper_-0.0.1-SNAPSHOT.jar
   ```
5. Access the web interface into web browser (not terminal):
   ```
   http://localhost:8080/scrape
   ```
   

## Usage

- Access the web interface at `http://localhost:8080/scrape`.
- Use the form to select a dining hall and meal time, then submit to view the menu.

## Key Components

- `ScraperController.java`: Contains methods for handling web requests. It includes:
  - `scrape`: Handles POST requests to scrape the menu based on selected dining hall and meal time.
  - `printMealTime`: Formats meal time headings and appends them to the result string.
  - `displayForm`: Handles GET requests and shows a form for user input.
- `WebScraperApplication.java`: The main class that runs the Spring Boot application.
- `menuView.html` & `scrapeForm.html`: Thymeleaf templates for displaying the menu and the selection form, respectively.

## Technologies

- Spring Boot
- Jsoup for HTML parsing
- Thymeleaf for views
