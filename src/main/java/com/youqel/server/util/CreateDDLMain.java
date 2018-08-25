package com.youqel.server.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import org.eclipse.persistence.internal.jpa.EntityManagerFactoryImpl;
import org.eclipse.persistence.sessions.DatabaseSession;
import org.eclipse.persistence.tools.schemaframework.SchemaManager;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CreateDDLMain {

    private static final String BUILDING_BLOCK = "objmodel";

    private CreateDDLMain() {
    }

    // objmodel_create-ddl-2013-03-20_09-19.sql
    private static String ddlFileNameCreate() {
        final String dateString = new SimpleDateFormat("yyyy-MM-dd_HH-mm")
            .format(Calendar.getInstance().getTime());
        return BUILDING_BLOCK + "_create-ddl-" + dateString + ".sql";
    }

    private static String ddlFileNameDrop() {
        final String dateString = new SimpleDateFormat("yyyy-MM-dd_HH-mm")
            .format(Calendar.getInstance().getTime());
        return BUILDING_BLOCK + "_drop-ddl-" + dateString + ".sql";
    }


    public static void main(final String[] args) throws Exception {

        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            new String[] {
                "classpath:/META-INF/spring/database-context.xml",
                "classpath:/META-INF/spring/database-props-createddl.xml",
            });

        final ResourceBundle bundle = ResourceBundle.getBundle("paths");
        final String outputDirectory = bundle.getString("directory.output.createDDL");

        if (!new File(outputDirectory).exists()) {
            log.debug("Directory " + outputDirectory + " does not exist!");
            context.close();
            return;
        }

        final String createFile = outputDirectory + "/" + ddlFileNameCreate();
        final String dropFile = outputDirectory + "/" + ddlFileNameDrop();
        final EntityManagerFactoryInfo entityManagerFactoryInfo = context
            .getBean("entityManagerFactory", EntityManagerFactoryInfo.class);
        final EntityManagerFactoryImpl emf = (EntityManagerFactoryImpl) entityManagerFactoryInfo
            .getNativeEntityManagerFactory();
        emf.getProperties();

        // emf.getProperties().put(PersistenceUnitProperties.DDL_GENERATION,
        // PersistenceUnitProperties.CREATE_OR_EXTEND);
        final DatabaseSession session = emf.getServerSession();
        final SchemaManager mgr = new SchemaManager(session);
        mgr.setCreateDatabaseSchemas(true);
        // set create ddl file name
        mgr.outputCreateDDLToFile(createFile);
        // set drop ddl file name
        mgr.outputDropDDLToFile(dropFile);
        // writes statement terminator
        mgr.setCreateSQLFiles(true);
        // generates both drop and create ddl
        mgr.replaceDefaultTables(true, true);

        mgr.closeDDLWriter();
        // FileHelpers.appendTerminator(createFile, ";");
        // FileHelpers.appendTerminator(dropFile, ";");
        context.close();
        log.debug("createFile: " + createFile);
        log.debug("dropFile: " + dropFile);

        replaceVarCharWithTextType(createFile);

    }

    private static void replaceVarCharWithTextType(final String fileName) {

        replace(fileName, "VARCHAR(255)", "TEXT");

    }

    /**
     * Replaces all occurrences of oldCharSequence to newCharSequence in the file with the given
     * name.
     *
     * @param fileName
     * @param oldCharSequence
     * @param newCharSequence
     */
    private static void replace(final String fileName, final String oldCharSequence,
        final String newCharSequence) {

        final String fileContents;

        try {
            fileContents = new String(Files.readAllBytes(Paths.get(fileName)),
                Charset.forName("UTF-8"));
        } catch (final IOException e) {
            log.error("Could not read contents of file {}", fileName);
            return;
        }

        try {
            Files.write(Paths.get(fileName),
                fileContents.replace(oldCharSequence, newCharSequence)
                .getBytes(Charset.forName("UTF-8")));
        } catch (final IOException e) {
            log.error("Could not write new file contents to file {}", fileName);
            return;
        }

        log.debug("Replaced all '{}' with '{}' in file {}", oldCharSequence, newCharSequence,
            fileName);

    }

}
