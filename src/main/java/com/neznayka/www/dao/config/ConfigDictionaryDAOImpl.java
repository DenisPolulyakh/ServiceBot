package com.neznayka.www.dao.config;


import com.neznayka.www.hibernate.Logging;
import com.neznayka.www.hibernate.Message;

import com.neznayka.www.hibernate.Tag;
import com.neznayka.www.model.CRUDRequestResponse;

import com.neznayka.www.model.Pager;

import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;




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



        Message message = crudRequestResponse.getMessage();
        Session session = sessionFactory.getCurrentSession();





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
        int id = message.getId();
        //получаем объект по id
        Message messageUpdate = (Message) session.get(Message.class,id);
        //устанавливаем новые значения
        messageUpdate.setValue(message.getValue());
        messageUpdate.setTags(message.getTags());
        //сохраняем изменения
        session.flush();
        crudRequestResponse.setMessage(messageUpdate);
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
        String keyAll = "";
        for (String key : keyWords) {
            keyAll = keyAll + key + " ";
        }
        //ищем фразу полностью
        /*if (keyAll.trim().length() > 5) {
            query = sessionFactory.getCurrentSession().
                    createCriteria(Message.class);
            query.createAlias("tags", "tagsJoin");
            query.add(Restrictions.like(("tagsJoin.tag"), keyAll.trim(), MatchMode.ANYWHERE));
            answers.addAll(query.list());
        }*/


            Disjunction or = Restrictions.disjunction();

            query = sessionFactory.getCurrentSession().createCriteria(Message.class);
            query.createAlias("tags", "tagsJoin");
            //сравниваем каждый тег
            for (String key : keyWords) {
                if (key.length() > 4) {
                    or.add(Restrictions.like("tagsJoin.tag", key));
                } else {
                    or.add(Restrictions.eq("tagsJoin.tag", key));
                }

            }
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

    @Override
    @Transactional
    public List<Logging> getLogging() {
        List<Logging> logs = new ArrayList<>();
        Criteria query = sessionFactory.getCurrentSession().createCriteria(Logging.class);
        query.addOrder(Order.desc("time"));
        log.info("READ");
        logs.addAll(query.list());
        return logs;
    }
}
