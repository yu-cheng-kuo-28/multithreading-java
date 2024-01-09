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
    private boolean unprocessedData = false; // Indicates if there is unprocessed data
    private int count; // The total number of votes counted by the assistant
    private String name; // The staff number of the assistant

    public synchronized void reportCount(String name, int count) {
        while (unprocessedData) {
            try {
                wait(); // Wait for the unprocessed data to be processed
            } catch (InterruptedException e) {}
        }

        System.out.println(name + " polling station reports " + count + " votes");
        this.count = count;
        this.name = name;
        unprocessedData = true; // Set the flag indicating there is new unprocessed data
        notify(); // Notify waiting threads that new data is available
        // notifyAll(); // Notify waiting threads that new data is available
    }


    public synchronized int getCount() {
        while (!unprocessedData) {
            try {
                wait(); // Wait for new data to become available
            } catch (InterruptedException e) {}
        }
        int value = count;
        unprocessedData = false; // Reset the flag as data has been processed
        notify(); // Notify any waiting threads that data has been processed
        // notifyAll(); // Notify any waiting threads that data has been processed
        return value;
    }
}

class InterthreadCommunication_02 {
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

        System.out.printf("\n");

        // Sum total votes safely using synchronization
        synchronized (a) {
            for (int i = 0; i < numOfStations * PollingStation.reportTimes; i++) {
                total += a.getCount(); // Number of votes from each polling station
                System.out.println("Current total votes: " + total);
            }
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
