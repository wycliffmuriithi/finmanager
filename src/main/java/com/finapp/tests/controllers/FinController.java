package com.finapp.tests.controllers;

import com.finapp.tests.services.mpesa.statements.FilestorageService;
import com.finapp.tests.wrappers.responses.ResponseObject;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class name: FinController
 * Creater: wgicheru
 * Date:7/25/2019
 */
@RestController
@RequestMapping("/finmanager")
public class FinController {
    @Autowired
    FilestorageService filestorageservice;
    private static final Logger LOGGER = Logger.getLogger(FinController.class);


//    @PostMapping("/singleupload")
//    public ResponseObject uploadStatement(@RequestParam("file") MultipartFile file) {
//        try {
//
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            LOGGER.info("user " + auth.getName() + " uploading file " + file.getResource().getFilename());
//            String result = filestorageservice.saveFileupload(auth.getName(), file);
//            List<String> resultlist = new ArrayList<>();
//            resultlist.add(result);
//            return new ResponseObject("success", "file upload complete", resultlist);
//        } catch (Exception ex) {
//            LOGGER.error(ex.getMessage(), ex);
//        }
//        return new ResponseObject("failed", "could not load file object", new ArrayList());
//    }

    @PostMapping("/upload")
    public ResponseObject uploadStatements(@RequestParam("files") MultipartFile[] files) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info("user " + auth.getName() + " uploading " + files.length + " files");
        List<String> result = Arrays.asList(files)
                .stream()
                .map(multipartFile -> filestorageservice.saveFileupload(auth.getName(), multipartFile))
                .collect(Collectors.toList());
        return new ResponseObject("success", "file upload complete", result);
    }

    @GetMapping("/getstatements")
    public ResponseObject retrieveStatementlist() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       List<String> fileobjects =  filestorageservice.listMpesastatements(auth.getName());
        return new ResponseObject("success", "statement files", fileobjects);
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // Load file as Resource
        Optional<Resource> resourcewrapper =
                filestorageservice.retrieveFile(auth.getName(), fileName);
        if (resourcewrapper.isPresent()) {
            // Try to determine file's content type
            Resource resource = resourcewrapper.get();
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/pdf"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.notFound()
                    .build();
        }
    }

}
