package com.neznayka.www.dao.config;


import com.neznayka.www.hibernate.Message;

import com.neznayka.www.hibernate.Tag;
import com.neznayka.www.model.CRUDRequestResponse;

import com.neznayka.www.model.Pager;

import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.Request;


import javax.sql.DataSource;
import java.util.*;

import static org.json.XMLTokener.entity;


@Repository
public class ConfigDictionaryDAOImpl implements ConfigDictionaryDAOIntf {
    private static final String CLASS_NAME = ConfigDictionaryDAOImpl.class.getName();
    private static final Logger log = Logger.getLogger(CLASS_NAME);
    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public CRUDRequestResponse create(CRUDRequestResponse crudRequestResponse) {

       /*DictionaryData responseInsert = null;
       Set<Tag> tags = new HashSet<Tag>();
       for(String tagstr:dictionaryMap.getTags()){
           Tag tag = new Tag();
           tag.setTag(tagstr);
           tags.add(tag);
       }
        Message message = new Message();
        message.setValue(dictionaryMap.getMessage());
        message.setTags(tags);*/

        Message message = crudRequestResponse.getMessage();
        Session session = sessionFactory.getCurrentSession();



        /*Integer id = message.getId();
        Set<Tag> tagsMessage = message.getTags();
        Set<Tag> tags = new HashSet<>();

        for(Tag tag:tagsMessage){
            log.info(tag);
            Tag saveTag = new Tag();
            saveTag.setTag(tag.getTag());
            saveTag.setMessage(message);
            tags.add(saveTag);
        }

        message.setTags(tags);*/

        session.save(message);
        // session.save(message);
        //log.info("Id entry="+id);
        CRUDRequestResponse response = new CRUDRequestResponse(message);

        return response;
    }

    @Override
    @Transactional
    public CRUDRequestResponse delete(CRUDRequestResponse crudRequestResponse) {
        int id = crudRequestResponse.getId();
        Session session = sessionFactory.getCurrentSession();
        Message message = (Message) session.get(Message.class, id);

        session.delete(message);
        crudRequestResponse.setId(id);
        return crudRequestResponse;
    }

    @Override
    @Transactional
    public CRUDRequestResponse update(CRUDRequestResponse crudRequestResponse) {
        Message message = crudRequestResponse.getMessage();
        System.out.println(message);
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(message);
        crudRequestResponse.setMessage(message);
        return crudRequestResponse;
    }

    @Override
    @Transactional
    public CRUDRequestResponse list(int offset, int records) {
        int start = offset;
        int stop = offset + records;
        log.info("Start=" + start);
        log.info("Stop=" + stop);
        System.out.print(start);
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Message.class);
        List<Message> messageList = (List<Message>) sessionFactory.getCurrentSession()
                .createCriteria(Message.class).setFirstResult(start).setMaxResults(stop).list();
        System.out.print(messageList);
        CRUDRequestResponse crudRequestResponse = new CRUDRequestResponse();
        crudRequestResponse.setRows(messageList);
        Pager pager = new Pager(offset, records, messageList.size());
        crudRequestResponse.setPager(pager);
        return crudRequestResponse;
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
    public List<Message> searchAnswer(String[] keyWords) {
        log.debug(keyWords);
        List<Message> answers = new ArrayList<>();
        Criteria query = sessionFactory.getCurrentSession().createCriteria(Message.class);
        query.createAlias("tags", "tagsJoin");
        Disjunction or = Restrictions.disjunction();
        String keyAll="";
        //сравниваем каждый тег
        for (String key : keyWords) {
            or.add(Restrictions.like("tagsJoin.tag", key));
            keyAll=keyAll+key+" ";
        }
        // дополнительное условие вся фраза как тег
        or.add(Restrictions.eq("tagsJoin.tag", keyAll.trim()));
        query.add(or);

        answers.addAll(query.list());


        return answers;
    }

    private MatchMode getMatchMode(String key) {
        if (key.toLowerCase().equals("при")) {
            return MatchMode.EXACT;
        }
        return MatchMode.ANYWHERE;
    }


    @Override
    @Transactional
    public Long getTotal() {
        String hql = "select count(*) from Message";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        Long total = (Long) query.uniqueResult();
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




