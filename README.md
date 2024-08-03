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


## Usage
<img width="1440" alt="Screenshot 2024-08-02 at 11 49 20 PM" src="https://github.com/user-attachments/assets/a6751c1f-c846-4824-a13e-337855f391ba">

<br>
<br>

<div align="center">
   <h2>Options</h2>
   <br>
    <table >
     <tr>
        <td><b>Dining Hall</b></td>
        <td><b>Meal Times</b></td>
     </tr>
     <tr>
       <td>- South Campus<br>
- Yhentamitsi<br>
- 251 North<br></td>
        <td>- Breakfast<br>
- Lunch<br>
- Dinner<br>
- All Meals</td>
     </tr>
    </table>
    </div>
<br>
<br>



<img width="1440" alt="Screenshot 2024-08-03 at 12 08 58 AM" src="https://github.com/user-attachments/assets/34006cff-3456-4a2f-9f08-7656d764b742">
<img width="1440" alt="Screenshot 2024-08-03 at 12 17 39 AM" src="https://github.com/user-attachments/assets/442824cb-2c6a-492a-be36-ac636dd7fc25">
<img width="1440" alt="Screenshot 2024-08-03 at 12 18 22 AM" src="https://github.com/user-attachments/assets/4b5c3ff3-132e-4db3-8698-ab6bfc9a4f64">
