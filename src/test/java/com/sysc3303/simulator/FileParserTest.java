package com.sysc3303.simulator;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class FileParserTest {

    static DateFormat df;

    @BeforeClass
    public static void setUp(){
        df = new SimpleDateFormat("kk:mm:ss", Locale.CANADA);
    }

    @Test
    public void testEventsFileProducesArray() throws Exception {
        FileParser fp = FileParser.getInstance();
        fp.parse("simpleTestEvents.txt");

        System.out.println(fp.getParsed());
        Event event = new Event(fp.getParsed().get(0));
        System.out.println(event.getTimestamp());
        Assert.assertEquals(2, fp.getParsed().size());
    }
}
