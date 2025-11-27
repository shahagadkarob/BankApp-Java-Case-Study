package bank;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

// --------------------------------------------------------------------------------------
// Bank
// -------------------------------------------------------------------------------------
class Bank {

	String bankName;
	String ifscCode;
	String bankAddress;
	String bankContactNumber;
	String bankEmail;

	BankAccount[] accounts = new BankAccount[100];
	int accountCount = 0;

	Scanner sc = new Scanner(System.in);

	// Initialize static interest rate
	static {
		BankAccount.interestRate = 4.0;
	}
	
	// --- Open Account ---

	BankAccount openAccount() {
		System.out.println("\n========= Select Account Type =========");
		System.out.println("1. Saving Account");
		System.out.println("2. Current Account");
		System.out.println("3. Salary Account");
		System.out.println("4. Loan Account");
		System.out.print("Enter your choice: ");
		int choice = sc.nextInt();
		sc.nextLine();

		System.out.print("Enter Account Holder Name: ");
		String name = sc.nextLine();

		System.out.print("Enter Branch Name: ");
		String branch = sc.nextLine();

		System.out.print("Enter Mobile Number: ");
		String mobile = sc.nextLine();

		System.out.print("Enter Email: ");
		String email = sc.nextLine();

		System.out.print("Enter Aadhaar Number: ");
		String aadhaar = sc.nextLine();

		System.out.print("Enter PAN Number: ");
		String pan = sc.nextLine();

		System.out.print("Enter Initial Balance: ");
		double balance = sc.nextDouble();
		sc.nextLine();

		long newAccNo = 100000 + accountCount + 1;
		BankAccount acc = null;

		if (choice == 1) {
			acc = createSavingAccount(name, newAccNo, branch, balance, mobile, email, aadhaar, pan);
		} else if (choice == 2) {
			acc = createCurrentAccount(name, newAccNo, branch, balance, mobile, email, aadhaar, pan);
		} else if (choice == 3) {
			acc = createSalaryAccount(name, newAccNo, branch, balance, mobile, email, aadhaar, pan);
		} else if (choice == 4) {
			acc = createLoanAccount(name, newAccNo, branch, mobile, email, aadhaar, pan);
		} else {
			System.out.println("Invalid choice.");
			return null;
		}

		if (accountCount < accounts.length) {
			accounts[accountCount++] = acc;
			System.out.println("\nAccount Created Successfully!");
			System.out.println("Account Number: " + newAccNo);
		} else {
			System.out.println("Bank account storage full!");
			return null;
		}
		return acc;
	}
	
	// --- Close Account ---

	void closeAccount(long accNo) {
		for (int i = 0; i < accountCount; i++) {
			if (accounts[i].getAccountNumber() == accNo) {
				accounts[i].closeAccount();
				for (int j = i; j < accountCount - 1; j++) {
					accounts[j] = accounts[j + 1];
				}
				accounts[--accountCount] = null;
				System.out.println("Account removed.");
				return;
			}
		}
		System.out.println("Account not found.");
	}
	
	// --- find account ---

	BankAccount findAccount(long accNo) {
		for (int i = 0; i < accountCount; i++) {
			if (accounts[i].getAccountNumber() == accNo)
				return accounts[i];
		}
		return null;
	}
	
	// --- Check Balance ---

	double checkBalance(long accNo) {
		BankAccount acc = findAccount(accNo);
		if (acc != null) {
			System.out.println("Balance: " + acc.getBalance());
			return acc.getBalance();
		}
		System.out.println("Account not found.");
		return -1;
	}
	
	// --- create saving account ---

	SavingAccount createSavingAccount(String name, long accNo, String branch, double balance, String mobile,
			String email, String aadhaar, String pan) {
		System.out.print("Enter Minimum Balance: ");
		double minBalance = sc.nextDouble();

		System.out.print("Enter Withdrawal Limit (per transaction): ");
		double withdrawalLimit = sc.nextDouble();

		System.out.print("Enter Daily Amount Limit: ");
		double dailyAmtLimit = sc.nextDouble();

		System.out.print("Enter CIBIL Score: ");
		double cibilScore = sc.nextDouble();
		sc.nextLine();

		return new SavingAccount(name, accNo, branch, balance, mobile, email, aadhaar, pan, minBalance, withdrawalLimit,
				dailyAmtLimit, cibilScore);
	}
	
