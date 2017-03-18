package com.neznayka.www.dao.config;

import com.neznayka.www.model.DictionaryData;
import com.neznayka.www.model.DictionaryMap;
import com.neznayka.www.model.ResponseCRUD;
import com.neznayka.www.model.ResponseData;


import java.util.List;

public interface ConfigDAO {

   boolean create(DictionaryMap dictionaryMap);
   boolean delete(int id);
   boolean update(DictionaryMap dictionaryMap);
   List<DictionaryData> list(int page, int records);

}
