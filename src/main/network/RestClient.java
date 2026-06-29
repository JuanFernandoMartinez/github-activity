package main.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import main.model.Event;

public class RestClient {

    public  List<Event> getGithubActivity(String url) throws IOException {
        URL netUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) netUrl.openConnection();
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
        return  gson.fromJson(content.toString(), eventListType);
    }

}
