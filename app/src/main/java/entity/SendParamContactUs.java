
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SendParamContactUs implements Serializable {
    public SendParamContactUs(String name, String email, String phone, String body, String title, String section) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.body = body;
        this.title = title;
        this.section = section;
    }

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("section")
    @Expose
    private String section;


}
