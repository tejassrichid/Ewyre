package com.example.ewyre.ViewModels;

import java.sql.Date;

public class UserDetails {
    public int Id;
    public String UserId;
    public String FirstName;
    public String MiddleName;
    public String LastName;
    public String InstitutionName;

    public String Password;

    public String ConfirmPassword;
    public String Email;
    public String UserName;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        ConfirmPassword = confirmPassword;
    }

    public String PhoneNumber;
    public Boolean IsActive;
    public int UserRole;
    public String Address;
    public String City;
    public String State;
    public String Country;
    public Number Pincode;
    public Date CreatedOn;
    public Boolean IsPhoneNumberConfirmed;
    public String Image;
    public int Age;
    public String Qualification;
    public Date DOB;

    public String CurrentCompany;
    public String CurrentCompanyPhoneNumber;
    public String Departments;
    public String Experience;

    public String DeviceId;

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public Boolean getActive() {
        return IsActive;
    }

    public void setActive(Boolean active) {
        IsActive = active;
    }

    public int getUserRole() {
        return UserRole;
    }

    public void setUserRole(int userRole) {
        UserRole = userRole;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public Number getPincode() {
        return Pincode;
    }

    public void setPincode(Number pincode) {
        Pincode = pincode;
    }

    public Date getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(Date createdOn) {
        CreatedOn = createdOn;
    }

    public Boolean getPhoneNumberConfirmed() {
        return IsPhoneNumberConfirmed;
    }

    public void setPhoneNumberConfirmed(Boolean phoneNumberConfirmed) {
        IsPhoneNumberConfirmed = phoneNumberConfirmed;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getAge() {
        return Age;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getQualification() {
        return Qualification;
    }

    public void setQualification(String qualification) {
        Qualification = qualification;
    }



    public String getCurrentCompany() {
        return CurrentCompany;
    }

    public void setCurrentCompany(String currentCompany) {
        CurrentCompany = currentCompany;
    }

    public String getCurrentCompanyPhoneNumber() {
        return CurrentCompanyPhoneNumber;
    }

    public void setCurrentCompanyPhoneNumber(String currentCompanyPhoneNumber) {
        CurrentCompanyPhoneNumber = currentCompanyPhoneNumber;
    }

    public String getDepartments() {
        return Departments;
    }

    public void setDepartments(String departments) {
        Departments = departments;
    }

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }


    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getInstitutionName() {
        return InstitutionName;
    }

    public void setInstitutionName(String institutionName) {
        InstitutionName = institutionName;
    }

    public byte[] Imagebyte;

    public byte[] getImagebyte() {
        return Imagebyte;
    }

    public void setImagebyte(byte[] imagebyte) {
        Imagebyte = imagebyte;
    }

}
