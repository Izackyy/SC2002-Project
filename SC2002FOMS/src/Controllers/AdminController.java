package Controllers;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import Enums.Gender;
import Enums.Role;
import Services.StaffDisplay;
import Stores.Branch;
import Stores.BranchTextDB;

public class AdminController extends EmployeeController{
	
	private static final Scanner sc = new Scanner(System.in);

	public void start() throws IOException
	{
		int selection;
		
		do
		{
			System.out.println("==========Admin's Actions==========");
			System.out.println("|| 1) Edit Staff Account         ||"); //just like how you edit menu
			System.out.println("|| 2) Display Staff List         ||");
			System.out.println("|| 3) Assign Manager             ||");
			System.out.println("|| 4) Promote Staff To Manager   ||");
			System.out.println("|| 5) Transfer Staff/Manager     ||");
			System.out.println("|| 6) Add/Remove Payment Method  ||");
			System.out.println("|| 7) Open/Close branch          ||");
			System.out.println("|| 8) Change Password            ||");
			System.out.println("|| 9) Quit                       ||");
			System.out.println("===================================");
			
			selection = sc.nextInt();
			
			switch (selection)
			{
				case(1):
					editStaffAcc();
					break;
				case(2):
					filterStaff();
					break;
				case(3):
				
				case(4):
					
				case(5):
					
				case(6):
					
				case(7):
					
				case(8):
					changePassword();
					break;
				case(9):
					
			}
			
		} while (selection != 9);
	}
	
	private static void editStaffAcc()
	{
		System.out.println("editing staff account"); 
	}
	
	private static void filterStaff() throws IOException
	{
		int selection;
		int choice;
		int i=0;
		System.out.println("======Filter staff======");
		System.out.println("1) Branch");
		System.out.println("2) Role");
		System.out.println("3) Gender");
		System.out.println("4) Age");
		System.out.println("5) Display all (no filter)");
		
		selection = sc.nextInt();
		
		switch(selection)
		{
			case(1):
				String branch;
				System.out.println("Select a branch: ");
				List<Branch> al = BranchTextDB.readBranchList("branch.txt");//test
	        	for (Branch b : al)
	        	{	//need to add condition that calls isopen() to only display branches that are open
	        		System.out.println(i + ") " + b.getName());
	        		i++;
	        	}
	        
	        	choice = sc.nextInt();
	        
	        	branch = al.get(selection-1).getName();
				
				StaffDisplay.printStaffList(branch);
				break;
			case(2):
				System.out.println("Select Role");
				System.out.println("1) Staff");
				System.out.println("2) Manager");
				
				choice = sc.nextInt();
				
		        Role role = (selection == 1) ? Role.S : Role.M;
		        StaffDisplay.printStaffList(role);
				break;
			case(3):
				System.out.println("Select gender");
				System.out.println("1) Female");
				System.out.println("2) Male");
			
				choice = sc.nextInt();
				
		        Gender gender = (selection == 1) ? Gender.F : Gender.M;
		        StaffDisplay.printStaffList(gender);
				break;
			case(4): // dont know if we can set a range 
			{
				System.out.println("Enter age:");
				choice = sc.nextInt();
				StaffDisplay.printStaffList(selection);
				break;
			}
				
		}
	}
}
