package com.neznayka.www.dao.config;

import com.neznayka.www.model.DictionaryData;
import com.neznayka.www.model.DictionaryMap;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class ConfigDAOStubImpl implements ConfigDAO{
    private static final String CLASS_NAME = ConfigDAOStubImpl.class.getName();
    private static final Logger log = Logger.getLogger(CLASS_NAME);

    @Override
    public boolean create(DictionaryMap dictionaryMap) {
        log.info("create "+dictionaryMap.getTags()+" "+dictionaryMap.getMessage());
        return true;
    }

    @Override
    public boolean delete(int id) {
        log.info("delete "+id);
        return true;
    }

    @Override
    public boolean update(DictionaryMap dictionaryMap) {
        log.info("update "+dictionaryMap.getTags()+" "+dictionaryMap.getMessage());
        return true;
    }

    @Override
    public List<DictionaryData> list(int page, int records) {
        List<DictionaryData> list = new ArrayList<DictionaryData>();

        for (int i = 0; i < records; i++) {
            ArrayList<String> tags = new ArrayList<String>();
            tags.add("Куропатка");
            tags.add("Доллар");
            tags.add("Оксимирон");
            tags.add("Хованский");
            String message="ррраунд";
            list.add( new DictionaryData(i, tags, message ));
        }
        return list;
    }
}
