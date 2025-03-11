package dev.patriciafb.stackmentor.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String category; // Ej: FRONTEND, BACKEND, TESTING
    private String type; // Ej: LINK, FILE
    private String url;
    @ManyToOne
    @JoinColumn(name = "uploader_id")
    private User uploader;
    @OneToMany(mappedBy = "resource", cascade = CascadeType.ALL)
    private List<Comment> comments;
 
    public Resource() {}
    public Resource(String title, String category, String type, String url, User uploader) {
        this.title = title;
        this.category = category;
        this.type = type;
        this.url = url;
        this.uploader = uploader;
    }
   
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public User getUploader() {
        return uploader;
    }
    public void setUploader(User uploader) {
        this.uploader = uploader;
    }
    public List<Comment> getComments() {
        return comments;
    }
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
