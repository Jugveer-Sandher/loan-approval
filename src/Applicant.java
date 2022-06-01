public class Applicant implements Comparable<Applicant> {
    private String name;
    private int score;
    private int yearsOfEducation;
    private int yearsOfExperience;
    private int loanAmount;
    private int[] profit;
    public Applicant(final String name, final int yearsOfEducation, final int yearsOfExperience,
                        final int[] profit, final int loanAmount) {
        this.name = name;
        this.yearsOfEducation = yearsOfEducation;
        this.yearsOfExperience = yearsOfExperience;
        this.profit = profit;
        for (int i = 0; i < profit.length; i++) {
            this.score += profit[i]/(i+1);
        }
        this.loanAmount = loanAmount;
    }

    public int compareTo(Applicant other){
        if (this.score > other.score)
            return 1;
        else if (this.score < other.score)
            return -1;
        else
            return 0;

    }


    public boolean equals(Applicant other) {
        return this.name.equals(other.getName());
    }

    @Override
    public String toString() {
        return name + "\t" + loanAmount + "\t" + score;
    }

    // add any other method you need
    // Getters and Setters
    public int getLoanAmount() {
        return loanAmount;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getYearsOfEducation() {
        return yearsOfEducation;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public int[] getProfit() {
        return profit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setYearsOfEducation(int yearsOfEducation) {
        this.yearsOfEducation = yearsOfEducation;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public void setLoanAmount(int loanAmount) {
        this.loanAmount = loanAmount;
    }

    public void setProfit(int[] profit) {
        this.profit = profit;
    }
}