package com.npn.spring.learning.logger.smallsite.models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ProvidedObjectTest {

    private int i = 0;

    @Test
    public void forbidNullWhenProvidedObjectCreated(){
        List<TestClass> list = new ArrayList<>();
        list.add(new TestClass("","",""));
        list.add(new TestClass("","test","test"));
        list.add(new TestClass("test","test",""));
        list.add(new TestClass("test","","test"));
        list.add(new TestClass(null,null,null));
        list.add(new TestClass(null,"test","test"));
        list.add(new TestClass("test","test",null));
        list.add(new TestClass("test",null,"test"));
        try{
            TestClass testClass = list.get(i++);
            testMethod(testClass.getLocation(),testClass.getContentType(), testClass.getHref());
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
            if (i!=list.size()) {
                forbidNullWhenProvidedObjectCreated();
            }
        }
    }

    private void testMethod(String location, String contentType, String href) {
        ProvidedObject object = new ProvidedObject(location,contentType,href);
    }

    public static class TestClass {
        String location;
        String contentType;
        String href;

        public TestClass() {
        }

        public TestClass(String location, String contentType, String href) {
            this.location = location;
            this.contentType = contentType;
            this.href = href;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }
    }
}