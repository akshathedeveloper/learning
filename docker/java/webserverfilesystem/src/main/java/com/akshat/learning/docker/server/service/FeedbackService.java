package com.akshat.learning.docker.server.service;

import com.akshat.learning.docker.server.entity.Feedback;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.val;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URI;
import java.nio.file.Paths;

@Service
public class FeedbackService {
    public static final String TEMP_STORAGE_FILE_PREFIX = "temp-feed";
    public static final String STORAGE_FILE_PREFIX = "feed";
    public void saveFeedback(Feedback feedback) throws IOException {
        var time = feedback.getTime();
        val fileName = String.format("%s_%s_%s_%s_%s_%s%s", TEMP_STORAGE_FILE_PREFIX, time.getYear(), time.getMonth(), time.getDayOfMonth(), time.getHour(), time.getMinute(), ".csv");
        val currDir = System.getProperty("user.dir");
        val filePath = Paths.get(currDir, "temp", fileName).toUri();
        System.out.println("temp path:"+ filePath);
        appendFeedback(feedback, filePath);
    }

    public void publishFeedbacks() throws IOException {
        val currDir = System.getProperty("user.dir");
        val sourceDirPath = Paths.get(currDir, "temp").toUri();
        val destinationDirPath = Paths.get(currDir, "permanent").toUri();
        File sourceDir = new File(sourceDirPath);
        File destinationDir = new File(destinationDirPath);
        copyDirectory(sourceDir, destinationDir);
    }

    private void copyDirectory(File sourceDirectory, File destinationDirectory) throws IOException {
        if (!destinationDirectory.exists()) {
            destinationDirectory.mkdir();
        }
        for (String f : sourceDirectory.list()) {
            val destinationFileName = f.replace(TEMP_STORAGE_FILE_PREFIX, STORAGE_FILE_PREFIX);
            var sourceFile = new File(sourceDirectory, f);
            copyFile(sourceFile, new File(destinationDirectory, destinationFileName));
            sourceFile.delete();
        }
    }

    public void copyFile(File source, File destination) throws IOException {
        try (InputStream in = new FileInputStream(source);
             OutputStream out = new FileOutputStream(destination)) {
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) > 0) {
                out.write(buf, 0, length);
            }
        }
    }

    private static void appendFeedback(Feedback feedback, URI filePath) throws IOException {
        File file = new File(filePath);
        try (
                FileWriter writer = new FileWriter(file, true)
        ) {
            file.createNewFile();
            StatefulBeanToCsv<Feedback> beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(feedback);
        } catch (CsvRequiredFieldEmptyException e) {
            throw new RuntimeException(e);
        } catch (CsvDataTypeMismatchException e) {
            throw new RuntimeException(e);
        }
    }
}
