public class VoteCounter_03 {
    private int votes = 0;
    private static final Object lock = new Object(); // Mutex

    public void castVote() {
        while (true) {
            synchronized(lock) { // Start of critical section
                int currentVotes = this.votes;
                this.votes = currentVotes + 1;
                System.out.println(Thread.currentThread().getName() + " casted a vote. Total votes: " + votes);
            } // End of critical section

            // Simulate delay
            try {
                Thread.sleep(800); // Pauses for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        final VoteCounter_03 counter = new VoteCounter_03();

        Thread voter1 = new Thread(counter::castVote, "Voter 1");
        Thread voter2 = new Thread(counter::castVote, "Voter 2");

        voter1.start();
        voter2.start();
    }
}
