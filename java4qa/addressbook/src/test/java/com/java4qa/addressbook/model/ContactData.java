package com.java4qa.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

@XStreamAlias("contacts")
@Entity
@Table(name = "addressbook")
public class ContactData {
  @XStreamOmitField
  @Id
  @Column(name = "id")
  private int id = Integer.MAX_VALUE;
  @Expose
  @Column(name = "firstname")
  private String firstName;
  @Expose
  @Column(name = "middlename")
  private String middleName;
  @Expose
  @Column(name = "lastname")
  private String lastName;
  @Expose
  @Column(name = "nickname")
  private String nickName;
  //TODO: add annotation
  private String title;
  @Expose
  @Column(name = "company")
  private String company;
  @Expose
  @Column(name = "address")
  @Type(type = "text")
  private String address;
  @Expose
  @Column(name = "photo")
  @Type(type = "text")
  private String photo;
  @Expose
  @Column(name = "home")
  @Type(type = "text")
  private String homePhone;
  @Expose
  @Column(name = "mobile")
  @Type(type = "text")
  private String mobilePhone;
  @Expose
  @Column(name = "work")
  @Type(type = "text")
  private String workPhone;
  @Expose
  @Column(name = "fax")
  @Type(type = "text")
  private String fax;
  @Expose
  @Column(name = "email")
  @Type(type = "text")
  private String email;
  @Expose
  @Column(name = "email2")
  @Type(type = "text")
  private String email2;
  @Expose
  @Column(name = "email3")
  @Type(type = "text")
  private String email3;
  //TODO: DONE - add annotation
  @Expose
  @Column(name = "homepage")
  @Type(type = "text")
  private String homePage;

  public Integer getBday() {
    return bday;
  }

  public String getBmonth() {
    return bmonth;
  }

  public Integer getByear() {
    return byear;
  }

  public Integer getAday() {
    return aday;
  }

  public String getAmonth() {
    return amonth;
  }

  public Integer getAyear() {
    return ayear;
  }

