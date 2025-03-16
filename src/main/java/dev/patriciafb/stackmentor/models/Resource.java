package dev.patriciafb.stackmentor.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "resources")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String category;
    private String subcategory;

    @Column(nullable = true)
    private String url;

    @Lob
    private byte[] fileData;

    private String fileType;
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "uploader_id")
    private User uploader;

    @OneToMany(mappedBy = "resource", cascade = CascadeType.ALL)
    private List<Comment> comments;

    private String type;

    public Resource() {
    }

    public Resource(String title, String description, String category, String subcategory, String type, String url,
            byte[] fileData, String fileType, String fileName, User uploader) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.subcategory = subcategory;
        this.type = type;
        this.url = url;
        this.fileData = fileData;
        this.fileType = fileType;
        this.fileName = fileName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", subcategory='" + subcategory + '\'' +
                ", url='" + url + '\'' +
                ", fileType='" + fileType + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
