package com.neznayka.www.processor;

import com.neznayka.www.dao.config.ConfigDictionaryDAOIntf;
import com.neznayka.www.hibernate.Message;
import com.neznayka.www.hibernate.Tag;
import com.neznayka.www.utils.BotUtilMethods;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author Polulyakh Denis
 * @date 19.03.2017
 */
public class PhraseProcessor {
    private static final String CLASS_NAME = "PhraseProcessor";
    private static final Logger log = Logger.getLogger(CLASS_NAME);
    private ConfigDictionaryDAOIntf configDAO;

    public String getMessageToAnswer(String message) {
        final String METHOD_NAME = "getMessageToAnswer";
        String text = BotUtilMethods.getPropertyFromJSON(message,"text");
        log.info(CLASS_NAME + " " + METHOD_NAME + " question: " + text);
        text =  BotUtilMethods.replaseSymbols(text);
        text = text.toLowerCase();
        List<Message> listMessage = configDAO.listMessage();
        for(Message m:listMessage){
            List<Tag> tagList = m.getTags();
            int k = 0;
            for(Tag t:tagList){
                String[] split = t.getTag().split(" ");
                log.info(CLASS_NAME + " " + METHOD_NAME + " TAG WORD " + split);
                for(String str: split) {
                    if (text.contains(str.toLowerCase())) {
                        k++;
                    }
                }
                log.info(CLASS_NAME + " " + METHOD_NAME + " К " + k);
                if(k/split.length*100>80){
                    return m.getValue();
                }
            }


        }


        return "Простите я Вас, не понял :)";
    }


    public void setConfigDAO(ConfigDictionaryDAOIntf configDAO) {
        this.configDAO = configDAO;
    }
}
