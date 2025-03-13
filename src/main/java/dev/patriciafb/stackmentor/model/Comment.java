package dev.patriciafb.stackmentor.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "comments")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "resource", "user" })
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id", nullable = false)
    @JsonIgnoreProperties("comments") // Evita bucles de serialización
    private Resource resource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties("comments") // Evita bucles de serialización
    private User user;

    // Constructor vacío
    public Comment() {
    }

    public Comment(String content, Resource resource, User user) {
        this.content = content;
        this.resource = resource;
        this.user = user;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Resource getResource() { return resource; }
    public void setResource(Resource resource) { this.resource = resource; } // ✅ Agregado

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; } // 
}
