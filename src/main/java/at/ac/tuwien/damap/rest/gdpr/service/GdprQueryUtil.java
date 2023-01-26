package at.ac.tuwien.damap.rest.gdpr.service;

import at.ac.tuwien.damap.rest.gdpr.domain.GdprQuery;
import at.ac.tuwien.damap.rest.gdpr.domain.HqlQuery;
import lombok.experimental.UtilityClass;
import lombok.extern.jbosslog.JBossLog;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
@JBossLog
public class GdprQueryUtil {

    public HqlQuery buildHqlQuery(GdprQuery query, boolean extended) {

        HqlQuery hqlQuery = new HqlQuery();
        hqlQuery.setEntity(query.getEntityName());

        // Build select query
        String entityAlias = "e1";
        StringBuilder select = new StringBuilder();
        select.append(entityAlias).append(".").append(query.getKey()).append(" as userId");

        // Base GDPR data
        appendSelect(select, query.getBase(), entityAlias);

        StringBuilder baseJoin = new StringBuilder();
        for (int i = 0; i < query.getBaseJoins().size(); i++) {
            GdprQuery joinQ = query.getBaseJoins().get(i);
            buildJoinQuery(entityAlias, select, baseJoin, joinQ, "j" + i);
        }

        // Extended GDPR data
        StringBuilder extendedJoin = new StringBuilder();
        if (extended && !query.getExtended().isEmpty()) {
            appendSelect(select, query.getExtended(), entityAlias);

            for (int i = 0; i < query.getExtendedJoins().size(); i++) {
                GdprQuery joinQ = query.getExtendedJoins().get(i);
                buildJoinQuery(entityAlias, select, extendedJoin, joinQ, "ej" + i);
            }
        }

        hqlQuery.setHql("SELECT new map(" + select + ")" +
                        " from " + query.getEntityName() + " " + entityAlias + baseJoin + extendedJoin +
                        " where " + entityAlias + ".universityId = :id");
        log.info("HQL: " + hqlQuery.getHql());
        return hqlQuery;
    }

    private void buildJoinQuery(String entityAlias, StringBuilder select, StringBuilder join, GdprQuery joinQ, String joinAlias) {
        appendSelect(select, joinQ.getBase(), joinAlias, joinQ.getEntityName() + "_");
        join.append(" LEFT JOIN ")
                .append(entityAlias).append(".").append(joinQ.getEntityName())
                .append(" ").append(joinAlias);
    }

    private void appendSelect(StringBuilder select, List<String> properties, String alias) {
        appendSelect(select, properties, alias, "");
    }

    private void appendSelect(StringBuilder select, List<String> properties, String alias, String prefix) {
        List<String> columns = new ArrayList<>();
        for (String property : properties) {
            columns.add(alias + "." + property + " as " + prefix + property);
        }
        if (!columns.isEmpty()) {
            select.append(", ").append(String.join(", ", columns));
        }
    }

}
