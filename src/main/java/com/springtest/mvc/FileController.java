package com.springtest.mvc;

import com.springtest.model.entity.File;
import com.springtest.model.entity.FileData;
import com.springtest.model.entity.User;
import com.springtest.service.FileService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * Created by vano on 03.03.16.
 */

@Controller
@RequestMapping("file")
public class FileController {

    @Autowired
    FileService fileService;

    @RequestMapping(value = "add/{taskId}", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public void addFile(@PathVariable int taskId, @RequestParam("file") MultipartFile multipartFile) throws IOException {

        FileData fileData = new FileData();
        fileData.setData(multipartFile.getBytes());
        fileData.setContentType(multipartFile.getContentType());

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        File file = new File();
        file.setFileName(multipartFile.getOriginalFilename());
        file.setFileData(fileData);
        file.setFileSize(multipartFile.getSize());
        file.setCreatedBy(user);

        fileService.saveFile(file, taskId);
    }


    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public void getFile(@PathVariable int id, HttpServletResponse response) throws ChangeSetPersister.NotFoundException {

        File file = fileService.getFileWithFileDataByFileId(id);

        FileData fileData = file.getFileData();

        try {
            response.setHeader("Content-Disposition", "inline;filename=\"" + URLEncoder.encode(file.getFileName(), "UTF-8")   + "\"");

            OutputStream out = response.getOutputStream();
            response.setContentType(fileData.getContentType());

            IOUtils.copy(new ByteArrayInputStream(fileData.getData()), out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "delete/{fileId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteFile(@PathVariable int fileId) throws IOException {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        fileService.deleteFile(fileId, user);
    }

    @ExceptionHandler
    @ResponseBody
    public String getFile(Exception e, HttpServletResponse response) {
        e.printStackTrace();

        if (e.getClass().getName().equals("javax.persistence.NoResultException"))
            response.setStatus(404);

        response.setStatus(500);

        return e.getMessage();
    }





}
