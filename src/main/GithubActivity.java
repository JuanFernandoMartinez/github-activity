package main;


import main.service.ActivityService;

public class GithubActivity {
    public static void main(String[] args) {

        ActivityService service = new ActivityService();

        var  events  = service.getEventsForUser(args[0]);
        events.forEach(x -> {
            System.out.printf("%s to %s \n", x.type(), x.repo().name());
        });
    }

}