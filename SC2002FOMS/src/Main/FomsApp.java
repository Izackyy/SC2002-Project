package Main;

import java.io.IOException;

import Controllers.AdminController;
import Controllers.AuthController;
import Controllers.ManagerController;
import Controllers.StaffController;
import Enums.Role;
import Interfaces.IBranchManagement;
import Interfaces.IStaffManagement;
import Interfaces.IOrderManager;
import Interfaces.IPaymentManagement;
import Services.BranchManager; 
import Services.OrderManager; 
import Services.PaymentManager;
import Services.StaffManager;
import Stores.AuthStore;
import Stores.Staff;


public class FomsApp {
	

	public static void main(String[]aArgs) throws IOException
	{
		AuthController.start(); //to log in
		
		
		Staff staff = AuthStore.getCurrentStaff();
		
		if(staff!=null)//if null then is customer
		{	
			if (staff.getRole().equals(Role.S)) //plan to change to enum
			{
				IOrderManager om = new OrderManager();
				StaffController staffController = new StaffController(om);
				staffController.start();
			}
			else if (staff.getRole().equals(Role.M))
			{
				IOrderManager om = new OrderManager();
				ManagerController managerController = new ManagerController(om);
				managerController.start();
			}
			else //admin
			{
				IBranchManagement bm = new BranchManager();
				IStaffManagement sm = new StaffManager();
				IPaymentManagement pm = new PaymentManager();
				AdminController adminController = new AdminController(bm,sm,pm);
				adminController.start();
			}
		}
		
		
        //StaffTextDB.printStaffList();
	}
}
