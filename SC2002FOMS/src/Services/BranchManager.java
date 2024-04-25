package Services;

import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import Interfaces.IBranchManagement;
import Stores.BranchTextDB;
import Stores.Branch;
import Enums.BranchStatus;

public class BranchManager implements IBranchManagement {

    private static final Scanner sc = new Scanner(System.in);

    @Override
    public void addBranch() throws IOException {

        System.out.println("Name:");
        String name = sc.nextLine();

        List<Branch> al = BranchTextDB.readBranchList("branch.txt");// test
        for (Branch branch : al) {
            if (branch.getName().equalsIgnoreCase(name)) {
                System.out.println("Branch already exists");
                return;
            }
        }
        // String name, String location, int staffQuota, BranchStatus branchStatus
        System.out.println("Location");
        String location = sc.nextLine();
        System.out.println("Staff Quota:");
        int staffQuota = sc.nextInt();
        sc.nextLine(); // input buffer

        // assumption that every new branch added will be set as open

        System.out.println("Branch has been succesfully added");

        Branch branch = new Branch(name, location, staffQuota, BranchStatus.OPEN);
        BranchTextDB.addBranch("branch.txt", branch);
    }

    @Override
    public void setBranchStatus() throws IOException {
        // TODO Auto-generated method stub
        int set, choice;

        BranchTextDB.printBranch("branch.txt");
        List<Branch> branches = BranchTextDB.readBranchList("branch.txt");
        choice = sc.nextInt();
        Branch selectedBranch = branches.get(choice - 1);
        Branch oldStatus = selectedBranch;
        Branch newStatus = oldStatus;

        System.out.println("Open/Close Branch");
        System.out.println("<Press 1 to open Branch or Press 0 to close Branch>");

        set = sc.nextInt();
        while (set != 1 && set != 0) {
            System.out.println("Invalid choice! Enter 1 to open or 0 to close the branch.");
            set = sc.nextInt();
        }
        while (!newStatus.setBranchStatus(set)) {
            set = sc.nextInt();
        }

        BranchTextDB.updateBranchStatus("branch.txt", oldStatus, newStatus);

        BranchTextDB.printBranch("branch.txt");
        System.out.println("Branch status updated successfully.\n");
    }

    @Override
    public void removeBranch() throws IOException {

        System.out.println("=======Branch List=======");
        List<Branch> al = BranchTextDB.readBranchList("branch.txt");// test

        for (Branch branch : al) {
            System.out.println(branch.getName());
        }
        // BranchTextDB.printBranch("branch.txt");

        System.out.println("Enter Branch Name:");
        String name = sc.nextLine();

        Branch toRemove = null;
        for (Branch branch : al) {
            if (branch.getName().equals(name)) // assumptions that branch names are always unique
            {
                toRemove = branch;
                BranchTextDB.removeBranch("branch.txt", toRemove);
                System.out.println("Branch has been removed successfully.");
                break;
            }
        }
        if (toRemove == null) {
            System.out.println("Branch does not exist");
        }
    }

    @Override
    public void viewBranch() throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewBranch'");
    }

    @Override
    public void filterBranch() throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'filterBranch'");
    }

}
