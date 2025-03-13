package dev.patriciafb.stackmentor.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;




public class ResourceTest {

    @Test
    public void testResourceConstructorAndGetters() {
        byte[] fileData = {1, 2, 3};
        Resource resource = new Resource("Title", "Description", "Category", "Subcategory", "http://example.com", fileData, "fileType", "fileName");

        assertEquals("Title", resource.getTitle());
        assertEquals("Description", resource.getDescription());
        assertEquals("Category", resource.getCategory());
        assertEquals("Subcategory", resource.getSubcategory());
        assertEquals("http://example.com", resource.getUrl());
        assertArrayEquals(fileData, resource.getFileData());
        assertEquals("fileType", resource.getFileType());
        assertEquals("fileName", resource.getFileName());
    }

    @Test
    public void testSetters() {
        Resource resource = new Resource();
        resource.setTitle("New Title");
        resource.setDescription("New Description");
        resource.setCategory("New Category");
        resource.setSubcategory("New Subcategory");
        resource.setUrl("http://newexample.com");
        byte[] newFileData = {4, 5, 6};
        resource.setFileData(newFileData);
        resource.setFileType("newFileType");
        resource.setFileName("newFileName");

        assertEquals("New Title", resource.getTitle());
        assertEquals("New Description", resource.getDescription());
        assertEquals("New Category", resource.getCategory());
        assertEquals("New Subcategory", resource.getSubcategory());
        assertEquals("http://newexample.com", resource.getUrl());
        assertArrayEquals(newFileData, resource.getFileData());
        assertEquals("newFileType", resource.getFileType());
        assertEquals("newFileName", resource.getFileName());
    }

    @Test
    public void testToString() {
        byte[] fileData = {1, 2, 3};
        Resource resource = new Resource("Title", "Description", "Category", "Subcategory", "http://example.com", fileData, "fileType", "fileName");
        String expected = "Resource{id=null, title='Title', description='Description', category='Category', subcategory='Subcategory', url='http://example.com', fileType='fileType', fileName='fileName'}";
        assertEquals(expected, resource.toString());
    }
}
