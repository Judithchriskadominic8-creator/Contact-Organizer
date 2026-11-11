import java.util.Scanner;

class Contact {
    private String contactId;
    private String name;
    private String phoneNo;
    private String companyName;
    private double salary;
    private String birthday;

    public Contact(String contactId, String name, String phoneNo, String companyName, double salary, String birthday) {
        this.contactId = contactId;
        this.name = name;
        this.phoneNo = phoneNo;
        this.companyName = companyName;
        this.salary = salary;
        this.birthday = birthday;
    }

    public String getContactId() { return contactId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhoneNo() { return phoneNo; }
    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
    public String getBirthday() { return birthday; }
    public void setBirthday(String birthday) { this.birthday = birthday; }
}

public class IFriendContactOrganizer{
    public static Contact[] contacts = new Contact[0];

    public final static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static void extendArrays() {
        Contact[] temp = new Contact[contacts.length + 1];
        for (int i = 0; i < contacts.length; i++) {
            temp[i] = contacts[i];
        }
        contacts = temp;
    }

    public static void deleteArrays(int index) {
        Contact[] temp = new Contact[contacts.length - 1];
        for (int i = 0, j = 0; i < contacts.length; i++) {
            if (i == index) continue;
            temp[j++] = contacts[i];
        }
        contacts = temp;
    }

    public static String genareteContactId(int num) {
        return String.format("C%04d", num);
    }

    public static boolean checkphoneNo(String phoneNo) {
        if (phoneNo.length() == 10 && phoneNo.charAt(0) == '0') {
            for (int i = 0; i < phoneNo.length(); i++) {
                if (!Character.isDigit(phoneNo.charAt(i))) return false;
            }
            return true;
        }
        return false;
    }

    public static boolean checksalary(double salary) {
        return salary >= 0;
    }

    public static boolean checkBirthday(String birthday) {
        if (birthday.length()!= 10) return false;
        if (birthday.charAt(4)!= '-' || birthday.charAt(7)!= '-') return false;
        for (int i = 0; i < birthday.length(); i++) {
            if (i == 4 || i == 7) continue;
            if (!Character.isDigit(birthday.charAt(i))) return false;
        }
        int year = Integer.parseInt(birthday.substring(0, 4));
        int month = Integer.parseInt(birthday.substring(5, 7));
        int day = Integer.parseInt(birthday.substring(8, 10));
        if (month < 1 || month > 12) return false;
        if (day < 1 || day > 31) return false;
        if (year > 2026) return false;
        if (year == 2026 && month > 9) return false;
        if (year == 2026 && month == 9 && day > 4) return false;
        return true;
    }

    public static int searchingContactIndex(String query) {
        for (int i = 0; i < contacts.length; i++) {
            if (contacts[i].getName().equalsIgnoreCase(query) || contacts[i].getPhoneNo().equals(query)) {
                return i;
            }
        }
        return -1;
    }

    public static void AddContacts() {
        Scanner input = new Scanner(System.in);
        char opt2 = 'Y';
        do {
            clearConsole();
            System.out.println("+--------------------------------------------------------------+");
            System.out.println("|                  Add Contact to the list                     |");
            System.out.println("+--------------------------------------------------------------+");
            System.out.println();
            String newId = genareteContactId(contacts.length + 1);
            System.out.println(newId);
            System.out.println("===============");
            System.out.println();
            System.out.print("Name : ");
            String name = input.next();
            String phoneNo = "";
            while (true) {
                System.out.print("Phone Number : ");
                phoneNo = input.next();
                if (checkphoneNo(phoneNo)) break;
                System.out.println("\n\tInvalid phone number...");
                System.out.print("Do you want to add phone number again (Y/N): ");
                char c = input.next().charAt(0);
                if (c == 'N' || c == 'n') return;
            }
            System.out.print("Company Name : ");
            String companyName = input.next();
            double salary = 0;
            while (true) {
                System.out.print("Salary : ");
                salary = input.nextDouble();
                if (checksalary(salary)) break;
                System.out.println("\n\tInvalid salary...");
                System.out.print("Do you want to add salary again (Y/N): ");
                char c = input.next().charAt(0);
                if (c == 'N' || c == 'n') return;
            }
            String bday = "";
            while (true) {
                System.out.print("B'Day(YYYY-MM-DD): ");
                bday = input.next();
                if (checkBirthday(bday)) break;
                System.out.println("\n\tInvalid Birthday...");
                System.out.print("Do you want to input birthday again (Y/N): ");
                char c = input.next().charAt(0);
                if (c == 'N' || c == 'n') return;
            }
            extendArrays();
            contacts[contacts.length - 1] = new Contact(newId, name, phoneNo, companyName, salary, bday);
            System.out.println("\n\tContact has been added successfully...\n");
            System.out.print("Do you want to add another Contact (Y/N): ");
            opt2 = input.next().charAt(0);
        } while (opt2 == 'Y' || opt2 == 'y');
    }

