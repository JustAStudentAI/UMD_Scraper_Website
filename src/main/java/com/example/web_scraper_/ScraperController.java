package com.example.web_scraper_;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.util.*;
import org.jsoup.nodes.Element;
import java.io.IOException;


/* This is a web application for my UMD dining hall web scraper.
@since Feb 8, 2024
 */

// For website 
@GetMapping("/")
public String home() {
    return "redirect:/scrape";
}


// Marks this class as a web request handler
@Controller
public class ScraperController {

    /* This method handles POST requests to the /scrape endpoint.
    Scrapes the menu off of the dynamic URL determined in the method.
    @param hall, the dining hall the user chooses
    @param meal, the meal the user chooses
    @param model, holder of the context data passed by a Controller to be displayed on a View
    @return String, contains the HTML / output to be shown to user
     */
    @PostMapping("/scrape")
    public String scrape(@RequestParam Integer hall, @RequestParam Integer meal, Model model) {

        // Get current date so the URL automatically adjusts
        Date date = new Date();

        int month = date.getMonth() + 1;
        int day = date.getDate();
        int year = date.getYear() + 1900;
        int week = date.getDay();
        boolean weekday = week != 0 && week != 6;

        // Keyword to identify meal times
        String keyword = "";
        // Lengths used to trim String of titles to print out
        final int TITLE_START = 23;
        // This variable is for counting the number of breakfast counts to discern breakfast/lunch/dinner
        int numOfOccurrences = 0;
        // String to return (contains the menu)
        String result = "";


        // Assign keyword
        keyword = switch (hall) {
            case 16 -> "Broiler Works";
            case 19 -> "Breakfast";
            case 51 -> "Smash Burger";
            default -> keyword;
        };


        // Print the meal title
        switch(meal) {
            case 1 -> result +=  "<u><div style='font-weight:bold; font-size:28px;'>Breakfast</div></u>"+ "\n";
            case 2 -> result +=  "<u><div style='font-weight:bold; font-size:28px;'>Lunch</div></u>"+ "\n";
            case 3 -> result +=  "<u><div style='font-weight:bold; font-size:28px;'>Dinner</div></u>"+ "\n";
        }


        // URL for UMD dining halls (dynamic)
        final String URL = "https://nutrition.umd.edu/?locationNum=" + hall + "&dtdate=" + month + "/" + day + "/" + year;


        try {
            // Create the document menu from the URL
            final Document document = Jsoup.connect(URL).get();

            // Get the cards (contains title of food and all items within it)
            Elements cards = document.select("div.card");


            // Each card is a section that contains a title and food items
            // We will go into each of them to retrieve info
            for(Element card : cards) {
                // Get title
                String title = card.select("h5.card-title").toString();
                title = title.substring(TITLE_START,title.length() - 5);
                // Check if current title is keyword
                if(title.equals(keyword)) {
                    // Found a title so increment
                    numOfOccurrences++;
                    // Change keyword if 251 north
                    if(hall == 51 & numOfOccurrences == 1) {
                        keyword = "Smash Deli";  // Reassign b/c it changes on the website
                        title = "Smash Deli";    // Have to do this to call printMealTime()
                    }
                }


                if(meal == 1 || (hall != 51 & meal == 2 & !weekday)) {
                    if(numOfOccurrences > 1) { // past first meal, no need to go to second meal
                        break;
                    }
                    result += title + "\n";
                } else if(meal == 2 || (hall != 51 & meal == 3 & !weekday)) {
                    if(numOfOccurrences == 2) { // currently in second meal
                        result += title + "\n";
                    } else if(numOfOccurrences > 2) { // in Dinner for weekdays now, no need to continue
                        break;
                    } else {
                        continue;  // continue if we are still in breakfast
                    }
                } else if(meal == 3){
                    if(numOfOccurrences == 3) { // In last meal
                        result += title + "\n";
                    } else {
                        continue;
                    }
                } else { // all meals
                    if(title.equals(keyword)) {
                        // Call to print which meal it is
                        result = printMealTime(weekday, numOfOccurrences, hall, result);
                    }
                    result += title + "\n";
                }

                // Goes through the food items in each card
                Elements foods = card.select("a.menu-item-name");
                for(Element food : foods) {
                    // Gets food name
                    String foodPrint = food.toString();
                    int startIndex = foodPrint.indexOf(">");
                    foodPrint = foodPrint.substring(startIndex + 1, foodPrint.length() - 4);
                    foodPrint = foodPrint.replace("&amp;", "&");
                    result +=  foodPrint + "\n";
                }
                result += "\n"; // new line
            }


        } catch (IOException e) {
            result = "Failed to scrape the website";
        }
        model.addAttribute("menu", result);
        return "menuView"; // Name of the Thymeleaf template to return (e.g., menuView.html)
    }


    /* This function is for printing out which current meal is showing when user chooses to display all
@param weekday, weather it's a weekday or not
@param numOfOccurrences, number of times keyword appeared
@param hall, which hall the user picked
@return String result, adds to what we return to user
 */
    public static String printMealTime(boolean weekday, int numOfOccurrences, int hall, String result) {
        if(hall == 51 || weekday) { // prints all meals
            switch (numOfOccurrences) {
                case 1 -> result +=  "<u><div style='font-weight:bold; font-size:28px;'>Breakfast</div></u>"+ "\n";
                case 2 -> result +=  "<u><div style='font-weight:bold; font-size:28px;'>Lunch</div></u>"+ "\n";
                case 3 -> result +=  "<u><div style='font-weight:bold; font-size:28px;'>Dinner</div></u>"+ "\n";
            }
        } else { // weekend and not 251 north
            switch (numOfOccurrences) {
                case 1:
                    result +=  "<u><div style='font-weight:bold; font-size:28px;'>Brunch</div></u>"+ "\n";
                    break;
                case 2:
                    result +=  "<u><div style='font-weight:bold; font-size:28px;'>Dinner</div></u>"+ "\n";
                    break;
            }
        }
        return result;
    }

    /* This method handles GET requests to the /scrape endpoint.
    It simply returns the name of the Thymeleaf template (scrapeForm.html)
    that contains the form allowing users to select a dining hall and meal time.
    This form submission triggers the @PostMapping method.
    @return template containing form
     */
    @GetMapping("/scrape")
    public String displayForm() {
        return "scrapeForm"; // Return a Thymeleaf template containing the form
    }

}
