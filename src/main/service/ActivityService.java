package main.service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import main.model.Event;
import main.network.RestClient;

public class ActivityService {

    private final RestClient restClient = new RestClient();
    private final String BASE_URL = "https://api.github.com/users/%s/events";
    
    public List<Event> getEventsForUser(String username){
        try {
            List<Event> events = restClient.getGithubActivity(String.format(BASE_URL, username ));
            return events;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
