package Services;

import Stores.StaffTextDB;
import java.io.IOException;
import java.util.List;
import Stores.Staff;
import Stores.Branch;
/*
 * @author Aaron Mari Santos Solis, Toh Jun Sheng, Dana Yak, Isaac Wong Jia Kai, Jamie Tan Pei Wen
 * @version 1.0
 * @since 2024-04-01
 */

/**
 * CheckQuota class checks the quota of staff and managers in a branch.
 */

public class CheckQuota {

    /**
     * Returns the number of staff in a branch.
     * 
     * @param b the branch to check
     * @return the number of staff in the branch
     * @throws IOException if an I/O error occurs when reading the staff list
     */
    public static int staffQuota(Branch b) throws IOException {
        int staffCount = 0;
        @SuppressWarnings("unchecked")
        List<Staff> staffs = StaffTextDB.readStaff("staff.txt");
        for (Staff s : staffs) {
            if (s.getRole().equals(Enums.Role.S) && s.getBranch().equals(b.getName())) {
                staffCount++;
            }
        }
        return staffCount;
    }
    /**
     * Returns the number of managers in a branch.
     * 
     * @param b the branch to check
     * @return the number of managers in the branch
     * @throws IOException if an I/O error occurs when reading the staff list
     */

    public static int managerQuota(Branch b) throws IOException {
        int managerCount = 0;
        @SuppressWarnings("unchecked")
        List<Staff> staffs = StaffTextDB.readStaff("staff.txt");
        for (Staff s : staffs) {
            if (s.getRole().equals(Enums.Role.M) && s.getBranch().equals(b.getName())) {
                managerCount++;
            }
        }
        return managerCount;
    }
    /**
     * Returns the remaining staff quota in a branch.
     * 
     * @param b the branch to check
     * @return the remaining staff quota in the branch
     * @throws IOException if an I/O error occurs when reading the staff list
     */

    public static int remainderStaffQuota(Branch b) throws IOException {

        int s = staffQuota(b);
        int max_s = b.getStaffQuota();

        // overstaffed
        if (max_s - s < 0)
            return -1;
        else
            return max_s - s -1;
    }
    /**
     * Returns the remaining manager quota in a branch.
     * 
     * @param b the branch to check
     * @return the remaining manager quota in the branch
     * @throws IOException if an I/O error occurs when reading the staff list
     */

    public static int remainderManagerQuota(Branch b) throws IOException {

        int s = staffQuota(b);
        int m = managerQuota(b);

        // check manager quota, too many managers = -1
        if (1 <= s && s <= 4) {
            if (1 - m <= 0)
                return -1;
            else
                return 1 - m -1;// if successfully, staff is added
        }

        if (5 <= s && s <= 8) {
            if (2 - m <= 0)
                return -1;
            else
                return 2 - m -1;
        }

        if (9 <= s && s <= 15) {
            if (3 - m <= 0)
                return -1;
            else
                return 3 - m -1;
        }
        return -1;
    }
    /**
     * Checks if the staff quota in a branch is exceeded.
     * 
     * @param b the branch to check
     * @return true if the staff quota is not exceeded, false otherwise
     * @throws IOException if an I/O error occurs when reading the staff list
     */

    public static boolean checkStaffQuota(Branch b) throws IOException {
        int check;
        check = remainderStaffQuota(b);
        if (check < 0) {
            System.out.println("Staff Quota for " + b.getName() + " has been exceeded. Staff cannot be added");
            return false;
        } 
        else if (check >= 0) {
            System.out.println(check + " staff slot(s) remaining.");
            return true;
        }
        return false;
    }
    /**
     * Checks if the manager quota in a branch is exceeded.
     * 
     * @param b the branch to check
     * @return true if the manager quota is not exceeded, false otherwise
     * @throws IOException if an I/O error occurs when reading the staff list
     */

    public static boolean checkManagerQuota(Branch b) throws IOException {
        int check;
        check = remainderManagerQuota(b);
        if (check < 0) {
            System.out.println("Too many managers in " + b.getName() + ". Remove some managers.");
            return false;
        }
        else if (check >= 0) {
            System.out.println(check + " manager slot(s) remaining.");
            return true;
        }
        return false;
    }

    
    /**
     * Checks if the staff and manager quota in a branch is exceeded.
     * 
     * @param b the branch to check
     * @throws IOException if an I/O error occurs when reading the staff list
     */

    public static void checkManagerRequirement(Branch b) throws IOException
    { 
        int s = staffQuota(b);
        int m = managerQuota(b);

        if (1 <= s && s <= 4) {
            if (m == 0)
            {
                System.out.println("Please assign a manager to " + b.getName());
            }
            else if (m > 1)
            {
                System.out.println("Please remove a manager from " + b.getName());
            }
            else
            {
                System.out.println("No further action required");
            }
        }

        if (5 <= s && s <= 8) {
            if (m<2)
                System.out.println("Please add assign manager to " + b.getName());
            else if (m>2)
                System.out.println("Please remove a manager from " + b.getName());
            else 
                System.out.println("No further action required");
        }

        if (9 <= s && s <= 15) {
            if (m<3)
                System.out.println("Please add assign manager to " + b.getName());
            else if (m>2)
                System.out.println("Please remove a manager from " + b.getName());
            else
                System.out.println("No further action required");
        }
    }

}
