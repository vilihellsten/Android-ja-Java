package com.example.javakurssi;

public class ItemDataModel {
    String name;
    String id;
    String registrationDate;
    String companyForm;

    public ItemDataModel(String name, String id,String companyForm, String registrationDate) {
        this.name = name;
        this.id = id;
        this.companyForm = companyForm;
        this.registrationDate = registrationDate;
    }

    public String getName() {
        return name;
    }


    public String getId() {
        return id;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public String getCompanyForm() {
        return companyForm;
    }

}
