package com.neznayka.www.dao.config;

import com.neznayka.www.hibernate.Message;
import com.neznayka.www.model.DictionaryData;
import com.neznayka.www.model.DictionaryMap;

import java.util.List;

/**
 * @author Polulyakh Denis
 *
 */
public interface ConfigDictionaryDAOIntf {

    DictionaryData create(DictionaryMap dictionaryMap);
    Integer delete(int id);
    DictionaryData update(DictionaryMap dictionaryMap);
    List<DictionaryData> list(int offset, int records);
    List<Message> listMessage();
    Long getTotal();
}
