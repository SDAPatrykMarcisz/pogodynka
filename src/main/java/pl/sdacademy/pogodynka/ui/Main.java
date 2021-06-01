package pl.sdacademy.pogodynka.ui;

import pl.sdacademy.pogodynka.exceptions.WeatherNotFoundException;
import pl.sdacademy.pogodynka.service.WeatherService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws WeatherNotFoundException {

        WeatherService weatherService = new WeatherService();
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("Podaj nazwę miasta: ");
            String city = scanner.nextLine();
            if(city.equals("exit")){
                break;
            }
            System.out.println(weatherService.widgetText(city) + "\n\n");
        }
    }
}
