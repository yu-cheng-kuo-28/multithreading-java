public class VoteCounter_02 {
    private int votes = 0;

    public synchronized void castVote() { // Synchronized method
        int currentVotes = this.votes;
        // Simulate delay
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.votes = currentVotes + 1;
    }

    public int getVotes() {
        return votes;
    }

    public static void main(String[] args) throws InterruptedException {
        final VoteCounter_02 counter = new VoteCounter_02();

        Thread voter1 = new Thread(counter::castVote);
        Thread voter2 = new Thread(counter::castVote);

        voter1.start();
        voter2.start();

        voter1.join();
        voter2.join();

        System.out.println("Total votes: " + counter.getVotes());
    }
}
