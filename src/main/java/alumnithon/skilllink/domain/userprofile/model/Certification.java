package alumnithon.skilllink.domain.userprofile.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Certification {
    @Column(name = "title", nullable = false, length = 120)
    private String title;

    @Column(name = "certification_url", columnDefinition = "TEXT")
    private String url;

    // Constructores, getters y setters
    public Certification() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Certification that = (Certification) o;
        return Objects.equals(title, that.title) && 
               Objects.equals(url, that.url);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(title, url);
    }

    // Getters y setters


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}