package com.example.DeafHelper.Domain;

import java.io.Serializable;

public class Service extends Item implements Serializable{
    private String video = "https://giveawaygiftcard.com/deafhelper/deafhelper/web/assets/service_videos/",
    image = "https://www.giveawaygiftcard.com/deafhelper/deafhelper/web/assets/images/service_images/";

    public Service(String id, String label, String description, String image, String video) {
        super(id, label, description);
        this.image = this.image + image;
        this.video = this.video + video;
    }

    public String getVideo() {
        return video;
    }
    public String getLabel(){
        return super.getLabel();
    }

    public String getImage() {
        return image;
    }
}
