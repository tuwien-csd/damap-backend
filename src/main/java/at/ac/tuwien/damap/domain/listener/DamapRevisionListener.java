package at.ac.tuwien.damap.domain.listener;

import at.ac.tuwien.damap.domain.DamapRevisionEntity;
import org.hibernate.envers.RevisionListener;

public class DamapRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {

        DamapRevisionEntity damapRevisionEntity = (DamapRevisionEntity) revisionEntity;

        //TODO determine what/who performed the change and insert into revision table

//        damapRevisionEntity.setChanged_by( change_by );
    }
}
