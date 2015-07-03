package com.monitorjbl.xlsx;

import com.monitorjbl.xlsx.exceptions.MissingSheetException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class StreamingReaderTest {

    @Test
    public void testTypes() throws Exception {
	SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	InputStream is = null;
	StreamingReader reader = null;
	try {
	    is = new FileInputStream(new File("src/test/resources/data_types.xlsx"));
	    reader = StreamingReader.builder().read(is);
	    List<List<Cell>> obj = new ArrayList<List<Cell>>();

	    for (Row r : reader) {
		List<Cell> o = new ArrayList<Cell>();
		for (Cell c : r) {
		    o.add(c);
		}
		obj.add(o);
	    }

	    assertEquals(6, obj.size());
	    List<Cell> row;

	    row = obj.get(0);
	    assertEquals(2, row.size());
	    assertEquals(Cell.CELL_TYPE_STRING, row.get(0).getCellType());
	    assertEquals(Cell.CELL_TYPE_STRING, row.get(1).getCellType());
	    assertEquals("Type", row.get(0).getStringCellValue());
	    assertEquals("Value", row.get(1).getStringCellValue());

	    row = obj.get(1);
	    assertEquals(2, row.size());
	    assertEquals(Cell.CELL_TYPE_STRING, row.get(0).getCellType());
	    assertEquals(Cell.CELL_TYPE_STRING, row.get(1).getCellType());
	    assertEquals("string", row.get(0).getStringCellValue());
	    assertEquals("jib-jab", row.get(1).getStringCellValue());

	    row = obj.get(2);
	    assertEquals(2, row.size());
	    assertEquals(Cell.CELL_TYPE_STRING, row.get(0).getCellType());
	    assertEquals(Cell.CELL_TYPE_NUMERIC, row.get(1).getCellType());
	    assertEquals("int", row.get(0).getStringCellValue());
	    assertEquals(10, row.get(1).getNumericCellValue(), 0);

	    row = obj.get(3);
	    assertEquals(2, row.size());
	    assertEquals(Cell.CELL_TYPE_STRING, row.get(0).getCellType());
	    assertEquals(Cell.CELL_TYPE_NUMERIC, row.get(1).getCellType());
	    assertEquals("double", row.get(0).getStringCellValue());
	    assertEquals(3.14, row.get(1).getNumericCellValue(), 0);

	    row = obj.get(4);
	    assertEquals(2, row.size());
	    assertEquals(Cell.CELL_TYPE_STRING, row.get(0).getCellType());
	    assertEquals(Cell.CELL_TYPE_NUMERIC, row.get(1).getCellType());
	    assertEquals("date", row.get(0).getStringCellValue());
	    assertEquals(df.parse("1/1/2014"), row.get(1).getDateCellValue());

	    row = obj.get(5);
	    assertEquals(7, row.size());
	    assertEquals(Cell.CELL_TYPE_STRING, row.get(0).getCellType());
	    assertEquals(Cell.CELL_TYPE_STRING, row.get(1).getCellType());
	    assertEquals(Cell.CELL_TYPE_STRING, row.get(2).getCellType());
	    assertEquals(Cell.CELL_TYPE_STRING, row.get(3).getCellType());
	    assertEquals(Cell.CELL_TYPE_STRING, row.get(4).getCellType());
	    assertEquals(Cell.CELL_TYPE_STRING, row.get(5).getCellType());
	    assertEquals(Cell.CELL_TYPE_STRING, row.get(6).getCellType());
	    assertEquals("long", row.get(0).getStringCellValue());
	    assertEquals("ass", row.get(1).getStringCellValue());
	    assertEquals("row", row.get(2).getStringCellValue());
	    assertEquals("look", row.get(3).getStringCellValue());
	    assertEquals("at", row.get(4).getStringCellValue());
	    assertEquals("it", row.get(5).getStringCellValue());
	    assertEquals("go", row.get(6).getStringCellValue());
	} finally {
	    IOUtils.closeQuietly(is);
	    IOUtils.closeQuietly(reader);
	}
    }

    @Test
    public void testGaps() throws Exception {
	InputStream is = null;
	StreamingReader reader = null;

	try {
	    is = new FileInputStream(new File("src/test/resources/gaps.xlsx"));
	    reader = StreamingReader.builder().read(is);

	    List<List<Cell>> obj = new ArrayList<List<Cell>>();

	    for (Row r : reader) {
		List<Cell> o = new ArrayList<Cell>();
		for (Cell c : r) {
		    o.add(c);
		}
		obj.add(o);
	    }

	    assertEquals(2, obj.size());
	    List<Cell> row;

	    row = obj.get(0);
	    assertEquals(2, row.size());
	    assertEquals(Cell.CELL_TYPE_STRING, row.get(0).getCellType());
	    assertEquals(Cell.CELL_TYPE_STRING, row.get(1).getCellType());
	    assertEquals("Dat", row.get(0).getStringCellValue());
	    assertEquals(0, row.get(0).getColumnIndex());
	    assertEquals(0, row.get(0).getRowIndex());
	    assertEquals("gap", row.get(1).getStringCellValue());
	    assertEquals(2, row.get(1).getColumnIndex());
	    assertEquals(0, row.get(1).getRowIndex());

	    row = obj.get(1);
	    assertEquals(2, row.size());
	    assertEquals(Cell.CELL_TYPE_STRING, row.get(0).getCellType());
	    assertEquals(Cell.CELL_TYPE_STRING, row.get(1).getCellType());
	    assertEquals("guuurrrrrl", row.get(0).getStringCellValue());
	    assertEquals(0, row.get(0).getColumnIndex());
	    assertEquals(6, row.get(0).getRowIndex());
	    assertEquals("!", row.get(1).getStringCellValue());
	    assertEquals(6, row.get(1).getColumnIndex());
	    assertEquals(6, row.get(1).getRowIndex());
	} finally {
	    IOUtils.closeQuietly(is);
	    IOUtils.closeQuietly(reader);
	}
    }

    @Test
    public void testMultipleSheets_alpha() throws Exception {
	InputStream is = null;
	StreamingReader reader = null;
	try

	{
	    is = new FileInputStream(new File("src/test/resources/sheets.xlsx"));
	    reader = StreamingReader.builder().sheetIndex(0).read(is);
	    List<List<Cell>> obj = new ArrayList<List<Cell>>();

	    for (Row r : reader) {
		List<Cell> o = new ArrayList<Cell>();
		for (Cell c : r) {
		    o.add(c);
		}
		obj.add(o);
	    }

	    assertEquals(1, obj.size());
	    List<Cell> row;

	    row = obj.get(0);
	    assertEquals(1, row.size());
	    assertEquals("stuff", row.get(0).getStringCellValue());
	} finally {
	    IOUtils.closeQuietly(is);
	    IOUtils.closeQuietly(reader);
	}
    }

    @Test
    public void testMultipleSheets_zulu() throws Exception {
	InputStream is = null;
	StreamingReader reader = null;
	try {
	    is = new FileInputStream(new File("src/test/resources/sheets.xlsx"));
	    reader = StreamingReader.builder().sheetIndex(1).read(is);
	    List<List<Cell>> obj = new ArrayList<List<Cell>>();

	    for (Row r : reader) {
		List<Cell> o = new ArrayList<Cell>();
		for (Cell c : r) {
		    o.add(c);
		}
		obj.add(o);
	    }

	    assertEquals(1, obj.size());
	    List<Cell> row;

	    row = obj.get(0);
	    assertEquals(1, row.size());
	    assertEquals("yeah", row.get(0).getStringCellValue());
	} finally {
	    IOUtils.closeQuietly(is);
	    IOUtils.closeQuietly(reader);
	}
    }

    @Test
    public void testSheetName_zulu() throws Exception {
	InputStream is = null;
	StreamingReader reader = null;
	try {
	    is = new FileInputStream(new File("src/test/resources/sheets.xlsx"));
	    reader = StreamingReader.builder().sheetName("SheetZulu").read(is);
	    List<List<Cell>> obj = new ArrayList<List<Cell>>();

	    for (Row r : reader) {
		List<Cell> o = new ArrayList<Cell>();
		for (Cell c : r) {
		    o.add(c);
		}
		obj.add(o);
	    }

	    assertEquals(1, obj.size());
	    List<Cell> row;

	    row = obj.get(0);
	    assertEquals(1, row.size());
	    assertEquals("yeah", row.get(0).getStringCellValue());
	} finally {
	    IOUtils.closeQuietly(is);
	    IOUtils.closeQuietly(reader);
	}
    }

    @Test
    public void testSheetName_alpha() throws Exception {
	InputStream is = null;
	StreamingReader reader = null;
	try {
	    is = new FileInputStream(new File("src/test/resources/sheets.xlsx"));
	    reader = StreamingReader.builder().sheetName("SheetAlpha").read(is);
	    List<List<Cell>> obj = new ArrayList<List<Cell>>();

	    for (Row r : reader) {
		List<Cell> o = new ArrayList<Cell>();
		for (Cell c : r) {
		    o.add(c);
		}
		obj.add(o);
	    }

	    assertEquals(1, obj.size());
	    List<Cell> row;

	    row = obj.get(0);
	    assertEquals(1, row.size());
	    assertEquals("stuff", row.get(0).getStringCellValue());
	} finally {
	    IOUtils.closeQuietly(is);
	    IOUtils.closeQuietly(reader);
	}
    }

    @Test(expected = MissingSheetException.class)
    public void testSheetName_missingInStream() throws Exception {
	InputStream is = null;
	StreamingReader reader = null;

	try {
	    is = new FileInputStream(new File("src/test/resources/sheets.xlsx"));
	    reader = StreamingReader.builder().sheetName("adsfasdfasdfasdf").read(is);
	    fail("Should have failed");
	} finally {
	    IOUtils.closeQuietly(is);
	    IOUtils.closeQuietly(reader);
	}
    }

    @Test
    public void testSheetName_missingInFile() throws Exception {
	File f = new File("src/test/resources/sheets.xlsx");
	StreamingReader reader = null;
	try {
	    reader = StreamingReader.builder().sheetName("adsfasdfasdfasdf").read(f);
	    fail("Should have failed");
	} catch (MissingSheetException e) {
	    assertTrue(f.exists());
	} finally {
	    IOUtils.closeQuietly(reader);
	}
    }

    @Test
    public void testIteration() throws Exception {
	File f = new File("src/test/resources/large.xlsx");
	StreamingReader reader = null;
	try {
	    reader = StreamingReader.builder().rowCacheSize(5).read(f);
	    int i = 1;
	    for (Row r : reader) {
		assertEquals(i, r.getCell(0).getNumericCellValue(), 0);
		assertEquals("#" + i, r.getCell(1).getStringCellValue());
		i++;
	    }
	} finally {
	    IOUtils.closeQuietly(reader);
	}
    }

    @Test
    public void testLeadingZeroes() throws Exception {
	File f = new File("src/test/resources/leadingZeroes.xlsx");
	StreamingReader reader = null;
	try {
	    reader = StreamingReader.builder().read(f);
	    Iterator<Row> iter = reader.iterator();
	    iter.hasNext();

	    Row r1 = iter.next();
	    assertEquals(1, r1.getCell(0).getNumericCellValue(), 0);
	    assertEquals("1", r1.getCell(0).getStringCellValue());
	    assertEquals(Cell.CELL_TYPE_NUMERIC, r1.getCell(0).getCellType());

	    Row r2 = iter.next();
	    assertEquals(2, r2.getCell(0).getNumericCellValue(), 0);
	    assertEquals("0002", r2.getCell(0).getStringCellValue());
	    assertEquals(Cell.CELL_TYPE_STRING, r2.getCell(0).getCellType());
	} finally {
	    IOUtils.closeQuietly(reader);
	}
    }
}
