package com.springtest.dao;

import com.springtest.model.entity.File;
import com.springtest.model.entity.FileData;

/**
 * Created by vano on 03.03.16.
 */
public interface FileDao {

     void saveFileWithFileData(File file);

     File getFileById(int id);

     File getFileWithFileDataByFileId(int fileId);

     void deleteFile(File file);

}
