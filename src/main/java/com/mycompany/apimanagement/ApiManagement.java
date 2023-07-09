package com.mycompany.apimanagement;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Scanner;

class Location {
    public String name;
    public String region;
    public String country;
}

class Current{
    public String temp_c;
    public String temp_f;
    public Condition condition;
}

class Condition{
    public String text;
}

class WeatherData{
    public Location location;
    public Current current;
}

public class ApiManagement {
    public String fetch(String city) throws URISyntaxException, IOException, InterruptedException {
           URI url = new URI("https://weatherapi-com.p.rapidapi.com/forecast.json?q="+city+"&days=3");
           HttpRequest request = HttpRequest.newBuilder()
                  .uri(url)
                  .header("X-RapidAPI-Key", "e7b4402e3fmshfdb4aa7589ea9fdp1c03cbjsna3ba8d1903cd")
                  .header("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
                  .GET()
                  .build();
           HttpClient client = HttpClient.newHttpClient();
           HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
           
           return response.body();
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ApiManagement api = new ApiManagement();
        
        System.out.println("Enter location (city):");
        String location = sc.nextLine().replace(" ", "-");
        
        System.out.println("\n\n========== Fetching weather data, please wait!.... ============");        
        try{
            String data = api.fetch(location);
            System.out.println("\n\n ================ Weather Data: ================\n");
            
            Gson gson = new Gson();
            WeatherData wd = gson.fromJson(data, WeatherData.class);

            
            //===================== Output Datas =====================  
            System.out.println("\n-----Location-----");
            System.out.println("Name: "+wd.location.name);
            System.out.println("Region: "+wd.location.region);
            System.out.println("Country: "+wd.location.country);
            
            System.out.println("\n----- Temperature -----");
            System.out.println("Temperature(C): "+wd.current.temp_c);
            System.out.println("Temperature(F): "+wd.current.temp_f);
            
            System.out.println("\n----- Condition -----");
            System.out.println("Condition: "+wd.current.condition.text);
            
            
        }catch(Exception err){
            System.out.println("Something went wrong!");
            System.out.println("Reasons:\n 1. Location may not be found.\n2.Unstable network connection.");
        }
        
        sc.close();
    }
}
