package org.damap.base.rest.gdpr.service;

import org.damap.base.rest.gdpr.domain.GdprQuery;
import org.damap.base.rest.gdpr.domain.HqlQuery;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class HqlQueryUtil {

    public HqlQuery buildHqlQuery(GdprQuery query, boolean extended) {

        HqlQuery hqlQuery = new HqlQuery();
        hqlQuery.setEntityName(query.getRoot().getSimpleName());

        // Build select query
        String entityAlias = "e1";
        StringBuilder select = new StringBuilder();
        select.append(entityAlias).append(".").append(query.getKey()).append(" as userId");

        // GDPR context
        appendSelect(select, query.getContext(), entityAlias);
        StringBuilder contextJoin = new StringBuilder();
        for (int i = 0; i < query.getContextJoins().size(); i++) {
            GdprQuery joinQ = query.getContextJoins().get(i);
            addContextJoin(entityAlias, select, contextJoin, joinQ, "c" + i, "", 0);
        }

        // Base GDPR data
        appendSelect(select, query.getBase(), entityAlias);
        StringBuilder baseJoin = new StringBuilder();
        for (int i = 0; i < query.getBaseJoins().size(); i++) {
            GdprQuery joinQ = query.getBaseJoins().get(i);
            addJoin(entityAlias, select, baseJoin, joinQ, "j" + i);
        }

        // Extended GDPR data
        StringBuilder extendedJoin = new StringBuilder();
        if (extended && !query.getExtended().isEmpty()) {
            appendSelect(select, query.getExtended(), entityAlias);
            for (int i = 0; i < query.getExtendedJoins().size(); i++) {
                GdprQuery joinQ = query.getExtendedJoins().get(i);
                addJoin(entityAlias, select, extendedJoin, joinQ, "ej" + i);
            }
        }

        // Safe to construct query like this, because all the information comes from classes (is predefined).
        // Using prepared statements only works partially as the entity (=table) cannot be set as parameter.
        hqlQuery.setHql("SELECT new map(" + select + ")" +
                        " from " + query.getRoot().getSimpleName() + " " + entityAlias + contextJoin + baseJoin + extendedJoin +
                        " where " + entityAlias + ".universityId = :id");
        return hqlQuery;
    }

    /**
     * Recursive method to build join statement for multiple tables.
     *
     * @param entityAlias alias for the table to join on
     * @param select      SELECT values
     * @param join        JOIN statements
     * @param joinQ       JOIN query objects
     * @param joinAlias   alias for the joined table
     * @param prefix      prefix for the property alias
     * @param i           recursion counter
     */
    private void addContextJoin(String entityAlias, StringBuilder select, StringBuilder join, GdprQuery joinQ,
                                String joinAlias, String prefix, int i) {
        prefix += joinQ.getFieldName() + "_";
        appendSelect(select, joinQ.getBase(), joinAlias, prefix);
        join.append(" LEFT JOIN ")
                .append(entityAlias).append(".").append(joinQ.getFieldName())
                .append(" ").append(joinAlias);
        if (joinQ.getContextJoins() != null) {
            for (int j = 0; j < joinQ.getContextJoins().size(); j++) {
                GdprQuery joinQ2 = joinQ.getContextJoins().get(j);
                addContextJoin(joinAlias, select, join, joinQ2, joinAlias + j, prefix, i++);
            }
        }
    }

    /**
     * Joins {@code joinQ} to the given query parts ({@code select} and {@code join}).
     *
     * @param entityAlias alias for the table to join on
     * @param select      SELECT values
     * @param join        JOIN statements
     * @param joinQ       JOIN query objects
     * @param joinAlias   alias for the joined table
     */
    private void addJoin(String entityAlias, StringBuilder select, StringBuilder join, GdprQuery joinQ, String joinAlias) {
        appendSelect(select, joinQ.getBase(), joinAlias, joinQ.getFieldName() + "_");
        join.append(" LEFT JOIN ")
                .append(entityAlias).append(".").append(joinQ.getFieldName())
                .append(" ").append(joinAlias);
    }

    /**
     * Adds the given properties to the select statement.
     *
     * @param select     SELECT statement
     * @param properties properties to append
     * @param alias      alias for the table
     */
    private void appendSelect(StringBuilder select, List<String> properties, String alias) {
        appendSelect(select, properties, alias, "");
    }

    /**
     * Adds the given properties to the select statement and prefixes their aliases.
     * (Used for nested properties.)
     *
     * @param select     SELECT statement
     * @param properties properties to append
     * @param alias      alias for the table
     * @param prefix     prefix for the property alias
     */
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
