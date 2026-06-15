package com.mcverse.jobify.model;

public class Region {
  private String countryCode;
  private String country;
  private String state;
  private String zipCode;
  private String city;
  private String continent;
  public Region(String countryCode, String country, String state, String zipCode, String city, String continent) {
    this.countryCode = countryCode;
    this.country = country;
    this.state = state;
    this.zipCode = zipCode;
    this.city = city;
    this.continent = continent;
  }
  public String getCountryCode() {
    return countryCode;
  }
  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }
  public String getCountry() {
    return country;
  }
  public void setCountry(String country) {
    this.country = country;
  }
  public String getState() {
    return state;
  }
  public void setState(String state) {
    this.state = state;
  }
  public String getZipCode() {
    return zipCode;
  }
  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }
  public String getCity() {
    return city;
  }
  public void setCity(String city) {
    this.city = city;
  }
  public String getContinent() {
    return continent;
  }
  public void setContinent(String continent) {
    this.continent = continent;
  }
}