  @Expose
  @Column(name = "bday", columnDefinition = "TINYINT")
  private Integer bday;
  @Expose
  @Column(name = "bmonth")
  private String bmonth;
  @Expose
  @Column(name = "byear", columnDefinition = "VARCHAR")
  private Integer byear;
  @Transient
  private String dOB = "" + bday + ". " + bmonth + " " + byear;
  @Expose
  @Column(name = "aday", columnDefinition = "TINYINT")
  private Integer aday;
  @Expose
  @Column(name = "amonth")
  private String amonth;
  @Expose
  @Column(name = "ayear", columnDefinition = "VARCHAR")
  private Integer ayear;
  @Transient
  private String anniversary = "" + aday + ". " + amonth + " " + ayear;
  //TODO: DONE - add annotation
  @Expose
  @Column(name = "address2")
  @Type(type = "text")
  private String homeAddress;
  //TODO: DONE - add annotation
  @Expose
  @Column(name = "phone2")
  @Type(type = "text")
  private String homePhoneNum;
  //TODO: DONE - add annotation
  @Expose
  @Column(name = "notes")
  @Type(type = "text")
  private String notes;
  @ManyToMany
  @JoinTable(name = "address_in_groups",
      joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
  private Set<GroupData> groups = new HashSet<GroupData>();
  @XStreamOmitField
  @Transient
  private String allPhones;
  @XStreamOmitField
  @Transient
  private String allEmails;
  @XStreamOmitField
  @Transient
  private String contactDetails;

  public int getId() {
    return id;
  }

  public ContactData withId(int id) {
    this.id = id;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public ContactData withFirst(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public ContactData withLast(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public ContactData withHomePhone(String home) {
    this.homePhone = home;
    return this;
  }

  public String getMobilePhone() {
    return mobilePhone;
  }

  public ContactData withMobilePhone(String mobile) {
    this.mobilePhone = mobile;
    return this;
  }

  public String getWorkPhone() {
    return workPhone;
  }

  public String getHomePhone() {
    return homePhone;
  }

  public ContactData withWorkPhone(String work) {
    this.workPhone = work;
    return this;
  }

  public String getAddress() {
    return address;
  }

  public ContactData withAddress(String address) {
    this.address = address;
    return this;
  }

  public String getAllPhones() {
    return allPhones;
  }

  public ContactData withAllPhones(String allPhones) {
    this.allPhones = allPhones;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public ContactData withEmail(String email) {
    this.email = email;
    return this;
  }

  public String getEmail2() {
    return email2;
  }

  public ContactData withEmail2(String email2) {
    this.email2 = email2;
    return this;
  }

  public String getEmail3() {
    return email3;
  }

  public ContactData withEmail3(String email3) {
    this.email3 = email3;
    return this;
  }

  public String getAllEmails() {
    return allEmails;
  }

  public ContactData withAllEmails(String allEmails) {
    this.allEmails = allEmails;
    return this;
  }

  public String getContactDetails() {
    return contactDetails;
  }

  public ContactData withContactDetails(String details) {
    this.contactDetails = details;
    return this;
  }

  public String getPhoto() {
    return photo;
  }

  public ContactData withPhoto(File photo) {
    this.photo = photo.getPath();
    return this;
  }

  public Groups getGroups() {
    return new Groups(groups);
  }

  public ContactData inGroups(GroupData group) {
    groups.add(group);
    return this;
  }

  public String getMiddleName() {
    return middleName;
  }

  public String getNickName() {
    return nickName;
  }

  public String getTitle() {
    return title;
  }

  public String getCompany() {
    return company;
  }

  public String getFax() {
    return fax;
  }

  public String getHomePage() {
    return homePage;
  }

  public String getdOB() {
    return dOB;
  }

  public String getAnniversary() {
    return anniversary;
  }

  public String getHomeAddress() {
    return homeAddress;
  }

  public String getHomePhoneNum() {
    return homePhoneNum;
  }

  public String getNotes() {
    return notes;
  }

  public ContactData withNick(String nickName) {
    this.nickName = nickName;
    return this;
  }

  public ContactData withTitle(String title) {
    this.title = title;
    return this;
  }

  public ContactData withCompany(String company) {
    this.company = company;
    return this;
  }

  public ContactData withFax(String fax) {
    this.fax = fax;
    return this;
  }

  public ContactData withHomePage(String homePage) {
    this.homePage = homePage;
    return this;
  }

  public ContactData withBirthDate(String dOB) {
    this.dOB = dOB;
    return this;
  }

  public ContactData withAnniversaryDate(String anniversary) {
    this.anniversary = anniversary;
    return this;
  }

  public ContactData withHomeAddress(String homeAddress) {
    this.homeAddress = homeAddress;
    return this;
  }

  public ContactData withHomePhoneNum(String homePhoneNum) {
    this.homePhoneNum = homePhoneNum;
    return this;
  }

  public ContactData withMiddle(String middleName) {
    this.middleName = middleName;
    return this;
  }

  public ContactData withNotes(String notes) {
    this.notes = notes;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ContactData that = (ContactData) o;

    if (id != that.id) return false;
    if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
    return lastName != null ? lastName.equals(that.lastName) : that.lastName == null;
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
    result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
    return result;
  }

  // If groups.size == 0, method .toString()@ContactData throws exception:
  // Method threw 'org.hibernate.LazyInitializationException' exception. Cannot evaluate com/java4qa/addressbook/model/ContactData.java.toString();
  // toString() method should not contains a groups field.
  @Override
  public String toString() {
    return "ContactData{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        '}';
  }

  public ContactData withBDay(int bday) {
    this.bday = bday;
    return this;
  }

  public ContactData withADay(int aday) {
    this.aday = aday;
    return this;
  }

  public ContactData withBMonth(String bMonth) {
    this.bmonth = bMonth;
    return this;
  }

  public ContactData withAMonth(String aMonth) {
    this.amonth = aMonth;
    return this;
  }

  public ContactData withBYear(Integer bYear) {
    this.byear = bYear;
    return this;
  }

  public ContactData withAYear(Integer aYear) {
    this.ayear = aYear;
    return this;

  }
}
