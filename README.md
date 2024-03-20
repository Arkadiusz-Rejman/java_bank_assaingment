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
![image](https://github.com/Arkadiusz-Rejman/java_bank_assaingment/assets/78605732/f847f9ce-475c-49fe-a4f0-8da04680c887)

Login attpemt with bad login typed:
![image](https://github.com/Arkadiusz-Rejman/java_bank_assaingment/assets/78605732/337e2c47-af74-408f-b297-ef07fd5fa4b7)

Succesfully logged and redirect to main window:
![image](https://github.com/Arkadiusz-Rejman/java_bank_assaingment/assets/78605732/efa0ffdd-28a8-4fea-b326-f40515413da2)

Adding a new wallet to account from main window:
![image](https://github.com/Arkadiusz-Rejman/java_bank_assaingment/assets/78605732/4be89e9f-be43-4c02-80ab-46f5bb1722ac)

Changing wallet name, wallet option window on left:
![image](https://github.com/Arkadiusz-Rejman/java_bank_assaingment/assets/78605732/f9eb0696-cee7-41b3-95af-e4c72a083e34)

Changing wallet status:
![image](https://github.com/Arkadiusz-Rejman/java_bank_assaingment/assets/78605732/bf6c9fde-6168-4241-9f30-5457505415e8)

Wallet status and name changed on main screen:
![image](https://github.com/Arkadiusz-Rejman/java_bank_assaingment/assets/78605732/bbb13ff9-6afc-4969-9f9d-dea5e21c79b5)

Window with user wallets list:
![image](https://github.com/Arkadiusz-Rejman/java_bank_assaingment/assets/78605732/8fb83f79-ed1d-48be-920f-0e66553e984e)

The CurrencyRates window shows the solution (here case the EUR and USD exchange rate) in relation to the current currency wallet, in the arrangement of some screens a simple currency calculator:
![image](https://github.com/Arkadiusz-Rejman/java_bank_assaingment/assets/78605732/e74ad76a-69bf-4a74-899e-841636c5644d)

The TransactionHistory window displays the entire transaction history user at the top of the screen, but in this case, the active wallet is not has no history, so a message is displayed stating this:
![image](https://github.com/Arkadiusz-Rejman/java_bank_assaingment/assets/78605732/ff9254de-06cd-40f3-917d-c937325b919d)

Logged in with another user used for testing application from the beginning of its creation, its wallet has a rich history:
![image](https://github.com/Arkadiusz-Rejman/java_bank_assaingment/assets/78605732/24687366-29c5-41aa-89a4-67a3217d57d1)

Attempt to make a transaction to a user without a currency account:
![image](https://github.com/Arkadiusz-Rejman/java_bank_assaingment/assets/78605732/2f534afe-8aa5-44a2-9c79-660337c12f7b)

















