package DataMangament;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.XSD;
import org.apache.jena.query.* ;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RDF_Graph_Manager {

    private final Model model;
    String exv;
    String exi;
    private int currentFreeID;

    public RDF_Graph_Manager() {
        // create an empty Model
        model = ModelFactory.createDefaultModel();
        currentFreeID = 0;
        if(new File("CompPhoneBook.rdf").isFile()){
            model.read("CompPhoneBook.rdf", "RDFXML");
        } else {
            createModelGraph();
        }
    }

    public List<CompanyPhoneBookEntry> getEntries() {
        String prefixes = "PREFIX exv:<http://example.org/vocabulary#> PREFIX exi:<http://example.org/instances#> PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#> PREFIX xsd:<http://www.w3.org/2001/XMLSchema#> PREFIX owl:<http://www.w3.org/2002/07/owl#>";
        String queryString = prefixes + " SELECT * WHERE { ?CompPhoneBookEntry rdf:type exv:CompanyPhoneBookEntry. ?CompPhoneBookEntry exv:legalName ?legalName.  ?CompPhoneBookEntry exv:email ?email. ?CompPhoneBookEntry exv:phoneNo ?phoneNo. ?CompPhoneBookEntry exv:website ?website. ?CompPhoneBookEntry exv:businessField ?businessField. ?CompPhoneBookEntry exv:address _:b0. _:b0 rdf:type exv:PostalAddress. _:b0 exv:country ?country. _:b0 exv:postalCode ?postalCode. _:b0 exv:street ?street. _:b0 exv:streetNo ?streetNo.}" ;
        Query query = QueryFactory.create(queryString);
        List<CompanyPhoneBookEntry> entries = new ArrayList<>();
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution() ;
                CompanyPhoneBookEntry entry = new CompanyPhoneBookEntry(soln.get("legalName").asLiteral().getString(), soln.get("email").asLiteral().getString(), soln.get("phoneNo").asLiteral().getString(), soln.get("website").asLiteral().getString(), soln.get("businessField").asLiteral().getString(), soln.get("country").asLiteral().getString(), soln.get("postalCode").asLiteral().getInt(), soln.get("street").asLiteral().getString(), soln.get("streetNo").asLiteral().getInt());
                entries.add(entry);
            }
        }
        return entries;
    }

    public List<CompanyPhoneBookEntry> getEntriesBy(String legalNameVal, String email, String businessField, String country, String postalCode, String street, String streetNo) {
        String prefixes = "PREFIX exv:<http://example.org/vocabulary#> PREFIX exi:<http://example.org/instances#> PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#> PREFIX xsd:<http://www.w3.org/2001/XMLSchema#> PREFIX owl:<http://www.w3.org/2002/07/owl#>";
        String queryString = prefixes + " SELECT * WHERE { ?CompPhoneBookEntry rdf:type exv:CompanyPhoneBookEntry. ?CompPhoneBookEntry exv:address _:b0. _:b0 rdf:type exv:PostalAddress." ;
        String stringtype = "\"^^xsd:string.";
        String inttype = "\"^^xsd:integer.";

        queryString += " ?CompPhoneBookEntry exv:legalName ?legalName.";
        if (!legalNameVal.equals(""))
        {
            queryString += " ?CompPhoneBookEntry exv:legalName \"" + legalNameVal + stringtype;
        }

        queryString += " ?CompPhoneBookEntry exv:email ?email.";
        if (!email.equals(""))
        {
            queryString += " ?CompPhoneBookEntry exv:email \"" + email + stringtype;
        }

        queryString += " ?CompPhoneBookEntry exv:phoneNo ?phoneNo.";
        queryString += " ?CompPhoneBookEntry exv:website ?website.";

        queryString += " ?CompPhoneBookEntry exv:businessField ?businessField.";
        if (!businessField.equals(""))
        {
            queryString += " ?CompPhoneBookEntry exv:businessField \"" + businessField + stringtype;
        }

        queryString += " _:b0 exv:country ?country.";
        if (!country.equals(""))
        {
            queryString += " _:b0 exv:country \"" + country + stringtype;
        }

        queryString += " _:b0 exv:postalCode ?postalCode.";
        if (!postalCode.equals(""))
        {
            queryString += " _:b0 exv:postalCode \"" + postalCode + inttype;
        }

        queryString += " _:b0 exv:street ?street.";
        if (!street.equals(""))
        {
            queryString += " _:b0 exv:street \"" + street + stringtype;
        }

        queryString += " _:b0 exv:streetNo ?streetNo.";
        if (!streetNo.equals(""))
        {
            queryString += " _:b0 exv:streetNo \"" + streetNo + inttype;
        }

        Query query = QueryFactory.create(queryString + "}");
        List<CompanyPhoneBookEntry> entries = new ArrayList<>();
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution() ;
                CompanyPhoneBookEntry entry = new CompanyPhoneBookEntry(soln.get("legalName").asLiteral().getString(), soln.get("email").asLiteral().getString(), soln.get("phoneNo").asLiteral().getString(), soln.get("website").asLiteral().getString(), soln.get("businessField").asLiteral().getString(), soln.get("country").asLiteral().getString(), soln.get("postalCode").asLiteral().getInt(), soln.get("street").asLiteral().getString(), soln.get("streetNo").asLiteral().getInt());
                entries.add(entry);
            }
        }
        return entries;
    }

    public void createModelGraph() {

        // prefixes
        String source = "http://example.org/";
        exi = source + "instances#";
        exv = source + "vocabulary#";

        model.setNsPrefix( "exi", exi );
        model.setNsPrefix( "exv", exv );

        // schema
        Resource CompanyPhoneBookEntry = model.createResource(exv + "CompanyPhoneBookEntry")
                                                .addProperty(RDF.type, RDFS.Class);
        Resource PostalAddress = model.createResource(exv + "PostalAddress")
                .addProperty(RDF.type, RDFS.Class);

        Property legalName = model.createProperty(exv + "legalName");
        legalName.addProperty(RDFS.domain, CompanyPhoneBookEntry);
        legalName.addProperty(RDFS.range, XSD.xstring);
        Property address = model.createProperty(exv + "address");
        address.addProperty(RDFS.domain, CompanyPhoneBookEntry);
        address.addProperty(RDFS.range, PostalAddress);
        Property email = model.createProperty(exv + "email");
        email.addProperty(RDFS.domain, CompanyPhoneBookEntry);
        email.addProperty(RDFS.range, XSD.xstring);
        Property phoneNo = model.createProperty(exv + "phoneNo");
        phoneNo.addProperty(RDFS.domain, CompanyPhoneBookEntry);
        phoneNo.addProperty(RDFS.range, XSD.xstring);
        Property website = model.createProperty(exv + "website");
        website.addProperty(RDFS.domain, CompanyPhoneBookEntry);
        website.addProperty(RDFS.range, XSD.xstring);
        Property businessField = model.createProperty(exv + "businessField");
        businessField.addProperty(RDFS.domain, CompanyPhoneBookEntry);
        businessField.addProperty(RDFS.range, XSD.xstring);

        Property country = model.createProperty(exv + "country");
        country.addProperty(RDFS.domain, PostalAddress);
        country.addProperty(RDFS.range, XSD.xstring);
        Property postalCode = model.createProperty(exv + "postalCode");
        postalCode.addProperty(RDFS.domain, PostalAddress);
        postalCode.addProperty(RDFS.range, XSD.integer);
        Property street = model.createProperty(exv + "street");
        street.addProperty(RDFS.domain, PostalAddress);
        street.addProperty(RDFS.range, XSD.xstring);
        Property streetNo = model.createProperty(exv + "streetNo");
        streetNo.addProperty(RDFS.domain, PostalAddress);
        streetNo.addProperty(RDFS.range, XSD.integer);

        // instances/individuals
        addInstance("Happy and Co.", "Austria", 1150, "Mattstraße", 93, "blub@happyandco.at", "happyandco.at", "+43 006 2039402/2", "Balloon Business");
        addInstance("Florist Gump", "Austria", 1150, "Maria-Helfer-Straße", 103, "blub@floristgump.at", "floristgump.at", "+43 006 384265/12", "Flower Business");
        addInstance("Lord of the Wings", "Austria", 4040, "Eichenweg", 4, "blub@lordofthewings.at", "lordofthewings.at", "+43 664 364251/1", "Restaurant");
        addInstance("Thai Tanic", "Austria", 4040, "Weidengasse", 19, "blub@thaitanic.at", "thaitanic.at", "+43 664 367124/3", "Restaurant");
        addInstance("Jack the Ripperl", "Austria", 4040, "Buschwindröschen Straße", 88, "blub@jacktheripperl.at", "jacktheripperl.at", "+43 664 361123/3", "Restaurant");
        addInstance("Burgerista", "Austria", 4040, "Platz-Da-Straße", 10, "blub@burgerista.at", "burgerista.at", "+43 000 114523/3", "Restaurant");
        addInstance("Burgerista", "Austria", 1150, "Helden-Straße", 67, "blub@burgerista.at", "burgerista.at", "+43 000 114524/2", "Restaurant");
        addInstance("Burgerista", "Austria", 7000, "Burggasse", 99, "blub@burgerista.at", "burgerista.at", "+43 000 114599/1", "Restaurant");
        addInstance("Curl up & Dye", "Austria", 7000, "Lochboden", 85, "blub@curlAndDye.at", "curlAndDye.at", "+43 123 531762/1", "Hair Studio");
        addInstance("Abra Kebabra", "Austria", 3350, "Strenkberg", 47, "blub@abrakebabra.at", "abrakebabra.at", "+43 000 633459/9", "Restaurant");


        //model.write(System.out);
        try {
            OutputStream out = new FileOutputStream("CompPhoneBook.rdf");
            model.write(out, "RDFXML");
        } catch (Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    private void addInstance(String legalNameVal, String countryVal, int postalCodeVal, String streetVal, int streetNoVal, String emailVal, String websiteVal, String phoneNoVal, String businessFieldVal){
        model.createResource(exi + "CompanyPhoneBookEntry" + currentFreeID)
                .addProperty(RDF.type, model.getResource(exv + "CompanyPhoneBookEntry"))
                .addProperty(model.getProperty(exv + "legalName"), legalNameVal)
                .addProperty(model.getProperty(exv + "address"), model.createResource()                                                                        //blank node
                        .addProperty(RDF.type, model.getResource(exv + "PostalAddress"))
                        .addProperty(model.getProperty(exv + "country"), model.createTypedLiteral(countryVal, XSDDatatype.XSDstring))
                        .addProperty(model.getProperty(exv + "postalCode"), model.createTypedLiteral(postalCodeVal, XSDDatatype.XSDinteger))
                        .addProperty(model.getProperty(exv + "street"), model.createTypedLiteral(streetVal, XSDDatatype.XSDstring))
                        .addProperty(model.getProperty(exv + "streetNo"), model.createTypedLiteral(streetNoVal, XSDDatatype.XSDinteger)))
                .addProperty(model.getProperty(exv + "email"), emailVal)
                .addProperty(model.getProperty(exv + "website"), websiteVal)
                .addProperty(model.getProperty(exv + "phoneNo"), phoneNoVal)
                .addProperty(model.getProperty(exv + "businessField"), businessFieldVal);
        currentFreeID++;
    }
}
