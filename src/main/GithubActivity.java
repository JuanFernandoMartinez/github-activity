package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import main.model.Event;

public class GithubActivity {
    public static void main(String[] args) throws IOException {

        URL url = new URL(String.format("https://api.github.com/users/%s/events", args[0]));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));

        String inputLine;

        StringBuffer content = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();

        Gson gson = new Gson();

        Type eventListType = new TypeToken<List<Event>>() {
        }.getType();
        List<Event> events = gson.fromJson(content.toString(), eventListType);

        events.forEach(x -> {
            System.out.printf("%s to %s \n", x.type(), x.repo().name());
        });


        // commit example
        
        // String path = System.getProperty("user.home")+ File.separator +
        // ".githubActivity" + File.separator + "data" + File.separator +
        // "activity.json";
        String outputPath = String.format("data\\%s.json", args[0]);
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
        writer.write(content.toString());
        writer.close();

    }

}