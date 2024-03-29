package ru.geekbrains.persist.model;

import javax.persistence.*;

@Entity
@Table(name = "pictures")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "storageUUID")
    private String storageUUID;

    @ManyToOne
    private Product product;

    public Picture() {
    }

    public Picture(Long id, String name, String contentType, String storageUUID, Product product) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
        this.storageUUID = storageUUID;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getStorageUUID() {
        return storageUUID;
    }

    public void setStorageUUID(String storageUUID) {
        this.storageUUID = storageUUID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
