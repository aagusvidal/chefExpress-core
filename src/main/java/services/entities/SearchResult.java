package services.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchResult {

    private String kind;
    private String etag;
    @JsonProperty("id")
    private Id id;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "kind='" + kind + '\'' +
                ", etag='" + etag + '\'' +
                ", id=" + id +
                '}';
    }
}
