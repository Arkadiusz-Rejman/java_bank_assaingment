Welcome, this is a bank management app made in Java.

To run this project, you need to download it and import the database structure, which is located in the "database_struct_folder" within the repository. We used MySQL Workbench to manage the database. You can find the dump (.sql) file in the mentioned folder. There is no need to populate it with any data for the project to work; simply create your new account when running our app. All external libraries are managed by Maven.

To set the database address, login, and password for the admin account in MySQL Workbench, use the three variables that you can find in the mySQL_class.java class as shown below:

![image](https://github.com/Arkadiusz-Rejman/java_bank_assaingment/assets/78605732/0cf31f86-2911-4463-87cb-8f1c1026507f)

To start the program, run it in an IDE from the LoginGUI class. From here, everything should work properly, but if you're having some problems or have no time, there is a graphic documentation below showing how the application works and its possibilities.


Creating new user:

![image](https://github.com/Arkadiusz-Rejman/java_bank_assaingment/assets/78605732/07f5fb42-d044-433f-a526-05a03c30d414)

Trying to register with login that already exist:

![image](https://github.com/Arkadiusz-Rejman/java_bank_assaingment/assets/78605732/9f2712be-795e-495c-9096-01d6d787f3e4)

Password re-typed with a mistake:

![image](https://github.com/Arkadiusz-Rejman/java_bank_assaingment/assets/78605732/62c273cc-722e-412d-9d14-03302f579522)

Login attpemt with bad login typed:

![image](https://github.com/Arkadiusz-Rejman/java_bank_assaingment/assets/78605732/337e2c47-af74-408f-b297-ef07fd5fa4b7)

Succesfully logged and redirect to main window:

![image](https://github.com/Arkadiusz-Rejman/java_bank_assaingment/assets/78605732/10b4bf83-ab5f-41cb-91cd-3c8d64cee048)

Adding a new wallet to account from main window:

![image](https://github.com/Arkadiusz-Rejman/java_bank_assaingment/assets/78605732/22ff3ef2-05c1-401d-a836-f27e3a2aa713)

Changing wallet name, wallet option window on left:

![image](https://github.com/Arkadiusz-Rejman/java_bank_assaingment/assets/78605732/a26919b9-f4d5-460d-9ae8-5f745092506a)

Changing wallet status:

![image](https://github.com/Arkadiusz-Rejman/java_bank_assaingment/assets/78605732/3ef3f0c8-2a50-4942-9d05-46fa5b360a84)

Wallet status and name changed on main screen:

![image](https://github.com/Arkadiusz-Rejman/java_bank_assaingment/assets/78605732/8377461b-6966-4508-9b79-be61c7a3ba30)

Window with user wallets list:

![image](https://github.com/Arkadiusz-Rejman/java_bank_assaingment/assets/78605732/8fb83f79-ed1d-48be-920f-0e66553e984e)

The CurrencyRates window shows the solution (here case the EUR and USD exchange rate) in relation to the current currency wallet, in the arrangement of some screens a simple currency calculator:

![image](https://github.com/Arkadiusz-Rejman/java_bank_assaingment/assets/78605732/e74ad76a-69bf-4a74-899e-841636c5644d)

The TransactionHistory window displays the entire transaction history user at the top of the screen, but in this case, the active wallet is not has no history, so a message is displayed stating this:

![image](https://github.com/Arkadiusz-Rejman/java_bank_assaingment/assets/78605732/5c732eba-2624-4e3f-ba4d-1124ec8c51bb)


Logged in with another user used for testing application from the beginning of its creation, its wallet has a rich history:

![image](https://github.com/Arkadiusz-Rejman/java_bank_assaingment/assets/78605732/24687366-29c5-41aa-89a4-67a3217d57d1)

Attempt to make a transaction to a user without a currency account:

![image](https://github.com/Arkadiusz-Rejman/java_bank_assaingment/assets/78605732/32ff5a1b-dede-47c5-9713-90153b5eed43)


















