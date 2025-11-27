# BankApp-Java-Case-Study
# 🏦 FirstBit Bank – Console-Based Banking Management System (Java)

This project is a **menu-driven console banking system** written in **Core Java (No SQL / No Collections)** using **Arrays, OOP & MVC Architecture**.  
It supports multiple account types and complete banking operations including loans, transactions & statements.

---

## ✨ Features

| Feature | Description |
|--------|-------------|
| Open Account | Saving / Current / Salary / Loan |
| Deposit Money | Supported for all except Loan withdrawal |
| Withdraw Money | Based on rules & limits per account type |
| Check Balance | Show available balance |
| Account Details | Full information of the account |
| Loan Services | EMI payment, Auto close on full repayment |
| Statement | Shows all deposits, withdrawals, interest & loan payments |
| Freeze Logic | Salary accounts freeze after 60 days inactivity |
| Interest | Applied to Saving, Current (overdraft interest), and Loan |

---

## 💡 Account Types & Rules

### 1️⃣ Saving Account
| Parameter | Rule |
|----------|------|
| Minimum Balance | Cannot go below min balance |
| Withdrawal Limit | Max amount allowed per transaction |
| Daily Limit | Max total withdrawal allowed per day |
| Interest | Monthly savings interest |

---

### 2️⃣ Current Account
| Feature | Details |
|--------|---------|
| Overdraft | Withdrawal beyond balance allowed |
| OD Interest | Interest applied when balance is negative |
| Business Details | GST No. & Business Name stored |

---

### 3️⃣ Salary Account
| Feature | Details |
|---------|---------|
| Monthly Salary | Credited using `creditSalary()` |
| Freeze Condition | Frozen if **no transaction for 60+ days** |
| Transaction Rules | No operation allowed when frozen except salary credit |
| Auto Unfreeze | When salary is credited |

---

### 4️⃣ Loan Account
| Feature | Details |
|--------|----------|
| Loan Amount | Given at account creation |
| EMI Payment | Handled through deposits |
| Interest | Applied monthly on outstanding |
| Loan Closure | Auto-closed when outstanding becomes zero |
| No Withdrawal | Not permitted |

---

## 🧱 Project Architecture (MVC)

┌──────────────────┐
│ BankView │ → Handles input/output (UI/menus)
└──────────────────┘
↓
┌──────────────────┐
│ BankController │ → Coordinates logic between View & Model
└──────────────────┘
↓
┌──────────────────┐
│ Bank │ → Holds array of BankAccount and main business logic
└──────────────────┘
↓
┌────────────────────────────────────────────┐
│ BankAccount (abstract) │
├────────────────────────────────────────────┤
│ SavingAccount │ CurrentAccount │ SalaryAccount │ LoanAccount │
└────────────────────────────────────────────┘
---

## ▶️ How to Run

1. Open project in **VS Code / IntelliJ / Eclipse**
2. Ensure **Java (JDK 8 or above)** is installed
3. Save all files inside **same package `bank`**
4. Run the **main class:**

---

## 📌 Tech Stack Used

| Technology | Usage |
|-----------|-------|
| Java | Core programming |
| OOP Concepts | Inheritance, Abstraction, Polymorphism |
| Arrays | Storage of accounts & transactions |
| MVC | Layer separation |
| Date / Time API | Managing deadlines and inactivity |

---

## 📝 Sample Output (Welcome Screen)

==============================================
WELCOME TO FIRSTBIT BANK
GROW IT BIT BY BIT
---

## 📂 File Contains

✔ Complete working console system  
✔ No SQL / No Collections — only **Arrays**  
✔ Beginner-friendly logic  

---

## 🤝 Credits

This project is designed for **learning core Java**, especially:
- Abstraction & inheritance
- Real-life banking rules
- Arrays & transaction storage
- Console-based application design

---

🔹 *Feel free to extend this project with GUI, database, or REST in future.*

