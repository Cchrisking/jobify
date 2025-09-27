package com.mcverse.jobify.model;

import java.util.ArrayList;
import java.util.Date;

abstract class User {
  private String id;
  private String name;
  private String lastName;
  private String passWord;
  private Date creationDate;
  private ArrayList<Date> lastUpdates=new ArrayList<>();
  public User(String id, String name, String lastName, String passWord, Date creationDate, Date lastUpdate){
    this.id=id;
    this.name=name;
    this.lastName=lastName;
    this.passWord = lastName;
    this.creationDate= creationDate;
    this.lastUpdates.add(lastUpdate);
  }
  public String getId() {
    return this.id;
  }
  public String getLastName() {
    return this.lastName;
  }
  public String getName(){
    return this.name;
  }
  public String getPassWord(){
    return this.passWord;
  }
  public Date getCreationDate(){
    return this.creationDate;
  }
  public Date getLastUpdate(){
    return this.lastUpdates.getLast();
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setPassWord(String passWord) {
    this.passWord = passWord;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }
  public void setLastUpdate(Date lastUpdate){
    this.lastUpdates.add(lastUpdate);
  }
}
