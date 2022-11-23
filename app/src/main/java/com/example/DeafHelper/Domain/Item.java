package com.example.DeafHelper.Domain;

import java.io.Serializable;

public class Item implements Serializable {
    private String id, label, description;
    public static String servicesPath = "https://www.giveawaygiftcard.com/deafhelper/deafhelper/web/api/activeservices";
    public static String termsPath = "https://giveawaygiftcard.com/deafhelper/deafhelper/web/api/activeterms";
    public Item(String id, String label, String description) {
        this.id = id;
        this.label = label;
        this.description = description;
    }

    public Item() {
    }


    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

}
