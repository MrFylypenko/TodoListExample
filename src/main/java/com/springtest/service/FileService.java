package com.springtest.service;

import com.springtest.model.entity.File;
import com.springtest.model.entity.FileData;
import com.springtest.model.entity.User;

/**
 * Created by vano on 03.03.16.
 */
public interface FileService {

    void saveFile(File file, int taskId);

    File getFileById(int id);

    File getFileWithFileDataByFileId(int fileId);

    void deleteFile(int fileId, User user);

}
