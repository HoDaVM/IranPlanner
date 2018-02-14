package entity;

/*
 * Created by troy379 on 10.03.17.
 */
public class CustomImage {

    private String url;

    public CustomImage() {
    }

    private String description;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CustomImage(String url, String description) {

        this.url = url;
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }
}