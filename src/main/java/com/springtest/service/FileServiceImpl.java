package com.springtest.service;

import com.springtest.dao.FileDao;
import com.springtest.dao.TaskDao;
import com.springtest.model.entity.File;
import com.springtest.model.entity.FileData;
import com.springtest.model.entity.Task;
import com.springtest.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by vano on 03.03.16.
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileDao fileDao;

    @Autowired
    TaskDao taskDao;

    @Override
    @Transactional
    public void saveFile(File file, int taskId) {
        Task task = taskDao.findTaskById(taskId);
        file.setTask(task);
        fileDao.saveFileWithFileData(file);
    }

    @Override
    public File getFileById(int id) {
        return fileDao.getFileById(id);
    }

    @Override
    public File getFileWithFileDataByFileId(int fileId) {
        return fileDao.getFileWithFileDataByFileId(fileId);
    }

    @Override
    @Transactional
    public void deleteFile(int fileId, User user) {
        File file = fileDao.getFileById(fileId);
        if(!file.getCreatedBy().equals(user)) {
            throw new AccessDeniedException("You don't have access to delete this file");
        }
        fileDao.deleteFile(file);
    }

}
