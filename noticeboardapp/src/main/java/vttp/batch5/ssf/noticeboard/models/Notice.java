package vttp.batch5.ssf.noticeboard.models;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Notice {
    
    private String id;


    @NotNull(message = "Title cannot be empty")
    @NotEmpty(message = "Title cannot be empty")
    @Size(min = 3, max = 128, message = "Title must be between 3 to 128 characters")
    private String title;
    
    @Email
    @NotNull(message = "Email cannot be empty")
    @NotEmpty(message = "Email cannot be empty")
    private String poster;
    
    @NotNull(message = "Please input a date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Date must be in the future")
    private Date postDate;

    @NotEmpty(message = "Must include at least 1 category")
    private List<String> categories = new LinkedList<>();

    @NotNull(message = "Text cannot be empty")
    @NotEmpty(message = "Text cannot be empty")
    private String text;

    public Notice() {}

    public Notice(String title, String poster, Date postDate, List<String> categories, String text) {
        this.title = title;
        this.poster = poster;
        this.postDate = postDate;
        this.categories = categories;
        this.text = text;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPoster() {
        return poster;
    }
    public void setPoster(String poster) {
        this.poster = poster;
    }
    public Date getPostDate() {
        return postDate;
    }
    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }
    public List<String> getCategories() {
        return categories;
    }
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    @Override
    public String toString() {
        return "Notice [title=" + title + ", email=" + poster + ", postDate=" + postDate + ", categories=" + categories
                + ", text=" + text + "]";
    }
}
