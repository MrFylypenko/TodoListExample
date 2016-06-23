package com.springtest.dao;

import com.springtest.model.entity.File;
import com.springtest.model.entity.FileData;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by vano on 03.03.16.
 */
@Repository
public class FileDaoImpl implements FileDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void saveFileWithFileData(File file) {
        FileData fileData = file.getFileData();
        fileData.setFile(file);
        entityManager.persist(fileData);
        entityManager.persist(file);
    }

    @Override
    public File getFileById(int id) {
        return entityManager.find(File.class, id);
    }

    @Override
    public File getFileWithFileDataByFileId(int fileId) {
        File file = entityManager.createQuery("from File f join fetch f.fileData where f.id = :id", File.class).setParameter("id", fileId).getSingleResult();
        return file;
    }

    @Override
    public void deleteFile(File file) {
        file = entityManager.getReference(File.class, file.getId());
        FileData fileData = file.getFileData();
        entityManager.remove(file);
        entityManager.remove(fileData);
    }
}
