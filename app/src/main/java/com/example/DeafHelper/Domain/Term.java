package com.example.DeafHelper.Domain;

import java.io.Serializable;

public class Term extends Item implements Serializable{
    private String image = "term_image";

    public Term(String id, String label, String description) {
        super(id, label, description);
        this.image = this.image + "";
    }

    public String getImage() {
        return image;
    }

    public String getLabel(){
        return super.getLabel();
    }

}
