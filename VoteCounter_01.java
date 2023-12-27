public class VoteCounter_01 {
    private int votes = 0;

    public void castVote() {
        int currentVotes = this.votes;
        // Simulate delay, which can cause race condition
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
        final VoteCounter_01 counter = new VoteCounter_01();

        // Simulating multiple users voting concurrently
        Thread voter1 = new Thread(counter::castVote);
        Thread voter2 = new Thread(counter::castVote);

        voter1.start();
        voter2.start();

        voter1.join();
        voter2.join();

        System.out.println("Total votes: " + counter.getVotes());
    }
}
