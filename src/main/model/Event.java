package main.model;

public record Event(
    long id,
    String type,
    Repo repo
) {}
