package edu.uga.cs.countryquiz;

/** Domain Class
 *POJO Class (Plain Old Java Object)
 * Represents a the question and correct answer combination for a quiz question*/
public class Country {

    //Private members
    private long id;
    //Continent that will be the basis for the question
    private String continent;
    //Country that is the correct answer for the question
    private String country;

    /**
     * Default constructor*/
    public Country(){
        //Set to -1 to show this question hasn't been "created", semantically speaking
        this.id = -1;
        this.continent = null;
        this.country = null;
    }

    //Overloaded Constructor w/ Parameters
    /**
     * Constructor
     * @param continent The continent to set the private member continent to
     * @param country The continent to set the private member continent to*/
    public Country(String country, String continent){
        this.id = -1;
        this.continent = continent;
        this.country = country;
    }

    /**
     * Method that returns the id of a question
     * @return id The id of the question*/
    public long getId() {
        return id;
    }

    /**
     * Method that returns the continent/answer for a question
     * @return continent The string representing a question's continent*/
    public String getContinent() {
        return continent;
    }

    /**
     * Method that returns the country for a question
     * @return country The string representing a question's country*/
    public String getCountry() {
        return country;
    }

    /**
     * Method that sets private member id to {id}
     * @param id The long to set the private member value to*/
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Method that sets private member continent to {continent}
     * @param continent The string to set the private member value to*/
    public void setContinent(String continent) {
        this.continent = continent;
    }

    /**
     * Method that sets private member country to {country}
     * @Param country The string to set the private member value to*/
    public void setCountry(String country) {
        this.country = country;
    }



    public String toString()
    {
        return id + ": "  + " " + country + " " + continent;
    }
}