    public static void UpdateContacts() {
        Scanner input = new Scanner(System.in);
        char retry = 'Y';
        do {
            clearConsole();
            System.out.println("+--------------------------------------------------------------+");
            System.out.println("|                      UPDATE Contact                          |");
            System.out.println("+--------------------------------------------------------------+");
            System.out.println();
            System.out.print("Search Contact by Name or Phone Number - ");
            String query = input.next();
            int index = searchingContactIndex(query);
            if (index == -1) {
                System.out.println("\n\tNo contact found for " + query + "...");
            } else {
                System.out.println("\n\tContact ID : " + contacts[index].getContactId());
                System.out.println("\tName : " + contacts[index].getName());
                System.out.println("\tPhone Number : " + contacts[index].getPhoneNo());
                System.out.println("\tCompany Name : " + contacts[index].getCompanyName());
                System.out.println("\tSalary : " + contacts[index].getSalary());
                System.out.println("\tB'Day : " + contacts[index].getBirthday());
                System.out.println("\nWhat do you want to update...");
                System.out.println("\t[01] Name");
                System.out.println("\t[02] Phone Number");
                System.out.println("\t[03] Company Name");
                System.out.println("\t[04] Salary");
                System.out.print("Enter an option -> ");
                int op = input.nextInt();
                switch (op) {
                    case 1: System.out.print("Input new name - "); contacts[index].setName(input.next()); break;
                    case 2: while(true){ System.out.print("Input new phone - "); String p=input.next(); if(checkphoneNo(p)){contacts[index].setPhoneNo(p); break;} System.out.println("Invalid phone..."); } break;
                    case 3: System.out.print("Input new company - "); contacts[index].setCompanyName(input.next()); break;
                    case 4: while(true){ System.out.print("Input new salary - "); double s=input.nextDouble(); if(checksalary(s)){contacts[index].setSalary(s); break;} System.out.println("Invalid salary..."); } break;
                }
                System.out.println("\n\tContact has been updated successfully...");
            }
            System.out.print("\nDo you want to update another Contact (Y/N): ");
            retry = input.next().charAt(0);
        } while (retry == 'Y' || retry == 'y');
    }

    public static void DeleteContacts() {
        Scanner input = new Scanner(System.in);
        char retry = 'Y';
        do {
            clearConsole();
            System.out.println("+--------------------------------------------------------------+");
            System.out.println("|                        DELETE Contact                        |");
            System.out.println("+--------------------------------------------------------------+");
            System.out.println();
            System.out.print("Search Contact by Name or Phone Number - ");
            String query = input.next();
            int index = searchingContactIndex(query);
            if (index == -1) {
                System.out.println("\n\tNo contact found for " + query + "...");
            } else {
                System.out.println("\n\tContact ID : " + contacts[index].getContactId());
                System.out.println("\tName : " + contacts[index].getName());
                System.out.println("\tPhone Number : " + contacts[index].getPhoneNo());
                System.out.print("Do you want to delete this Contact (Y/N): ");
                char confirm = input.next().charAt(0);
                if (confirm == 'Y' || confirm == 'y') {
                    deleteArrays(index);
                    System.out.println("\n\tContact has been deleted successfully...");
                }
            }
            System.out.print("\nDo you want to delete another Contact (Y/N): ");
            retry = input.next().charAt(0);
        } while (retry == 'Y' || retry == 'y');
    }

