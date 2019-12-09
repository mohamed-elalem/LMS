//This is a note from the team responsible for this project.
//It tells the professor about any extra work that was done or other things
//that need to be mentioned.

Mohamed Elalem:

Hou XSron:
UML:
	Use Case Diagram
	Add A Book Copy
	Add Library Member
Implementation:
	Start.java: update menu
	1. Add new library member:
		LibraryMemberWindow.java
		DataAccessFacade.java: update new method saveNewMember
	2. Add A Book Copy:
		BookCopyWindow.java
		DataAccessFacade.java: update new method saveBooks 
Hoang Thao Nguyen:
UML:
	Class diagram
	Add new book sequence diagram
	Print checkout record sequence diagram
	Check overdue sequence diagram
Implementation:
	TestData.java: Add more example for overdue entry
	Start.java: update menu
	1. Add new book feature:	
		AddBookWindow.java
		BookController.java
		BookControllerImpl.java
		InvalidBookException.java
		DataAccessFacade.java: update new mothed save new book
	2. Print checkout record
		PrintCheckoutReccordWindow.java
		CheckoutRecordController.java
		CheckoutRecordControllerImpl.java
		MemberNotFoundException.java
	3. Check overdue
		DetermineOverdueWindow.java
		CheckoutRecordController.java
		CheckoutRecordControllerImpl.java
		BookNotFoundException.java
		BookCopyNotFoundException.java
		
		
	
	