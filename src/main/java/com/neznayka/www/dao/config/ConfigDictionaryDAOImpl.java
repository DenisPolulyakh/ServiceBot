package com.neznayka.www.dao.config;

import com.neznayka.www.controller.NeznaykaConfigDictionaryController;
import com.neznayka.www.hibernate.Message;
import com.neznayka.www.hibernate.Tag;
import com.neznayka.www.model.DictionaryData;
import com.neznayka.www.model.DictionaryMap;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


@Repository
public class ConfigDictionaryDAOImpl implements ConfigDictionaryDAOIntf{
    private static final String CLASS_NAME = ConfigDictionaryDAOImpl.class.getName();
    private static final Logger log = Logger.getLogger(CLASS_NAME);
    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public DictionaryData create(DictionaryMap dictionaryMap) {

       DictionaryData responseInsert = null;
       List<Tag> tags = new ArrayList<Tag>();
       for(String tagstr:dictionaryMap.getTags()){
           Tag tag = new Tag();
           tag.setTag(tagstr);
           tags.add(tag);
       }
        Message message = new Message();
        message.setValue(dictionaryMap.getMessage());
        message.setTags(tags);


        Integer id = (Integer)sessionFactory.getCurrentSession().save(message);
        log.info("Id entry="+id);
        String hql ="from Message m where m.id=:id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("id", id);
        message = (Message) query.list().get(0);
        responseInsert = new DictionaryData();
        responseInsert.setMessage(message.getValue());
        responseInsert.setId(id);
        List<String> tagsList= new ArrayList<String>();
        for(Tag tag:message.getTags()){
           tagsList.add(tag.getTag());
        }
        responseInsert.setTags(tagsList);
        return responseInsert;
    }

    @Override
    @Transactional
    public Integer delete(int id) {
        String hql ="from Message m where m.id=:id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("id", id);
        Message message =  (Message)query.list().get(0);
        sessionFactory.getCurrentSession().delete(message);
        return id;
    }

    @Override
    @Transactional
    public DictionaryData update(DictionaryMap dictionaryMap) {
        DictionaryData responseInsert = null;
        Integer id = dictionaryMap.getId();
        String hql ="from Message m where m.id=:id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("id", id);
        Message message =  (Message)query.list().get(0);
        message.setValue(dictionaryMap.getMessage());
        List<Tag> tags = message.getTags();
        List<Tag> tagsNew = new ArrayList<Tag>();
        List<String> tagsStrList = dictionaryMap.getTags();
        int i=0;
        for(Tag t:tags){
            t.setTag(tagsStrList.get(i));
            i++;
            sessionFactory.getCurrentSession().update(t);
            tagsNew.add(t);
        }
        message.setTags(tagsNew);
        sessionFactory.getCurrentSession().saveOrUpdate(message);
        hql ="from Message m where m.id=:id";
        query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("id", id);
        message = (Message) query.list().get(0);
        responseInsert = new DictionaryData();
        responseInsert.setMessage(message.getValue());
        responseInsert.setId(message.getId());
        List<String> tagsList= new ArrayList<String>();
        for(Tag tag:message.getTags()){
            tagsList.add(tag.getTag());
        }
        responseInsert.setTags(tagsList);
        return responseInsert;
    }

