package com.omertron.thetvdbapi;

import com.omertron.thetvdbapi.tools.DOMHelper;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.w3c.dom.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DOMHelperTest extends AbstractTests{

    @BeforeClass
    public static void setUpClass() throws TvDbException {
        doConfiguration();
    }

    //Testing valid URL for getting en event document
    @Test
    public void testDOMGetEventDocFromValidURL() throws TvDbException {
        Document returnDoc = DOMHelper.getEventDocFromUrl("http://thetvdb.com/api/387f00af-59ef-4ece-8637-bfa0b05532cb/series/383203/actors.xml");
        assertFalse(returnDoc == null);
    }

    @Test
    public void testDOMGetEventDocFromEmptyURL() {
        assertThrows(TvDbException.class, () -> DOMHelper.getEventDocFromUrl(""));
    }

    @Test
    public void testDOMGetEventDocFromNoXMLURL() {
        assertThrows(TvDbException.class, () -> DOMHelper.getEventDocFromUrl("http://thetvdb.com/api/387f00af-59ef-4ece-8637-bfa0b05532cb/series/383203"));
    }

    @Test
    public void testDOMGetEventDocFromNoSeriesCodeURL() {
        assertThrows(TvDbException.class, () -> DOMHelper.getEventDocFromUrl("http://thetvdb.com/api/387f00af-59ef-4ece-8637-bfa0b05532cb/series/actors.xml"));
    }

    @Test
    public void testDOMGetEventDocFromNoApiKeyURL() {
        assertThrows(TvDbException.class, () -> DOMHelper.getEventDocFromUrl("http://thetvdb.com/api/series/383203/actors.xml"));
    }

    @Test
    public void testDOMGetEventDocFromNoHttpURL() {
        assertThrows(TvDbException.class, () -> DOMHelper.getEventDocFromUrl("/387f00af-59ef-4ece-8637-bfa0b05532cb/series/383203/actors.xml"));
    }

    @Test
    public void testDOMGetValueFromNullElement() {
        Element testEll = null;
        String valueReturn = DOMHelper.getValueFromElement(testEll, "id");
        assertTrue(valueReturn.equals(""));
    }


}
