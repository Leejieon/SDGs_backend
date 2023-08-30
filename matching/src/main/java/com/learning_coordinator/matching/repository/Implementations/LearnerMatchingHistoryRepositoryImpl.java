package com.learning_coordinator.matching.repository.Implementations;

import com.learning_coordinator.matching.domain.LearnerMatchingHistory;
import com.learning_coordinator.matching.repository.LearnerMatchingHistoryRepository;
import com.learning_coordinator.matching.utility.JDOUtil;

// import java.io.File;
// import java.io.FileNotFoundException;
// import java.nio.file.Path;
// import java.nio.file.Paths;
import java.util.Collections;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;
import javax.jdo.identity.LongIdentity;

import org.springframework.stereotype.Repository;
import com.objectdb.jdo.PMF;

@Repository
public class LearnerMatchingHistoryRepositoryImpl extends JDOUtil implements LearnerMatchingHistoryRepository{
    private static final Logger LOGGER = Logger.getLogger(LearnerMatchingHistoryRepositoryImpl.class.getName());
    private PMF pmf;
    public LearnerMatchingHistoryRepositoryImpl() {
        super("LearnerMatchingHistory", LearnerMatchingHistory.class);
        pmf = super.pmf;
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        // Path path = Paths.get("src/main/resources/LearnerMatchingHistory.odb");
        // File file = path.toFile();
        LearnerMatchingHistory learnerMatchingHistory;
        try{
            learnerMatchingHistory = (LearnerMatchingHistory)pm.getObjectById(new LongIdentity(LearnerMatchingHistory.class, 0));
        }catch(JDOObjectNotFoundException e){
            tx.begin();
            learnerMatchingHistory = new LearnerMatchingHistory();
            learnerMatchingHistory.setId((long) 0);
            Set<Long> matchingset = Collections.emptySet();
            learnerMatchingHistory.setMatchingHistory(matchingset);
            pm.makePersistent(learnerMatchingHistory);
            tx.commit();
        } finally {
            if(tx.isActive()){
                tx.rollback();
                LOGGER.log(Level.WARNING, "Creation Commit Failed --- Rollback");
            } else {
                LOGGER.log(Level.INFO, "Initialization Success - LearnerMatchingHistory.odb");
            }    
            pm.close();    
        }
    }

    @Override
    public void updateMatchingHistory(Long matchingUserId, Long matchingOpponentId, Boolean flag) {
        //flag - true : add, false : remove
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try{
            tx.begin();
            LearnerMatchingHistory learnerMatchingHistory = (LearnerMatchingHistory)pm.getObjectById(new LongIdentity(LearnerMatchingHistory.class, matchingUserId));
            if(flag)
                learnerMatchingHistory.getMatchingHistory().add(matchingOpponentId);
            else
                learnerMatchingHistory.getMatchingHistory().remove(matchingOpponentId);
            tx.commit();
        } catch(JDOObjectNotFoundException e) {
            LOGGER.log(Level.INFO,"Initial MatchingHistory Commit");
            if(!tx.isActive()){
                tx.begin();
            }
            LearnerMatchingHistory learnerMatchingHistory = new LearnerMatchingHistory();
            learnerMatchingHistory.setId(matchingUserId);
            Set<Long> matchingset = Collections.emptySet();
            if(flag)
                learnerMatchingHistory.getMatchingHistory().add(matchingOpponentId);
            else
                LOGGER.log(Level.SEVERE,"OutOfBoundException MatchingHistorySet...");
            learnerMatchingHistory.setMatchingHistory(matchingset);
            pm.makePersistent(learnerMatchingHistory);
            tx.commit();
        } finally {
            if(tx.isActive()){
                tx.rollback();
                LOGGER.log(Level.WARNING, "Commit Failed --- Rollback");
            }    
            pm.close();
        }
    }

    @Override
    public Set<Long> getMatchingHistory(Long matchingUserId) {
        PersistenceManager pm = pmf.getPersistenceManager();
        LearnerMatchingHistory learnerMatchingHistory;
        try{
            learnerMatchingHistory = (LearnerMatchingHistory)pm.getObjectById(new LongIdentity(LearnerMatchingHistory.class, matchingUserId));
        } catch(JDOObjectNotFoundException e) {
            LOGGER.log(Level.INFO, "First Matching");
            return Collections.emptySet();
        }finally {
            pm.close();
        }
        return learnerMatchingHistory.getMatchingHistory();
    }
}