    @Override
    @Transactional
    public List<DictionaryData> list(int offset, int records) {
        int start = offset;
        int stop = offset+records;
        log.info("Start="+start);
        log.info("Stop="+stop);
        /*DictionaryData responseInsert = new DictionaryData();
        String hql ="from Message m where m.id>:start AND m.id<=:stop ORDER BY m.id ASC";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("start", start);
        query.setInteger("stop", stop);
        List<Message> messageList =  query.list();*/
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Message.class);
        criteria.setFirstResult(start);
        criteria.setMaxResults(stop);
        List<Message> messageList = criteria.list();
        List<DictionaryData> dictionaryDataList = new ArrayList<DictionaryData>();
        log.info("Find "+messageList.size());
        for(Message message:messageList){
            DictionaryData dictionaryData = new DictionaryData();
            dictionaryData.setId(message.getId());
            dictionaryData.setMessage(message.getValue());
            List<String> tagsList= new ArrayList<String>();
            for(Tag tag:message.getTags()){
                tagsList.add(tag.getTag());
            }
            dictionaryData.setTags(tagsList);
            dictionaryDataList.add(dictionaryData);


        }
        return dictionaryDataList;
    }

    @Override
    @Transactional
    public List<Message> listMessage() {
        List<Message> answersList = (List<Message>) sessionFactory.getCurrentSession()
                .createCriteria(Message.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return answersList;
    }

    @Override
    @Transactional
    public Long getTotal() {
        String hql ="select count(*) from Message";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        Long total =  (Long) query.uniqueResult();
        return total;
    }
}


    /*@Override
    @Transactional
    public List<ValueAnswer> list() {
        List<ValueAnswer> answersList = (List<ValueAnswer>) sessionFactory.getCurrentSession()
                .createCriteria(ValueAnswer.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return answersList;
    }

    @Override
    @Transactional
    public List<ValueAnswer> listAnswersByTypePhrase(String type) {
        String hql = "select a from Answers as a join a.keyPhrase as  kp where kp.typePhrase=:type";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("type", type);
        return query.list();
    }


    @Override
    @Transactional
    public List<KeyPhrase> listKeyPhraseByTypePhrase(String type) {
        String hql = "select kp from KeyPhrase as kp where kp.typePhrase = :type";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("type", type);
        return query.list();
    }


    @Override
    @Transactional
    public void saveOrUpdate(KeyPhrase keyPhrase) {
        sessionFactory.getCurrentSession().saveOrUpdate(keyPhrase);
    }

    @Override
    @Transactional
    public void saveOrUpdate(ValueAnswer answers) {
        sessionFactory.getCurrentSession().saveOrUpdate(answers);
    }


    @Override
    @Transactional
    public KeyPhrase getCode(String name) {
        String hql = "select c from CodeCurrency as c join c.variantsName as cv where cv.nameCurrency = :namecurrency";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return null;
    }*/

   /* @Override
    @Transactional
    public List<ValueAnswer> listAnswersByKeyQuestion(String keyQuestion) {
        String hql = "select a from ValueAnswer as a join a.keyQuestion as kq where kq.question = :key";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("key",keyQuestion);
        return query.list();
    }

    @Override
    @Transactional
    public List<KeyQuestion> keyQuestionByKey(String key) {
        String hql = "from KeyQuestion kq where kq.question =  :key";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("key", key);
        return  query.list();
    }

    @Override
    @Transactional
    public List<KeyQuestion> listKeyQuestion() {
        List<KeyQuestion> keyQuestionList = (List<KeyQuestion> ) sessionFactory.getCurrentSession()
                .createCriteria(KeyQuestion.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return keyQuestionList;
    }

    @Override
    @Transactional
    public List<ValueAnswer> listValueAnswer() {
        List<ValueAnswer> valueAnswerList = (List<ValueAnswer>) sessionFactory.getCurrentSession()
                .createCriteria(ValueAnswer.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return valueAnswerList;
    }

    @Override
    @Transactional
    public void saveOrUpdate(KeyQuestion keyQuestion) {
        sessionFactory.getCurrentSession().saveOrUpdate(keyQuestion);
    }

    @Override
    @Transactional
    public void saveOrUpdate(ValueAnswer valueAnswer) {
        sessionFactory.getCurrentSession().saveOrUpdate(valueAnswer);
    }

    @Override
    @Transactional
    public List<MemoryProcessTable> getMemoryProcessTable(String idUser) {
        String hql = "from MemoryProcessTable mpt where mpt.idUser =  :idUser";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("idUser", idUser);
        System.out.println("IDUSER "+idUser);
        return  query.list();

        *//*List memoryCommandTable = query.list();
        if (memoryCommandTable.size() > 0) {
            Blob blob = ((MemoryProcessTable)memoryCommandTable.get(0)).getMemoryProcessor();
            try {
                return new String(blob.getBytes(1, (int) blob.length()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return null;
        }
        return null;*//*
    }

    @Override
    @Transactional
    public void saveOrUpdate(MemoryProcessTable memoryProcessTable) {
        sessionFactory.getCurrentSession().saveOrUpdate(memoryProcessTable);
    }

    @Override
    @Transactional
    public void deleteMemoryProcessor(MemoryProcessTable memoryProcessTable) {
        sessionFactory.getCurrentSession().delete(memoryProcessTable);
    }

}


*//*
    @Override
    public CodeNameCurrency load() {
        String sql = "SELECT * FROM CODE_NAME_CURRENCY";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            CodeNameCurrency codeNameCurrency = new CodeNameCurrency();
            ArrayList<String> code = new ArrayList<String>();
            ArrayList<String> nameCurrency = new ArrayList<String>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
               code.add(rs.getString("CODE"));
               nameCurrency.add(rs.getString("VARIANTS_NAME"));
            }
            rs.close();
            ps.close();
            codeNameCurrency.setCode(code);
            codeNameCurrency.setNames(nameCurrency);
            return codeNameCurrency;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }
    */




