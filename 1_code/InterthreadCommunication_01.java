class PollingStation extends Thread { // Report votes
    static int reportTimes = 5; // Number of reports
    int total = 0; // Total votes obtained by this polling station
    Assistant a; // Assistant of this polling station
    String name; // Name of the polling station

    public PollingStation(Assistant a, String name) { // Constructor
        this.a = a; // Declare Assistant object
        this.name = name; // Declare the name of the polling station
    }

    public void run() { // The work of reporting votes
        for (int i = 0; i < reportTimes; i++) { // Report the number of votes 5 times
            // Randomly generate the number of votes obtained this time
            int count = (int)(Math.random() * 500);
            a.reportCount(name, count); // Report the number of votes to the assistant object
            total += count; // Obtained by this polling station
        }
    }
}

class Assistant {
    private int count; // Total number of votes of the assistant
    private String name; // Employee number of the assistant

    public synchronized void reportCount(String name, int count) {
        System.out.println(name + " polling station reports " + count + " votes");
        this.count = count;
        this.name = name;
    }

    public synchronized int getCount() {
        return count;
    }
}

public class InterthreadCommunication_01 {
    static int total = 0; // Total votes
    static int numOfStations = 2; // Number of polling stations
    static PollingStation[] stations;

    public static void main(String[] argv) {
        // Establish assistant object
        Assistant a = new Assistant();

        // Establish polling stations
        stations = new PollingStation[numOfStations];

        // One by one, establish polling station objects and provide
        for (int i = 1; i <= numOfStations; i++) {
            stations[i - 1] = new PollingStation(a, "Candidate " + i);
        }

        // One by one, start the threads
        for (int i = 0; i < numOfStations; i++) {
            stations[i].start();
        }

        // Wait for all polling stations to complete voting
        for (int i = 0; i < numOfStations; i++) {
            try {
                stations[i].join();
            } catch (InterruptedException e) {
                // e.printStackTrace();
            }
        }

        System.out.printf("\n");

        // Sum total votes
        for (int i = 0; i < numOfStations * PollingStation.reportTimes; i++) {
            total += a.getCount(); // Number of votes from each polling station
            System.out.println("Current total votes: " + total);
        }

        System.out.printf("\n");

        // One by one, report the number of votes of each polling station
        for (int i = 0; i < numOfStations; i++) {
            System.out.println("Total votes of " + stations[i].name + ": " + stations[i].total);
        }

        System.out.printf("\n");

        System.out.println("Final total votes: " + total);
    }
}