	// --- create current account ---

	CurrentAccount createCurrentAccount(String name, long accNo, String branch, double balance, String mobile,
			String email, String aadhaar, String pan) {
		System.out.print("Enter Overdraft Limit: ");
		double overdraftLimit = sc.nextDouble();

		System.out.print("Enter Overdraft Interest Rate (%): ");
		double overdraftInterestRate = sc.nextDouble();
		sc.nextLine();

		System.out.print("Enter Business Name: ");
		String businessName = sc.nextLine();

		System.out.print("Enter GST Number: ");
		String gstNumber = sc.nextLine();

		return new CurrentAccount(name, accNo, branch, balance, mobile, email, businessName, gstNumber, aadhaar, pan,
				overdraftLimit, overdraftInterestRate);
	}
	
	// --- create salary account ---

	SalaryAccount createSalaryAccount(String name, long accNo, String branch, double balance, String mobile,
			String email, String aadhaar, String pan) {
		System.out.print("Enter Salary Amount: ");
		double salaryAmount = sc.nextDouble();
		sc.nextLine();

		System.out.print("Enter Salary Credit Date (yyyy-mm-dd): ");
		String creditDateStr = sc.nextLine();

		System.out.print("Enter Account Status: ");
		String accountStatus = sc.nextLine();

		System.out.print("Enter Last Transaction Date (yyyy-mm-dd): ");
		String lastTranStr = sc.nextLine();

		LocalDate creditDate = LocalDate.parse(creditDateStr);
		LocalDate lastTranDate = LocalDate.parse(lastTranStr);

		return new SalaryAccount(name, accNo, branch, balance, mobile, email, aadhaar, pan, salaryAmount,
				Date.from(creditDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), accountStatus,
				Date.from(lastTranDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
	}
	
	// --- create Loan account ---

	LoanAccount createLoanAccount(String name, long accNo, String branch, String mobile, String email, String aadhaar,
			String pan) {
		System.out.print("Enter Loan Amount: ");
		double loanAmount = sc.nextDouble();
		sc.nextLine();

		System.out.print("Enter Loan Type: ");
		String loanType = sc.nextLine();

		System.out.print("Enter Loan Interest Rate (%): ");
		double loanInterestRate = sc.nextDouble();

		System.out.print("Enter Tenure (months): ");
		int tenure = sc.nextInt();
		sc.nextLine();

		return new LoanAccount(name, accNo, branch, loanAmount, loanType, loanInterestRate, tenure, mobile, email,
				aadhaar, pan);
	}
}

// -----------------------------------------------------------------------------------------------------
// Transaction
// -----------------------------------------------------------------------------------------------------
class Transaction {
	String transactionId;
	double amount;
	LocalDate date;
	String type;
	String status;
	static int transactionCount = 0;

	Transaction(String transactionId, double amount, LocalDate date, String type, String status) {
		this.transactionId = transactionId;
		this.amount = amount;
		this.date = date;
		this.type = type;
		this.status = status;
	}

	void setStatus(String status) {
		this.status = status;
	}
}

// ---------------------------------------------------------------------------------------------
// Bank Account 
// ---------------------------------------------------------------------------------------------
abstract class BankAccount {
	String accHolderName;
	final long accountNumber;
	String branchName;
	double balance;
	String accountType;
	String mobileNumber;
	String email;
	final Date createdDate;
	final String aadhaarNumber;
	final String panNumber;
	static double interestRate = 4.0;

	Transaction[] transactions = new Transaction[100];
	int transactionCount = 0;

	BankAccount(String accHolderName, long accountNumber, String branchName, double balance, String accountType,
			String mobileNumber, String email, String aadhaarNumber, String panNumber) {
		this.accHolderName = accHolderName;
		this.accountNumber = accountNumber;
		this.branchName = branchName;
		this.balance = balance;
		this.accountType = accountType;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.aadhaarNumber = aadhaarNumber;
		this.panNumber = panNumber;
		this.createdDate = new Date();
	}

	public double getBalance() {
		return balance;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	abstract boolean deposit(double amount);

	abstract boolean withdraw(double amount);

	abstract String getAccountDetails();

	abstract void applyInterest();

	final void closeAccount() {
		System.out.println("Account " + accountNumber + " closed permanently.");
	}

	void recordTransaction(Transaction t, boolean success) {
		t.setStatus(success ? "SUCCESS" : "FAILED");
		if (transactionCount < transactions.length) {
			transactions[transactionCount++] = t;
		}
	}
}

// -----------------------------------------------------------------------
// Saving Account
// -----------------------------------------------------------------------
class SavingAccount extends BankAccount {
	final double minBalance;
	final double withdrawalLimit;
	final double dailyAmtLimit;
	double cibilScore;
	private double todayWithdrawn = 0;
	private LocalDate lastWithdrawalDay = LocalDate.now();

	SavingAccount(String accHolderName, long accountNumber, String branchName, double balance, String mobile,
			String email, String aadhaar, String pan, double minBalance, double withdrawalLimit, double dailyAmtLimit,
			double cibilScore) {
		super(accHolderName, accountNumber, branchName, balance, "SAVING", mobile, email, aadhaar, pan);
		this.minBalance = minBalance;
		this.withdrawalLimit = withdrawalLimit;
		this.dailyAmtLimit = dailyAmtLimit;
		this.cibilScore = cibilScore;
	}

	@Override
	boolean deposit(double amount) {
		if (amount <= 0) {
			recordTransaction(new Transaction("TXN" + (++Transaction.transactionCount), amount, LocalDate.now(),
					"DEPOSIT", "FAILED"), false);
			return false;
		}
		balance = balance + amount;
		recordTransaction(new Transaction("TXN" + (++Transaction.transactionCount), amount, LocalDate.now(), "DEPOSIT",
				"SUCCESS"), true);
		return true;
	}

	@Override
	boolean withdraw(double amount) {
		LocalDate today = LocalDate.now();
		if (!today.equals(lastWithdrawalDay)) {
			todayWithdrawn = 0;
			lastWithdrawalDay = today;
		}
		if (amount <= 0 || amount > withdrawalLimit || (todayWithdrawn + amount) > dailyAmtLimit
				|| (balance - amount) < minBalance) {
			recordTransaction(new Transaction("TXN" + (++Transaction.transactionCount), amount, LocalDate.now(),
					"WITHDRAW", "FAILED"), false);
			return false;
		}
		balance = balance - amount;
		todayWithdrawn = todayWithdrawn - amount;
		recordTransaction(new Transaction("TXN" + (++Transaction.transactionCount), amount, LocalDate.now(), "WITHDRAW",
				"SUCCESS"), true);
		return true;
	}

	@Override
	String getAccountDetails() {
		return "[SAVING] AccNo:" + accountNumber + " \nName:" + accHolderName + " \nBalance:" + balance + " \nBranch:"
				+ branchName + " \nMinBal:" + minBalance;
	}

	@Override
	void applyInterest() {
		double interest = (balance * interestRate) / 100.0;
		balance += interest;
		recordTransaction(new Transaction("TXN" + (++Transaction.transactionCount), interest, LocalDate.now(),
				"INTEREST", "SUCCESS"), true);
	}
}

//----------------------------------------------------------------------------------------------------------------------
//Current Account
//---------------------------------------------------------------------------------------------------------------------

class CurrentAccount extends BankAccount {
	final double overdraftLimit;
	final double overdraftInterestRate;
	final String businessName;
	final String gstNumber;

	CurrentAccount(String accHolderName, long accountNumber, String branchName, double balance, String mobile,
			String email, String businessName, String gstNumber, String aadhaar, String pan, double overdraftLimit,
			double overdraftInterestRate) {
		super(accHolderName, accountNumber, branchName, balance, "CURRENT", mobile, email, aadhaar, pan);
		this.businessName = businessName;
		this.gstNumber = gstNumber;
		this.overdraftLimit = overdraftLimit;
		this.overdraftInterestRate = overdraftInterestRate;
	}

	@Override
	boolean deposit(double amount) {
		if (amount <= 0)
			return false;
		balance += amount;
		recordTransaction(new Transaction("TXN" + (++Transaction.transactionCount), amount, LocalDate.now(), "DEPOSIT",
				"SUCCESS"), true);
		return true;
	}

	@Override
	boolean withdraw(double amount) {
		if (amount <= 0 || amount > (balance + overdraftLimit))
			return false;
		balance = balance - amount;
		recordTransaction(new Transaction("TXN" + (++Transaction.transactionCount), amount, LocalDate.now(), "WITHDRAW",
				"SUCCESS"), true);
		return true;
	}

	@Override
	String getAccountDetails() {
		return "[CURRENT] AccNo:" + accountNumber + " \nName:" + accHolderName + " \nBalance:" + balance + " \nBusiness:"
				+ businessName + " \nOD Limit:" + overdraftLimit;
	}

	@Override
	void applyInterest() {
		if (balance < 0) {
			double interest = Math.abs(balance) * (overdraftInterestRate / 100.0);
			balance = balance - interest;
			recordTransaction(new Transaction("TXN" + (++Transaction.transactionCount), interest, LocalDate.now(),
					"OD_INTEREST", "SUCCESS"), true);
		}
	}
}

//----------------------------------------------------------------------------------------------------------------------
//Salary Account
//---------------------------------------------------------------------------------------------------------------------

class SalaryAccount extends BankAccount {
	double salaryAmount;
	Date salaryCreditDate;
	String accountStatus;
	Date lastTransactionDate;
	private boolean isFrozen = false; // New field

	SalaryAccount(String accHolderName, long accountNumber, String branchName, double balance, String mobile,
			String email, String aadhaar, String pan, double salaryAmount, Date salaryCreditDate, String accountStatus,
			Date lastTransactionDate) {
		super(accHolderName, accountNumber, branchName, balance, "SALARY", mobile, email, aadhaar, pan);
		this.salaryAmount = salaryAmount;
		this.salaryCreditDate = salaryCreditDate;
		this.accountStatus = accountStatus;
		this.lastTransactionDate = lastTransactionDate;
	}

	// Check if account should be frozen due to inactivity (no txn in 60 days)
	private void checkAndFreezeIfInactive() {
		if (isFrozen)
			return; // Already frozen

		if (lastTransactionDate == null) {
			lastTransactionDate = createdDate; // fallback
		}

		long diffInMillis = new Date().getTime() - lastTransactionDate.getTime();
		long daysInactive = diffInMillis / (1000 * 60 * 60 * 24);

		if (daysInactive > 60) {
			isFrozen = true;
			accountStatus = "FROZEN - INACTIVE FOR 60+ DAYS";
			System.out.println(
					"Salary Account " + accountNumber + " is FROZEN due to inactivity (>60 days).");
		}
	}
	
	void unfreeze() {
		if (isFrozen) {
			isFrozen = false;
			accountStatus = "ACTIVE";
			System.out.println("Salary Account " + accountNumber + " has been UNFREEZED.");
		}
	}

	void creditSalary() {
		unfreeze(); 
		deposit(salaryAmount);
		System.out.println("Salary ₹" + salaryAmount + " credited successfully!");
	}

	@Override
	boolean deposit(double amount) {
		checkAndFreezeIfInactive(); // Check freeze status on any operation

		if (isFrozen && !"SALARY_CREDIT".equals(Thread.currentThread().getStackTrace()[2].getMethodName())) {
			recordTransaction(new Transaction("TXN" + (++Transaction.transactionCount), amount, LocalDate.now(),
					"DEPOSIT", "FAILED - ACCOUNT FROZEN"), false);
			System.out.println("Cannot deposit. Account is FROZEN due to inactivity.");
			return false;
		}

		if (amount <= 0) {
			recordTransaction(new Transaction("TXN" + (++Transaction.transactionCount), amount, LocalDate.now(),
					"DEPOSIT", "FAILED"), false);
			return false;
		}

		balance = balance + amount;
		lastTransactionDate = new Date(); 
		unfreeze(); 
		recordTransaction(new Transaction("TXN" + (++Transaction.transactionCount), amount, LocalDate.now(), "DEPOSIT",
				"SUCCESS"), true);
		return true;
	}

	@Override
	boolean withdraw(double amount) {
		checkAndFreezeIfInactive(); 

		if (isFrozen) {
			recordTransaction(new Transaction("TXN" + (++Transaction.transactionCount), amount, LocalDate.now(),
					"WITHDRAW", "FAILED - ACCOUNT FROZEN"), false);
			System.out.println(" Withdrawal failed! Account is FROZEN due to no activity in last 60 days.");
			return false;
		}

		if (amount <= 0 || amount > balance) {
			recordTransaction(new Transaction("TXN" + (++Transaction.transactionCount), amount, LocalDate.now(),
					"WITHDRAW", "FAILED"), false);
			return false;
		}

		balance -= amount;
		lastTransactionDate = new Date();
		recordTransaction(new Transaction("TXN" + (++Transaction.transactionCount), amount, LocalDate.now(), "WITHDRAW",
				"SUCCESS"), true);
		return true;
	}

	@Override
	String getAccountDetails() {
		checkAndFreezeIfInactive(); // Update status dynamically
		return "[SALARY] AccNo:" + accountNumber + " \nName:" + accHolderName + " \nBalance: ₹" + balance + " \nSalary: ₹"
				+ salaryAmount + " \nStatus: " + accountStatus + " \nBranch:" + branchName;
	}

	@Override
	void applyInterest() {
		checkAndFreezeIfInactive();
		if (isFrozen) {
			System.out.println("Interest not applied. Account is FROZEN.");
			return;
		}
		double interest = (balance * interestRate) / 100.0;
		balance += interest;
		lastTransactionDate = new Date();
		recordTransaction(new Transaction("TXN" + (++Transaction.transactionCount), interest, LocalDate.now(),
				"INTEREST", "SUCCESS"), true);
	}

}

//----------------------------------------------------------------------------------------------------------------------
//Loan Account
//---------------------------------------------------------------------------------------------------------------------

class LoanAccount extends BankAccount {
	final int loanId;
	double loanAmount;
	final String loanType;
	double loanInterestRate;
	int tenureMonths;
	Date issueDate;
	Date dueDate;
	String status;
	double outstandingAmount;
	Date lastPaymentDate;
	static int loanCounter = 0;

	LoanAccount(String accHolderName, long accountNumber, String branchName, double loanAmount, String loanType,
			double loanInterestRate, int tenureMonths, String mobileNumber, String email, String aadhaar, String pan) {
		super(accHolderName, accountNumber, branchName, 0, "LOAN", mobileNumber, email, aadhaar, pan);
		this.loanId = ++loanCounter;
		this.loanAmount = loanAmount;
		this.loanType = loanType;
		this.loanInterestRate = loanInterestRate;
		this.tenureMonths = tenureMonths;
		this.outstandingAmount = loanAmount;
		this.issueDate = new Date();

		Calendar c = Calendar.getInstance();
		c.setTime(issueDate);
		c.add(Calendar.MONTH, tenureMonths);
		this.dueDate = c.getTime();
		this.status = "ACTIVE";
	}

	@Override
	boolean deposit(double amount) {
		if (amount <= 0 || amount > outstandingAmount)
			return false;
		outstandingAmount -= amount;
		balance = balance + amount;
		lastPaymentDate = new Date();
		if (outstandingAmount <= 0)
			status = "CLOSED";
		recordTransaction(new Transaction("TXN" + (++Transaction.transactionCount), amount, LocalDate.now(),
				"LOAN_PAYMENT", "SUCCESS"), true);
		return true;
	}

	@Override
	boolean withdraw(double amount) {
		System.out.println("Withdrawal not allowed for Loan Account.");
		return false;
	}

	@Override
	String getAccountDetails() {
		return "[LOAN] AccNo:" + accountNumber + " \nHolder:" + accHolderName + " \nLoan:" + loanAmount + " \nOutstanding:"
				+ outstandingAmount + " \nType:" + loanType;
	}

	@Override
	void applyInterest() {
		double interest = outstandingAmount * (loanInterestRate / 100.0);
		outstandingAmount = outstandingAmount + interest;
		recordTransaction(new Transaction("TXN" + (++Transaction.transactionCount), interest, LocalDate.now(),
				"LOAN_INTEREST", "SUCCESS"), true);
	}
}

// --------------------------------------------------------------------------------------
// Bank Controller 
// --------------------------------------------------------------------------------------
class BankController {
	Bank bank;
	BankView view;
	Scanner sc = new Scanner(System.in);

	BankController(Bank bank, BankView view) {
		this.bank = bank;
		this.view = view;
	}

	void handleMainMenu() {
		while (true) {
			view.showMainMenu();
			int choice = view.getUserChoice();
			switch (choice) {
			case 1 -> openAccountController();
			case 2 -> depositController();
			case 3 -> withdrawController();
			case 4 -> checkBalanceController();
			case 5 -> showAccountDetailsController();
			case 6 -> loanMenuController(); // Fixed
			case 7 -> generateStatementController();
			case 8 -> {
				view.displayMessage("Exiting... Have A Good Day!");
				return;
			}
			default -> view.displayError("Invalid choice!");
			}
		}
	}

	void openAccountController() {
		BankAccount acc = bank.openAccount();
		if (acc != null) {
			view.displayMessage("Account created successfully!");
			view.displayAccountDetails(acc);
		}
	}

	void depositController() {
		long accNo = view.getAccountNumberInput();
		BankAccount acc = bank.findAccount(accNo);
		if (acc != null) {
			double amt = view.getAmountInput();
			if (acc.deposit(amt))
				view.displayMessage("Deposit successful!");
			else
				view.displayError("Deposit failed!");
		} else
			view.displayError("Account not found!");
	}

	void withdrawController() {
		long accNo = view.getAccountNumberInput();
		BankAccount acc = bank.findAccount(accNo);
		if (acc != null) {
			double amt = view.getAmountInput();
			if (acc.withdraw(amt))
				view.displayMessage("Withdrawal successful!");
			else
				view.displayError("Withdrawal failed!");
		} else
			view.displayError("Account not found!");
	}

	void checkBalanceController() {
		long accNo = view.getAccountNumberInput();
		bank.checkBalance(accNo);
	}

	void showAccountDetailsController() {
		long accNo = view.getAccountNumberInput();
		BankAccount acc = bank.findAccount(accNo);
		if (acc != null)
			view.displayAccountDetails(acc);
		else
			view.displayError("Account not found!");
	}

	//  Only method was broken
	void loanMenuController() {
		while (true) {
			view.showLoanMenu();
			int choice = view.getUserChoice();
			switch (choice) {
			case 1 -> view.displayMessage("To apply for a loan, go to Main Menu → Open Account → Choose option 4");
			case 2 -> payEMIController();
			case 3 -> view.displayMessage("Loan closes automatically when fully repaid.");
			case 4 -> {
				return;
			}
			default -> view.displayError("Invalid choice!");
			}
		}
	}

	void payEMIController() {
		long accNo = view.getAccountNumberInput();
		BankAccount acc = bank.findAccount(accNo);
		if (acc instanceof LoanAccount loanAcc) {
			view.displayMessage("Outstanding Amount: ₹" + loanAcc.outstandingAmount);
			double amt = view.getAmountInput();
			if (loanAcc.deposit(amt)) {
				view.displayMessage("EMI Payment Successful! New Outstanding: ₹" + loanAcc.outstandingAmount);
				if (loanAcc.outstandingAmount <= 0) {
					view.displayMessage("Loan Fully Repaid! Account will be closed.");
					bank.closeAccount(accNo);
				}
			} else {
				view.displayError("Payment failed! Amount cannot exceed outstanding.");
			}
		} else {
			view.displayError("Not a Loan Account!");
		}
	}

	void generateStatementController() {
		long accNo = view.getAccountNumberInput();
		BankAccount acc = bank.findAccount(accNo);
		if (acc != null && acc.transactionCount > 0) {
			view.displayMessage("Transaction Statement for Account: " + accNo);
			for (int i = 0; i < acc.transactionCount; i++) {
				Transaction t = acc.transactions[i];
				view.displayMessage("TxnID: " + t.transactionId + " | Type: \n" + t.type + " | Amount: \n" + t.amount
						+ " | Date: \n" + t.date + " | Status: \n" + t.status);
			}
		} else {
			view.displayError("No transactions or account not found!");
		}
	}
}

//----------------------------------------------------------------------------------------------------------------------
// Bank View
// ---------------------------------------------------------------------------------------------------------------------

class BankView {
	Scanner sc = new Scanner(System.in);

	void start() {
		System.out.println("==============================================");
		System.out.println("              WELCOME TO FIRSTBIT BANK             ");
		System.out.println("              GROW IT BIT  BY BIT             ");
		System.out.println("==============================================");
	}

	void showMainMenu() {
		System.out.println("\n================ MAIN MENU ================");
		System.out.println("1. Open Account");
		System.out.println("2. Deposit Money");
		System.out.println("3. Withdraw Money");
		System.out.println("4. Check Balance");
		System.out.println("5. Show Account Details");
		System.out.println("6. Loan Services");
		System.out.println("7. Generate Statement");
		System.out.println("8. Exit");
		System.out.print("Enter your choice: ");
	}

	void showLoanMenu() {
		System.out.println("\n========= LOAN MENU =========");
		System.out.println("1. Apply for Loan  → (Use Open Account → Option 4)");
		System.out.println("2. Pay EMI");
		System.out.println("3. Close Loan      → (Auto on full repayment)");
		System.out.println("4. Back");
		System.out.print("Enter your choice: ");
	}

	int getUserChoice() {
		while (!sc.hasNextInt()) {
			System.out.print("Invalid input! Enter a number: ");
			sc.next();
		}
		int ch = sc.nextInt();
		sc.nextLine();
		return ch;
	}

	void displayMessage(String msg) {
		System.out.println("\n " + msg);
	}

	void displayError(String msg) {
		System.out.println("\n " + msg);
	}

	void displayAccountDetails(BankAccount acc) {
		System.out.println("\n========= ACCOUNT DETAILS =========");
		System.out.println(acc.getAccountDetails());
		System.out.println("====================================");
	}

	long getAccountNumberInput() {
		System.out.print("Enter Account Number: ");
		while (!sc.hasNextLong()) {
			System.out.print("Invalid! Enter number: ");
			sc.next();
		}
		long num = sc.nextLong();
		sc.nextLine();
		return num;
	}

	double getAmountInput() {
		System.out.print("Enter Amount: ");
		while (!sc.hasNextDouble()) {
			System.out.print("Invalid! Enter number: ");
			sc.next();
		}
		double amt = sc.nextDouble();
		sc.nextLine();
		return amt;
	}
}

// ==================================================================================
// Main Class
// ==================================================================================
class BankManagementSystem {
	public static void main(String[] args) {
		Bank bank = new Bank();

		BankView view = new BankView();
		view.start();

		BankController controller = new BankController(bank, view);
		controller.handleMainMenu();
	}
}