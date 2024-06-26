package Entity;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import Enums.DiningOption;
import Enums.OrderStatus;
import View.MenuDisplay;
import Services.PaymentService;
import Stores.Branch;
import Stores.Order;
import Stores.OrderTextDB;
/*
 * @author Aaron Mari Santos Solis, Toh Jun Sheng, Dana Yak, Isaac Wong Jia Kai, Jamie Tan Pei Wen
 * @version 1.0
 * @since 2024-04-01
 */

/**
 * Represents a new order made by a customer.
 */

public class NewOrder {

	private Branch branch;

	private Cart cart;

	private DiningOption option;

	public static int orderID;

	private static final Scanner sc = new Scanner(System.in);
	private static final Random random = new Random();

	// private timeOrder;
	/**
	 * Creates a new order with an empty cart and the specified branch.
	 * @param branch
	 */
	public NewOrder(Branch branch) {

		cart = new Cart();
		option = DiningOption.TAKEAWAY;
		this.branch = branch;
	}
	/**
	 * Starts the order process by allowing the customer to add, remove, or edit items in the cart.
	 * @param branch
	 * @throws IOException
	 */
	public static void startOrder(Branch branch) throws IOException {
		String wantReceipt;
		NewOrder order = new NewOrder(branch);

		boolean confirm = false;
		int selection = -1;

		do {
			System.out.println("======Cart Actions======");
			System.out.println("1) Add Item to cart");
			System.out.println("2) Remove Item from cart");
			System.out.println("3) Edit Item in cart");
			System.out.println("4) Confirm cart");

			try{
				selection = sc.nextInt();

				switch (selection) {
					case (1):
						MenuDisplay.printMenuItem(branch.getName());
						order.cart.addItem(branch.getName());
						System.out.println("===================Updated Cart===================");
						order.cart.printCart();
						break;
					case (2):
						order.cart.removeItem();
						System.out.println("===================Updated Cart===================");
						order.cart.printCart();
						break;
					case (3):
						order.cart.editItem();
						System.out.println("===================Updated Cart===================");
						order.cart.printCart();
						break;
					case (4):
						if (order.cart.checkItems() == false) {
							System.out.println("Cart is empty. Please add items.");
						} else {
							confirm = true;
						}
					default:
						System.out.println("Please enter a valid number.");
						break;
				}
			}
			catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                sc.next(); // Clear the incorrect input from scanner buffer
            } catch (RuntimeException e) {
                System.out.println("An error occurred: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }   
		} while (confirm == false);

		System.out.println("Dine-in/Takeaway?");
		System.out.println("<Press 1 for Dine-in or Press 2 for Takeaway>");

		while (selection < 1 || selection > 2) {
			selection = sc.nextInt();

		if (selection == 1) {
			order.option = DiningOption.DINE_IN;
		} else if (selection == 2) {
			order.option = DiningOption.TAKEAWAY;
		} else {
			System.out.println("Invalid selection. Please try again.");
		}
	}
		sc.nextLine();
		// check out
		PaymentService.start();
		Receipt receipt = new Receipt();
		System.out.println("Do you need a receipt? Y/N");
		wantReceipt = sc.nextLine();
		if (wantReceipt.equalsIgnoreCase("Y")) {
			System.out.println("Generating Receipt...");
			Receipt.printReceipt(order);
		}

		order.orderID = generateRandomOrderID();

		System.out.println("");

		System.out.println("Order ID: " + order.orderID);

		Order storeOrder = new Order(orderID, branch.getName(), OrderStatus.PROCESSING);
		OrderTextDB.addOrder("order.txt", storeOrder);
		order.cart.addOrderline(orderID);
	}

	/**
	 * Generates a random order ID for the order.
	 * @return
	 */

	private static int generateRandomOrderID() {
		return random.nextInt(100000);
	}
	/**
	 * Returns the branch where the order was made.
	 * @return
	 */
	public Branch getBranch() {
		return branch;
	}
	/**
	 * Returns the cart containing items in the order.
	 * @return
	 */

	public Cart getCart() {
		return cart;
	}
	/**
	 * Returns the dining option for the order.
	 * @return
	 */

	public DiningOption getDiningOption() {
		return option;
	}
}