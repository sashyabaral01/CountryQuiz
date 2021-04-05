package edu.uga.cs.countryquiz;

/***
 * Returns a POJO class
 */
public class Country {

    //Private members
    private long id;
    //Continent that will be the basis for the question
    private String continent;
    //Country that is the correct answer for the question
    private String country;

    /***
     * Returns a country
     */
    public Country(){
            this.id = -1;
        this.continent = null;
        this.country = null;
    }


    /***
     * @param country,continent
     */
    public Country(String country, String continent){
        this.id = -1;
        this.continent = continent;
        this.country = country;
    }

    /***
     * Method that returns the id of a question
     * @return */
    public long getId() {
        return id;
    }

    /***
     * Method that returns the continent/answer for a question
     * @return
      */
    public String getContinent() {
        return continent;
    }

    /***
     * Gets the country
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /***
     * Sets the id
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /***
     * Sets the continent
     * @param continent
     */
    public void setContinent(String continent) {
        this.continent = continent;
    }


    /***
     * Sets the country
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }


    /***
     * Creates a string
     * @return
     */
    public String toString()
    {
        return  country + " " + continent;
    }
}