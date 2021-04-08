package test;

import com.codeborne.xlstest.XLS;
import net.lingala.zip4j.exception.ZipException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

import static utils.Files.getXls;
import static utils.Files.readTextFromDocFile;
import static utils.Files.readTextFromDocxFile;
import static utils.Files.readTextFromPath;
import static utils.Zip.unzip;

public class ReadFilesTest {

    String expectedData = "Hello world! Hi! Hello! Privet!";

    @Test
    public void readDoc() throws IOException {
        String docFilePath = "src/test/resources/files/doc.doc";

        String actualData = readTextFromDocFile(docFilePath);

        assertThat(actualData, containsString(expectedData));
    }

    @Test
    public void readDocx() throws IOException {
        String docxFilePath = "src/test/resources/files/docx.docx";

        String actualData = readTextFromDocxFile(docxFilePath);

        assertThat(actualData, containsString(expectedData));
    }

    @Test
    public void readSimpleXls() {
        String xlsFilePath = "src/test/resources/files/xls.xls";

        XLS xls = getXls(xlsFilePath);

        assertThat(xls, XLS.containsText(expectedData));
    }

    @Test
    public void readCellXls() {
        String xlsFilePath = "src/test/resources/files/xls.xls";

        XLS xls = getXls(xlsFilePath);
        String actualData = xls.excel.getSheetAt(0).getRow(0).getCell(0).toString();

        assertThat(actualData, containsString(expectedData));
    }

    @Test
    public void readXlsx() {
        String xlsxFilePath = "src/test/resources/files/xlsx.xlsx";

        XLS xls = getXls(xlsxFilePath);
        String actualData = xls.excel.getSheetAt(0).getRow(0).getCell(0).toString();

        assertThat(actualData, containsString(expectedData));
    }

    @Test
    public void readZip() throws IOException, ZipException {
        String zipFilePath = "src/test/resources/files/zipped.zip";
        String unzipFolderPath = "src/test/resources/files/unzip";
        String zipPassword = "123";
        String unzipDocFilePath = "src/test/resources/files/unzip/zipped.txt";
        String expectedData = "This is txt file in zip archive with password";

        unzip(zipFilePath, unzipFolderPath, zipPassword);
        String actualData = readTextFromPath(unzipDocFilePath);

        assertThat(actualData, containsString(expectedData));
    }
}
