package edu.uga.cs.countryquiz;


public class Country {
    int id;
    String countryName;
    String countryContinent;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryContinent() {
        return countryContinent;
    }

    public void setCountryContinent(String countryContinent) {
        this.countryContinent = countryContinent;
    }
    public Country(){};

    public Country(String countryName, String countryContinent){
        this.countryContinent = countryContinent;
        this.countryName=countryName;

    }
    public Country(int id, String countryName, String countryContinent){
        this.countryContinent = countryContinent;
        this.countryName=countryName;
        this.id=id;

    }





}
