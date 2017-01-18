package sobolev.message;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by kirrok on 17.01.17.
 */
public class ChatMessage {
    private int id;
    private String type;
    private String title;
    private String username;
    @JsonProperty("first_name")
    private String firstname;
    @JsonProperty("last_name")
    private String lastName;
    private boolean all_members_are_administrators;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isAll_members_are_administrators() {
        return all_members_are_administrators;
    }

    public void setAll_members_are_administrators(boolean all_members_are_administrators) {
        this.all_members_are_administrators = all_members_are_administrators;
    }
}
