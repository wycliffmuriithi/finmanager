package com.finapp.tests.services.dbdao;

import com.finapp.tests.database.MpesastatementRepo;
import com.finapp.tests.database.entities.MpesaStatements;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class name: FilestorageService
 * Creater: wgicheru
 * Date:7/25/2019
 */
@Service
public class StatementsDao {
    private static final Logger LOGGER = Logger.getLogger(StatementsDao.class);
    @Autowired
    MpesastatementRepo mpesastatementRepo;

    public String saveFileupload(String authenticateduser, MultipartFile multipart) {
        try {
            LOGGER.info("saving file " + multipart.getResource().getFilename());
            MpesaStatements mpesaStatements = new MpesaStatements();
            mpesaStatements.setStatementfile(multipart.getBytes());
            mpesaStatements.setUser(authenticateduser);
            mpesaStatements.setFilename(multipart.getOriginalFilename());

            mpesastatementRepo.save(mpesaStatements);
            return multipart.getResource().getFilename() + " saved successfully";
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return "error saving " + multipart.getResource().getFilename();
    }

    public List<String> listMpesastatements(String authenticateduser) {
        List<MpesaStatements> mpesadocumentList = mpesastatementRepo.findByUser(authenticateduser);
        return mpesadocumentList.stream().map(document -> document.getFilename()).collect(Collectors.toList());
    }


    public Optional<Resource> retrieveFile(String email, String filename) {
        List<MpesaStatements> mpesadocumentList = mpesastatementRepo.findByUserAndFilename(email, filename);
        if (mpesadocumentList.isEmpty()) {
            return Optional.empty();
        }

        MpesaStatements document = mpesadocumentList.get(0);
        LOGGER.info("writing file " + document.getFilename());
        Resource resource = new ByteArrayResource(document.getStatementfile());
        return Optional.of(resource);
    }

    public List<Resource> retrieveFilesbyuser(String email){
       return mpesastatementRepo.findByUser(email).stream()
                .map(mpesaStatements -> new ByteArrayResource(mpesaStatements.getStatementfile()))
                .collect(Collectors.toList());
    }
}
