package dev.patriciafb.stackmentor.model;

import jakarta.persistence.*;

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

   
    public Resource() {}

   
    public Resource(String title, String description, String category, String subcategory, String url, byte[] fileData, String fileType, String fileName) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.subcategory = subcategory;
        this.url = url;
        this.fileData = fileData;
        this.fileType = fileType;
        this.fileName = fileName;
    }

    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
   public String getDescription() { return description; }

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
