package com.learning_coordinator.matching.utility;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.objectdb.jdo.PMF;

public class JDOUtil {
        
    private static final Logger LOGGER = Logger.getLogger(JDOUtil.class.getName());
    protected PMF pmf = new PMF();

    public JDOUtil(String domain, Class<?> domainClass){
        createProperties(domain, domainClass);
    }

    private void createProperties(String domain, Class<?> domainClass){
        pmf.setConnectionURL("src/main/resources/" + domain + ".odb");
        pmf.setConnectionDriverName("Object DB");
        pmf.setConnectionFactory(domainClass);
        pmf.setConnectionFactoryName(domain);
        pmf.setMultithreaded(true);
        pmf.setTransactionType("RESOURCE_LOCAL");
        LOGGER.log(Level.INFO, pmf.getConnectionFactoryName() + "---" + pmf.getConnectionURL());
    }
}
