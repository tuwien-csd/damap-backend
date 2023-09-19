package at.ac.tuwien.damap.domain.listener;

import at.ac.tuwien.damap.domain.DamapRevisionEntity;
import at.ac.tuwien.damap.security.SecurityService;
import lombok.extern.jbosslog.JBossLog;
import org.hibernate.envers.RevisionListener;

import jakarta.enterprise.inject.spi.CDI;

@JBossLog
public class DamapRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        if (revisionEntity instanceof DamapRevisionEntity) {

            final String userId = CDI.current().select(SecurityService.class).get().getUserId();
            final String username = CDI.current().select(SecurityService.class).get().getUserName();
            ((DamapRevisionEntity) revisionEntity).setChangedById(userId);
            ((DamapRevisionEntity) revisionEntity).setChangedBy(username);
        }
    }
}
