package DataMangament;


public class CompanyPhoneBookEntry {
    private final String legalName;
    private final String email;
    private final String phoneNo;
    private final String website;
    private final String businessField;
    private final String country;
    private final int postalCode;
    private final String street;
    private final int streetNo;

    public CompanyPhoneBookEntry(String legalName, String email, String phoneNo, String website, String businessField, String country, int postalCode, String street, int streetNo) {
        this.legalName = legalName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.website = website;
        this.businessField = businessField;
        this.country = country;
        this.postalCode = postalCode;
        this.street = street;
        this.streetNo = streetNo;
    }

    public String getPrettyString(){
       return String.format("| %-20s | %-25s | %-20s | %-20s | %-20s | %-15s | %-10d | %-25s |%-10d |", legalName, email, phoneNo, website, businessField, country, postalCode, street, streetNo);
    }

    public String getLegalName() {
        return legalName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getWebsite() {
        return website;
    }

    public String getBusinessField() {
        return businessField;
    }

    public String getCountry() {
        return country;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public String getStreet() {
        return street;
    }

    public int getStreetNo() {
        return streetNo;
    }
}
