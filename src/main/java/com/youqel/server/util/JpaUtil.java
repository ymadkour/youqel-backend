package com.youqel.server.util;

import java.util.Vector;

import javax.persistence.EntityManager;

import org.eclipse.persistence.internal.jpa.EntityManagerFactoryImpl;
import org.eclipse.persistence.jpa.JpaCache;
import org.eclipse.persistence.jpa.JpaHelper;
import org.eclipse.persistence.sessions.DatabaseRecord;
import org.eclipse.persistence.tools.schemaframework.SchemaManager;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class JpaUtil {

    private static final String FLYWAY_TABLE = "flyway_schema_version";

    private JpaUtil() {
    }

    public static void writeDDLToDatabase(final EntityManagerFactoryInfo emfi) {
        final EntityManagerFactoryImpl emf = (EntityManagerFactoryImpl) emfi
            .getNativeEntityManagerFactory();
        final SchemaManager schemaManager = new SchemaManager(JpaHelper.getServerSession(emf));
        emf.getServerSession().beginTransaction();

        // no side effects - simply sets writers to null
        schemaManager.outputDDLToDatabase();

        schemaManager.createDefaultTables(true/* generateFKConstraints */);

        emf.getServerSession().commitTransaction();
        schemaManager.closeDDLWriter();
    }


    /**
     * H2 database does not support truncate. This method can be used in Tests with H2 database.
     *
     * @param emfi
     */

    public static void clearTestData(final EntityManagerFactoryInfo emfi) {
        final EntityManagerFactoryImpl emf = (EntityManagerFactoryImpl) emfi
            .getNativeEntityManagerFactory();

        final SchemaManager schemaManager = new SchemaManager(emf.getServerSession());

        @SuppressWarnings("rawtypes")
        final Vector tableInfos = schemaManager.getTableInfo("", "public", null,
            new String[] {"TABLE"});

        emf.getServerSession().beginTransaction();

        for (final Object table : tableInfos) {
            final DatabaseRecord rec = (DatabaseRecord) table;
            final String tableName = (String) rec.get("TABLE_NAME");

            if (!FLYWAY_TABLE.equals(tableName)) {
                emf.getServerSession().executeNonSelectingSQL("TRUNCATE " + tableName + " CASCADE");
            }
        }
        emf.getServerSession().commitTransaction();
        log.info("cleared test data");
    }

    /**
     * H2 database does not support truncate. This method can be used in Tests with H2 database.
     *
     * @param emfi
     */
    public static void clearTestDataH2(final EntityManagerFactoryInfo emfi) {
        final EntityManagerFactoryImpl emf = (EntityManagerFactoryImpl) emfi
            .getNativeEntityManagerFactory();

        final SchemaManager schemaManager = new SchemaManager(emf.getServerSession());

        @SuppressWarnings("rawtypes")
        final Vector tableInfos = schemaManager.getTableInfo("XPETESTINMEMDB", "PUBLIC", null,
            new String[] {"TABLE"});

        @SuppressWarnings("rawtypes")
        final Vector secTableInfos = schemaManager.getTableInfo("XPETESTINMEMDB", "SEC", null,
            new String[] {"TABLE"});

        emf.getServerSession().beginTransaction();
        emf.getServerSession().executeNonSelectingSQL("SET REFERENTIAL_INTEGRITY FALSE");

        for (final Object table : tableInfos) {
            final DatabaseRecord rec = (DatabaseRecord) table;
            final String tableName = (String) rec.get("TABLE_NAME");

            if (!FLYWAY_TABLE.equals(tableName)) {
                emf.getServerSession().executeNonSelectingSQL("TRUNCATE TABLE " + tableName);
            }
        }

        for (final Object table : secTableInfos) {
            final DatabaseRecord rec = (DatabaseRecord) table;
            final String tableName = "SEC." + (String) rec.get("TABLE_NAME");

            if (!FLYWAY_TABLE.equals(tableName)) {
                emf.getServerSession().executeNonSelectingSQL("TRUNCATE TABLE " + tableName);
            }
        }

        emf.getServerSession().executeNonSelectingSQL("SET REFERENTIAL_INTEGRITY TRUE");
        emf.getServerSession().commitTransaction();
        log.info("cleared test data");
    }

    public static JpaCache getJpaCache(final EntityManager em) {
        if (!JpaHelper.isEclipseLink(em)) {
            throw new RuntimeException("ERROR NOT A ECLIPSELINK ENTITYMANAGER");
        }
        final JpaCache cache = (JpaCache) em.getEntityManagerFactory().getCache();
        return cache;
    }




}
