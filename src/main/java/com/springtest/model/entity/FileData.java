package com.springtest.model.entity;

import javax.persistence.*;

/**
 * Created by vano on 03.03.16.
 *
 * The FileData supporting JPA Entity
 */

@Entity
public class FileData {

    public FileData (){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne (fetch = FetchType.LAZY, mappedBy = "fileData")
    private File file;

    private String contentType;

    @Lob
    @Column (length = 10000)
    private byte [] data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
