import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;

public class WeatherService {

    private static final String API_KEY = "YOUR_API_KEY"; // replace with your WeatherStack API key
    private static final String API_URL = "(link unavailable)";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter city or location: ");
        String location = scanner.nextLine();
        scanner.close();

        try {
            URL url = new URL(API_URL + "?access_key=" + API_KEY + "&query=" + location);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();

                JSONObject jsonObject = new JSONObject(response.toString());
                System.out.println("Weather in " + location + ":");
                System.out.println("Temperature: " + jsonObject.getJSONObject("current").getInt("temperature") + "°C");
                System.out.println("Weather Condition: " + jsonObject.getJSONObject("current").getString("weather_descriptions"));
                System.out.println("Humidity: " + jsonObject.getJSONObject("current").getInt("humidity") + "%");
                System.out.println("Wind Speed: " + jsonObject.getJSONObject("current").getInt("wind_speed") + " km/h");
            } else {
                System.out.println("Failed to retrieve weather data");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

