import DataMangament.CompanyPhoneBookEntry;
import DataMangament.RDF_Graph_Manager;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Scanner;

public class CompanyPhoneBookApp {
    public static void main(String[] args) {
        RDF_Graph_Manager graph = new RDF_Graph_Manager();
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("*****************   Welcome to Mini-Herold (Company edition)   **********************");
        System.out.println("------------------------------------------------------------------------------------------\n");

        Scanner input = new Scanner(System.in);
        String nextRound = "";

        while(!nextRound.equals("q")) {
            System.out.println("##########################################################################################");
            System.out.println("Let's search an company in Austria!");
            System.out.println("Please provide at least one of the following fields for the search \n(e.g. legal name, email, businessField, country, postal code, street name, street number).");
            System.out.println("(If nothing is given all organizations of this this phone book will be listed.)");
            System.out.println("Enter \"r\" to restart search.");
            System.out.println("------------------------------------------------------------------------------------------\n");
            System.out.print("Enter legal name: ");
            String legalNameInput = input.nextLine();  // Read user input
            if (legalNameInput.equals("r")) {
                System.out.println("------ Restart search ------------------>");
                continue;
            }

            System.out.print("Enter email: ");
            String emailInput = input.nextLine();
            if (emailInput.equals("r")) {
                continue;
            }

            System.out.print("Enter business field: ");
            String businessFieldInput = input.nextLine();
            if (businessFieldInput.equals("r")) {
                continue;
            }

            /*System.out.print("Enter country: ");
            String countryInput = input.nextLine();
            if (countryInput.equals("r")) {
                continue;
            }*/

            System.out.print("Enter postal code: ");
            String postalCodeInput = input.nextLine();
            if (postalCodeInput.equals("r")) {
                continue;
            } else if (!postalCodeInput.equals("") && !StringUtils.isNumeric(postalCodeInput)) {
                System.out.print("Please enter an integer as postal code. Try again!\nEnter postal code: ");
                postalCodeInput = input.nextLine();
            }

            System.out.print("Enter street name: ");
            String streetInput = input.nextLine();
            if (streetInput.equals("r")) {
                continue;
            }

            System.out.print("Enter street number: ");
            String streetNoInput = input.nextLine();
            if (streetNoInput.equals("r")) {
                continue;
            } else if (!streetNoInput.equals("") && !StringUtils.isNumeric(streetNoInput)) {
                System.out.print("Invalid input: please enter an integer as street number. Try again!\nEnter street number: ");
                streetNoInput = input.nextLine();
            }


            //List<CompanyPhoneBookEntry> entries = graph.getEntries();
            List<CompanyPhoneBookEntry> entries = graph.getEntriesBy(legalNameInput, emailInput, businessFieldInput, "Austria", postalCodeInput, streetInput, streetNoInput);
            if(!entries.isEmpty()){
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.format("| %-20s | %-25s | %-20s | %-20s | %-20s | %-15s | %-10s | %-25s |%-10s |\n", "legalName", "email", "phoneNo", "website", "businessField", "country", "postalCode", "street", "streetNo");
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            } else {
                System.out.println("\n-----------> Sorry, no results were found\n");
            }
            for (CompanyPhoneBookEntry entry: entries) {
                System.out.println(entry.getPrettyString());
            }
            System.out.print("\nPrint \"q\" (quit) or press some arbitrary key: ");
            nextRound = input.nextLine();
        }
    }
}