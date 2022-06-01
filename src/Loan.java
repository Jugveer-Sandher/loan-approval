import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Loan {
    private PriorityQueue<Applicant> activeList;
    private ArrayList<Applicant> approvedList;
    private ArrayList<Applicant> rejectedList;
    private int budget;

    public Loan () {
        activeList = new PriorityQueue<>();
        approvedList = new ArrayList<>();
        rejectedList = new ArrayList<>();
        budget = 0;
    }



    public void run() throws IOException {
        /* print the options for the Loan app  and execute them
         *
         * Possible operations:
         * Load applications
         * Set the budget
         * Make a decision
         * Print
         * Update an application (there is a method find() in PriorityQueue
         *                        that you can use to find the applicant
         *                        you would like to update)
         */

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Please enter one out of the following menu options: \n" +
                    "1.) Load Applications \n" +
                    "2.) Set the Budget \n" +
                    "3.) Make a decision \n" +
                    "4.) Print \n" +
                    "5.) Update an applicant \n" +
                    "6.) Exit");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    loadApplications();
                    break;
                case "2":
                    updateBudget();
                    break;
                case "3":
                    decisionMaking();
                    break;
                case "4":
                    print();
                    break;
                case "5":
                    updateApplication();
                    break;
                case "6":
                    System.out.println("Logging out");
                    System.exit(0);
                default:
                    System.out.println("Input not found please try again.");
                    System.exit(0);
            }
        }
    }

    private void loadApplications() throws FileNotFoundException {
        File text = new File("src/application.txt");
        Scanner scan = new Scanner(text);

        while(scan.hasNext()) {
            String[] line = scan.nextLine().split("\t");
            int[] profit = new int[30];
            for(int i = 4; i <= line.length-1; i++) {
                profit[i-4] = Integer.parseInt(line[i]);
            }
            Applicant a1 = new Applicant(line[0], Integer.parseInt(line[2]), Integer.parseInt(line[3]), profit, Integer.parseInt(line[1]));
            if (Integer.parseInt(line[2]) + Integer.parseInt(line[3]) < 10) {
                rejectedList.add(a1);
            }
            else {
                activeList.push(a1);
            }
        }
        System.out.println("Applicants have been loaded\n");
    }

    private void updateBudget() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the new budget:");
        budget = Integer.parseInt(scanner.nextLine());
        System.out.println("New budge of: " + budget + " is created.\n");
    }

    private void decisionMaking() {
        Applicant a1;
        PriorityQueue<Applicant> temp = new PriorityQueue<Applicant>();
        while (!activeList.isEmpty()) {
            a1 = activeList.pop();
            if (a1.getLoanAmount() <= budget) {
                budget -= a1.getLoanAmount();
                approvedList.add(a1);
            }
            else {
                temp.push(a1);
            }
        }
        activeList = temp;
        System.out.println("Decision on Applicants have been made.\n");
    }

    private void print() throws IOException {
        FileWriter approved = new FileWriter("approved.txt");
        FileWriter rejected = new FileWriter("rejected.txt");
        FileWriter active = new FileWriter("active.txt");

        for (Applicant value : approvedList) {
            approved.append(value.getName()).append("\t").append(String.valueOf(value.getLoanAmount())).append("\n");
        }
        for (Applicant applicant : rejectedList) {
            rejected.append(applicant.getName()).append("\t").append(String.valueOf(applicant.getLoanAmount())).append("\n");
        }

        Applicant a1;
        PriorityQueue<Applicant> temp = new PriorityQueue<Applicant>();
        while (!activeList.isEmpty()) {
            a1 = activeList.pop();
            active.append(a1.getName()).append("\t").append(String.valueOf(a1.getLoanAmount())).append("\t").append(String.valueOf(a1.getScore())).append("\n");
            temp.push(a1);
        }
        activeList = temp;

        approved.close();
        rejected.close();
        active.close();
        System.out.println("Applicants have been printed onto the files\n");
    }

    private void updateApplication() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Applicant applicant = null;
        System.out.println("Enter applicants name:");
        String input = scanner.nextLine();

        Applicant a1;
        PriorityQueue<Applicant> temp = new PriorityQueue<Applicant>();
        while (!activeList.isEmpty()) {
            a1 = activeList.pop();
            if (a1.getName().equalsIgnoreCase(input)) {
                applicant = a1;
            }
            temp.push(a1);
        }
        activeList = temp;

        System.out.println("Please enter the file");
        File file = new File(scanner.nextLine());
        Scanner fileScanner = new Scanner(file);
        String[] line = fileScanner.nextLine().split("\t");

        String name = line[0];
        int loanAmount = Integer.parseInt(line[1]);
        int yearsOfEducation = Integer.parseInt(line[2]);
        int yearsOfExperience = Integer.parseInt(line[3]);
        int[] profit = new int[30];
        for(int i = 4; i <= line.length-1; i++) {
            profit[i-4] = Integer.parseInt(line[i]);
        }

        if (applicant == null) {
            System.out.println("Applicant with the name entered was not found");
            System.exit(0);
        }
        applicant.setName(name);
        applicant.setLoanAmount(loanAmount);
        applicant.setYearsOfEducation(yearsOfEducation);
        applicant.setYearsOfExperience(yearsOfExperience);
        applicant.setProfit(profit);
        int score = 0;
        for (int i = 0; i < profit.length; i++) {
            score += profit[i]/(i+1);
        }
        applicant.setScore(score);
        activeList.updateItem(applicant);
        decisionMaking();
        System.out.println("And Applicant info has been updated.\n");
    }
}