# OCafe Kiosk 

This application simulates a virtual food ordering system for a cafe. It features an interactive menu comprised of my favourite 
items from brunch cafes I've visited around the world! Hopefully the OCafe Kiosk can inspire you to explore new cuisines, 
or to take notice of local cafes that artfully combine unique cultural flavours with the warm ambiance of a traditional 
cafe, and appreciate the style that influenced this project!

## Demo

https://user-images.githubusercontent.com/74160990/118728615-7d814000-b802-11eb-841c-e74ab9e0ad50.mp4



## Set Up
A Java Runtime Environment (JRE) is required to run the application, available for download [here](https://www.oracle.com/ca-en/java/technologies/javase-jre8-downloads.html).


1. Clone repository using any method. To clone using git, enter the following in the Terminal/Command Prompt:
```bash
git clone https://github.com/vanessaip/OCafeProject.git
```
alternatively, the zip file can be found at the green **Code** button   
    
2. Navigate to the project file:
```bash
cd OCafeProject
```
    
3. Run the application:
```bash
java -jar out/artifacts/OCafe_jar/OCafe.jar
```


## Features
### Menu
- Browse menu items by category
- Select an item to view its image, details, and available customizations
- Customize menu items by selecting add-ons, size options etc. (unique to each item)
- Modify the quantity of an item
- View updated price dynamically

### Order
- Add items with desired customizations to the current order
- View summary of all the items added to the order
- Change the quantity of items in the order
- Remove items from the order
- Place the order

### Account
- Create an account (optional)
- Save the current order to the account
- View previous orders sorted by date 
- Clear order history
- Sign in to access data from an account after relaunching the app or signing out
- Sign out 
