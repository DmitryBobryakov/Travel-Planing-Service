package org.example;

public class Trip {
    private String name;
    private String country;
    private String dates;
    private String participants;

    public Trip(String name, String country, String dates, String participants) {
        this.name = name;
        this.country = country;
        this.dates = dates;
        this.participants = participants;
    }

    // Геттеры
    public String getName() { return name; }
    public String getCountry() { return country; }
    public String getDates() { return dates; }
    public String getParticipants() { return participants; }
}
