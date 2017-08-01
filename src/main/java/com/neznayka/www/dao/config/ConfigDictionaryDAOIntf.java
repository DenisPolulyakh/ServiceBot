package com.neznayka.www.dao.config;

import com.neznayka.www.hibernate.Message;
import com.neznayka.www.model.CRUDRequestResponse;
import com.neznayka.www.model.DictionaryData;
import com.neznayka.www.model.DictionaryMap;

import java.util.List;

/**
 * @author Polulyakh Denis
 *
 */
public interface ConfigDictionaryDAOIntf {

    CRUDRequestResponse create(CRUDRequestResponse crudRequestResponse);
    CRUDRequestResponse delete(CRUDRequestResponse crudRequestResponse);
    CRUDRequestResponse update(CRUDRequestResponse crudRequestResponse);
    CRUDRequestResponse list(int offset, int records);
    List<Message> listMessage();
    Long getTotal();
}