    public static void SearchContacts() {
        Scanner input = new Scanner(System.in);
        char retry = 'Y';
        do {
            clearConsole();
            System.out.println("+--------------------------------------------------------------+");
            System.out.println("|                     SEARCH Contact                           |");
            System.out.println("+--------------------------------------------------------------+");
            System.out.println();
            System.out.print("Search Contact by Name or Phone Number - ");
            String query = input.next();
            int index = searchingContactIndex(query);
            if (index == -1) {
                System.out.println("\n\tNo contact found for " + query + "...");
            } else {
                System.out.println("\n\tContact ID : " + contacts[index].getContactId());
                System.out.println("\tName : " + contacts[index].getName());
                System.out.println("\tPhone Number : " + contacts[index].getPhoneNo());
                System.out.println("\tCompany Name : " + contacts[index].getCompanyName());
                System.out.println("\tSalary : " + contacts[index].getSalary());
                System.out.println("\tB'Day : " + contacts[index].getBirthday());
            }
            System.out.print("\nDo you want to search another contact (Y/N): ");
            retry = input.next().charAt(0);
        } while (retry == 'Y' || retry == 'y');
    }

    public static void printTable(int[] indices) {
        System.out.println("+-----------------------------------------------------------------------------------+");
        System.out.println("| Contact ID | Name | Phone Number | Company | Salary |      Birthday               |");
        System.out.println("+-----------------------------------------------------------------------------------+");
        for (int idx : indices) {
            System.out.printf("| %-10s | %-14s | %-12s | %-12s | %-10.1f | %-10s |\n",
                    contacts[idx].getContactId(), contacts[idx].getName(), contacts[idx].getPhoneNo(),
                    contacts[idx].getCompanyName(), contacts[idx].getSalary(), contacts[idx].getBirthday());
        }
        System.out.println("+-----------------------------------------------------------------------------------+");
    }

    public static void ListContacts() {
        Scanner input = new Scanner(System.in);
        clearConsole();
        System.out.println("+--------------------------------------------------------------+");
        System.out.println("|                        SORT Contact                          |");
        System.out.println("+--------------------------------------------------------------+");
        System.out.println("\t[01] Sorting by Name");
        System.out.println("\t[02] Sorting by Salary");
        System.out.println("\t[03] Sorting by Birthday");
        System.out.print("Enter an option -> ");
        int option = input.nextInt();
        int[] indices = new int[contacts.length];
        for (int i = 0; i < indices.length; i++) indices[i] = i;

        if (option == 1) {
            for (int i = 0; i < indices.length - 1; i++) {
                for (int j = 0; j < indices.length - 1 - i; j++) {
                    if (contacts[indices[j]].getName().compareToIgnoreCase(contacts[indices[j+1]].getName()) > 0) {
                        int t = indices[j]; indices[j] = indices[j+1]; indices[j+1] = t;
                    }
                }
            }
            clearConsole();
            System.out.println("--- List Contact by Name ---");
            printTable(indices);
        } else if (option == 2) {
            for (int i = 0; i < indices.length - 1; i++) {
                for (int j = 0; j < indices.length - 1 - i; j++) {
                    if (contacts[indices[j]].getSalary() > contacts[indices[j+1]].getSalary()) {
                        int t = indices[j]; indices[j] = indices[j+1]; indices[j+1] = t;
                    }
                }
            }
            clearConsole();
            System.out.println("--- List Contact by Salary ---");
            printTable(indices);
        } else if (option == 3) {
            for (int i = 0; i < indices.length - 1; i++) {
                for (int j = 0; j < indices.length - 1 - i; j++) {
                    if (contacts[indices[j]].getBirthday().compareTo(contacts[indices[j+1]].getBirthday()) > 0) {
                        int t = indices[j]; indices[j] = indices[j+1]; indices[j+1] = t;
                    }
                }
            }
            clearConsole();
            System.out.println("--- List Contact by Birthday ---");
            printTable(indices);
        }
        System.out.print("\nDo you want to go Home Page (Y/N): ");
        input.next();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while (true) {
            clearConsole();
            System.out.println(" IFRIEND CONTACT ORGANIZER - OOP VERSION");
            System.out.println("=================================================================");
            System.out.println(" [01] ADD Contacts");
            System.out.println(" [02] UPDATE Contacts");
            System.out.println(" [03] DELETE Contacts");
            System.out.println(" [04] SEARCH Contacts");
            System.out.println(" [05] LIST Contacts");
            System.out.println(" [06] Exit");
            System.out.print("\nEnter an option to continue -> ");
            int option = input.nextInt();
            switch (option) {
                case 1: AddContacts(); break;
                case 2: UpdateContacts(); break;
                case 3: DeleteContacts(); break;
                case 4: SearchContacts(); break;
                case 5: ListContacts(); break;
                case 6: System.exit(0); break;
            }
        }
    }
}
