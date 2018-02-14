package com.neznayka.www.controller;


import com.neznayka.www.dao.config.ConfigDAO;
import com.neznayka.www.dao.config.ConfigDictionaryDAOIntf;
import com.neznayka.www.model.*;
import com.neznayka.www.processor.PhraseProcessor;
import com.neznayka.www.service.ExportService;
import com.neznayka.www.service.LoggingService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


;import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;


/**
 * @author Denis Polulyakh
 */

@RestController
@EnableWebMvc
public class NeznaykaConfigDictionaryController {
    private static final String CLASS_NAME = NeznaykaConfigDictionaryController.class.getName();
    private static final Logger log = Logger.getLogger(CLASS_NAME);

    @Autowired
    @Qualifier("ConfigDAO")
    ConfigDictionaryDAOIntf configDAO;

    @Autowired
    @Qualifier("ConfigDAOStub")
    ConfigDAO configDAOstub;

    @Autowired
    LoggingService loggingService;

    @Autowired
    ExportService exportService;


    @CrossOrigin(origins = "*", allowedHeaders = {"Origin", "X-Requested-With", "Content-Type", "Accept"})
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<CRUDRequestResponse> create(@RequestBody CRUDRequestResponse crudRequestResponse) {
        log.info("CREATE");
        try {
            crudRequestResponse = configDAO.create(crudRequestResponse);
            crudRequestResponse.setStatus("success");
            return new ResponseEntity<CRUDRequestResponse>(crudRequestResponse, HttpStatus.OK);
        } catch (Exception e) {
            crudRequestResponse = new CRUDRequestResponse("error");
            return new ResponseEntity<CRUDRequestResponse>(crudRequestResponse,  HttpStatus.BAD_REQUEST);
        }
    }


    @CrossOrigin(origins = "*", allowedHeaders = {"Origin", "X-Requested-With", "Content-Type", "Accept"})
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<CRUDRequestResponse> update(@RequestBody CRUDRequestResponse crudRequestResponse) {
        log.info("UPDATE");
        try {
            crudRequestResponse = configDAO.update(crudRequestResponse);
            crudRequestResponse.setStatus("success");
            return new ResponseEntity<CRUDRequestResponse>(crudRequestResponse, HttpStatus.OK);
        } catch (Exception e) {
            crudRequestResponse = new CRUDRequestResponse("error");
            return new ResponseEntity<CRUDRequestResponse>(crudRequestResponse, HttpStatus.BAD_REQUEST);
        }

    }

    @CrossOrigin(origins = "*", allowedHeaders = {"Origin", "X-Requested-With", "Content-Type", "Accept"})
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<CRUDRequestResponse> delete(@RequestBody CRUDRequestResponse crudRequestResponse) {
        log.info("DELETE");
        try {
            crudRequestResponse = configDAO.delete(crudRequestResponse);
            crudRequestResponse.setStatus("success");
            return new ResponseEntity<CRUDRequestResponse>(crudRequestResponse, HttpStatus.OK);
        } catch (Exception e) {
            crudRequestResponse = new CRUDRequestResponse("error");
            return new ResponseEntity<CRUDRequestResponse>(crudRequestResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = "*",allowedHeaders = {"Origin","X-Requested-With","Content-Type","Accept"})
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<CRUDRequestResponse> list(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,@RequestParam(value = "records", required = false, defaultValue = "1") Integer records) {
        CRUDRequestResponse crudRequestResponse = null;
        try {

            crudRequestResponse = configDAO.list(offset, records);
            System.out.println(configDAO.getTotal());
            crudRequestResponse.setStatus("success");
            return new ResponseEntity<CRUDRequestResponse>(crudRequestResponse, HttpStatus.OK);
        }
        catch(Exception e) {
            crudRequestResponse = new CRUDRequestResponse("error");
            return new ResponseEntity<CRUDRequestResponse>(crudRequestResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @CrossOrigin(origins = "*",allowedHeaders = {"Origin","X-Requested-With","Content-Type","Accept"})
    public MessageAnswer search(@RequestParam(value = "id", required = false) Long id,@RequestParam(value = "message", required = false, defaultValue = "привет") String text) throws UnsupportedEncodingException {
        text = URLDecoder.decode(text, "UTF-8");
        log.info("After decode: " + text);

        PhraseProcessor phraseProcessor = new PhraseProcessor();
        phraseProcessor.setConfigDAO(configDAO);
        MessageAnswer messageAnswer = phraseProcessor.getMessageToAnswer(text);
        //loggingService.logMessage(id,text,messageAnswer.getPhrase());
        return  messageAnswer;

    }
    //экспорт базы логов в эксель
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @CrossOrigin(origins = "*",allowedHeaders = {"Origin","X-Requested-With","Content-Type","Accept"})
    public void export( final HttpServletResponse response) {
        exportService.exportToExcel(response);

    }

}
