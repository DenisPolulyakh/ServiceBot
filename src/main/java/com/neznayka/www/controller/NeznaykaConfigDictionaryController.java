package com.neznayka.www.controller;


import com.neznayka.www.dao.config.ConfigDAO;
import com.neznayka.www.dao.config.ConfigDictionaryDAOIntf;
import com.neznayka.www.model.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


;import java.util.List;


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

    @CrossOrigin(origins = "*",allowedHeaders = {"Origin","X-Requested-With","Content-Type","Accept"})
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<ResponseCRUD> create(@RequestBody DictionaryMap dictionaryMap) {

        HttpHeaders headers = new HttpHeaders();
        DictionaryData responseDictionaryData = configDAO.create(dictionaryMap);
        if(responseDictionaryData!=null) {
            ResponseCRUD responseCRUD = new ResponseCRUD("success",responseDictionaryData.getMessage(), responseDictionaryData.getTags(), responseDictionaryData.getId());
            return new ResponseEntity<ResponseCRUD>(responseCRUD,HttpStatus.OK);
        }else{
            ResponseCRUD responseCRUD = new ResponseCRUD("error",null,null, 159);
            return new ResponseEntity<ResponseCRUD>(responseCRUD,headers,HttpStatus.BAD_REQUEST);
        }
    }




    @CrossOrigin(origins = "*",allowedHeaders = {"Origin","X-Requested-With","Content-Type","Accept"})
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<ResponseCRUD> update(@RequestBody DictionaryMap dictionaryMap) {
        DictionaryData responseDictionaryData = configDAO.update(dictionaryMap);
        log.info("UPDATE");
        if(responseDictionaryData!=null) {
            ResponseCRUD responseCRUD = new ResponseCRUD("success",responseDictionaryData.getMessage(), responseDictionaryData.getTags(), responseDictionaryData.getId());
            return new ResponseEntity<ResponseCRUD>(responseCRUD,HttpStatus.OK);
        }else{
            ResponseCRUD responseCRUD = new ResponseCRUD("error",null,null, 159);
            return new ResponseEntity<ResponseCRUD>(responseCRUD,HttpStatus.BAD_REQUEST);
        }
    }
    @CrossOrigin(origins = "*",allowedHeaders = {"Origin","X-Requested-With","Content-Type","Accept"})
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<ResponseCRUD> delete(@RequestBody DeleteRequestData deleteRequestData) {
        /*if(id.equals("")){ResponseCRUD responseCRUD = new ResponseCRUD("error", "",null, 159);
            return new ResponseEntity<ResponseCRUD>(responseCRUD,HttpStatus.BAD_REQUEST);}*/
       // configDAO.delete(Integer.parseInt(id));
       // HttpHeaders headers = new HttpHeaders();

        Integer idDelete = configDAO.delete(Integer.parseInt(deleteRequestData.getId()));
        ResponseCRUD responseCRUD = new ResponseCRUD("success", "",null, idDelete);
        return new ResponseEntity<ResponseCRUD>(responseCRUD, HttpStatus.OK);
       /* }else{
            ResponseCRUD responseCRUD = new ResponseCRUD("error", null,null, 159);
            return new ResponseEntity<ResponseCRUD>(responseCRUD,HttpStatus.BAD_REQUEST);
        }*/
    }

    @CrossOrigin(origins = "*",allowedHeaders = {"Origin","X-Requested-With","Content-Type","Accept"})
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<ResponseData> list(@RequestParam(value = "offset", required = false, defaultValue = "1") String offset,@RequestParam(value = "records", required = false, defaultValue = "1") String records) {

        HttpHeaders headers = new HttpHeaders();
        List<DictionaryData> dictionaryData = configDAO.list(Integer.parseInt(offset),Integer.parseInt(records));
        //List<DictionaryData> dictionaryData = configDAOstub.list(Integer.parseInt(offset),Integer.parseInt(records));

        ResponseData responseData = new ResponseData("success",offset, records,(configDAO.getTotal()).toString() , dictionaryData);
        return new ResponseEntity<ResponseData>(responseData, HttpStatus.OK);
    }
}