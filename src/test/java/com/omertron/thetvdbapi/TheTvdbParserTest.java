package com.omertron.thetvdbapi;

import com.omertron.thetvdbapi.model.*;
import com.omertron.thetvdbapi.tools.TvdbParser;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TheTvdbParserTest extends AbstractTests{

    @BeforeClass
    public static void setUpClass() throws TvDbException {
        doConfiguration();
    }

    @Test
    public void testParserGetActorsInvalidURLEmpty() {
        assertThrows(TvDbException.class, () -> TvdbParser.getActors(""));
    }

    @Test
    public void testParserGetActorsInvalidURLNoApiKey() {
        assertThrows(TvDbException.class, () -> TvdbParser.getActors("http://thetvdb.com/api/series/80348/actors.xml"));

    }

    @Test
    public void testParserGetActorsInvalidURLMalformed() {
        assertThrows(TvDbException.class, () -> TvdbParser.getActors("http://thetvdb.com/api//387f00af-59ef-4ece-8637-bfa0b05532cb//series/80348/actors.xml"));

    }

    @Test
    public void testParserGetActorsValidURL() throws TvDbException {
         List<Actor> returnActors = TvdbParser.getActors("http://thetvdb.com/api/387f00af-59ef-4ece-8637-bfa0b05532cb/series/383203/actors.xml");
         assertFalse(returnActors.isEmpty());

    }

    @Test
    public void testParserGetAllEpisodesAllSeasons() throws TvDbException {
        List<Episode> returnEpisodes = TvdbParser.getAllEpisodes("http://thetvdb.com/api/387f00af-59ef-4ece-8637-bfa0b05532cb/series/383203/all/en", -1);
        assertFalse(returnEpisodes.isEmpty());
    }

    @Test
    public void testParserGetAllEpisodesBadNegative() throws TvDbException {
        List<Episode> returnEpisodes = TvdbParser.getAllEpisodes("http://thetvdb.com/api/387f00af-59ef-4ece-8637-bfa0b05532cb/series/383203/all/en", -20);
        assertTrue(returnEpisodes.isEmpty());
    }

    @Test
    public void testParserGetAllEpisodesBigSeason() throws TvDbException {
        List<Episode> returnEpisodes = TvdbParser.getAllEpisodes("http://thetvdb.com/api/387f00af-59ef-4ece-8637-bfa0b05532cb/series/383203/all/en", 20);
        assertTrue(returnEpisodes.isEmpty());
    }

    @Test
    public void testParserGetAllEpisodesBigPositive() throws TvDbException {
        List<Episode> returnEpisodes = TvdbParser.getAllEpisodes("http://thetvdb.com/api/387f00af-59ef-4ece-8637-bfa0b05532cb/series/383203/all/en", 200000);
        assertTrue(returnEpisodes.isEmpty());
    }

    @Test
    public void testParserGetAllEpisodesZeroSeason() throws TvDbException {
        List<Episode> returnEpisodes = TvdbParser.getAllEpisodes("http://thetvdb.com/api/387f00af-59ef-4ece-8637-bfa0b05532cb/series/383203/all/en", 0);
        assertTrue(returnEpisodes.isEmpty());
    }

    @Test
    public void testParserGetAllEpisodesBadURL() throws TvDbException {
        assertThrows(TvDbException.class, () -> TvdbParser.getAllEpisodes("http://thetvdb.com/api/387f00af-59ef-4ece-8637-bfa0b05532cb/seriessss/383203/all/en", -1));
    }

    @Test
    public void testParserGetBannersEmptyURL() {
        assertThrows(TvDbException.class, () -> TvdbParser.getBanners(""));
    }

    @Test
    public void testParserGetBannersNoXMLURL() {
        assertThrows(TvDbException.class, () -> TvdbParser.getBanners("http://thetvdb.com/api/387f00af-59ef-4ece-8637-bfa0b05532cb/series/80348"));
    }

    @Test
    public void testParserGetBannerNoSeriesCodeURL() {
        assertThrows(TvDbException.class, () -> TvdbParser.getBanners("http://thetvdb.com/api/387f00af-59ef-4ece-8637-bfa0b05532cb/banners.xml"));
    }

    @Test
    public void testParserGetBannerNoApiKeyURL() {
        assertThrows(TvDbException.class, () -> TvdbParser.getBanners("http://thetvdb.com/api/series/80348/banners.xml"));
    }

    @Test
    public void testParserGetBannerNoHttpURL() {
        assertThrows(TvDbException.class, () -> TvdbParser.getBanners("/387f00af-59ef-4ece-8637-bfa0b05532cb/series/80348/banners.xml"));
    }

    @Test
    public void testParserGetBannerValidURL() throws TvDbException{
        Banners returnBanners = TvdbParser.getBanners("http://thetvdb.com/api/387f00af-59ef-4ece-8637-bfa0b05532cb/series/80348/banners.xml");
        assertFalse(returnBanners == null);
    }

    @Test
    public void testParserGetEpisodeValidURL() throws TvDbException {
        Episode returnEpisode = TvdbParser.getEpisode("http://thetvdb.com/api/387f00af-59ef-4ece-8637-bfa0b05532cb/episodes/1534661/en");
        assertTrue(returnEpisode != null);
        assertEquals("80348", returnEpisode.getSeriesId());
        assertEquals("Chuck Versus the Tango", returnEpisode.getEpisodeName());
    }

    @Test
    public void testParserGetEpisodeEmptyURL() {
        assertThrows(TvDbException.class, () -> TvdbParser.getEpisode(""));
    }

    @Test
    public void testParserGetEpisodeNoCodeURL() {
        assertThrows(TvDbException.class, () -> TvdbParser.getEpisode("http://thetvdb.com/api/387f00af-59ef-4ece-8637-bfa0b05532cb/episodes"));
    }

    @Test
    public void testParserGetEpisodeNoApiKeyURL() {
        assertThrows(TvDbException.class, () -> TvdbParser.getEpisode("http://thetvdb.com/api/episodes/1534661/en"));
    }

    @Test
    public void testParserGetEpisodeNoHttpURL() {
        assertThrows(TvDbException.class, () -> TvdbParser.getEpisode("387f00af-59ef-4ece-8637-bfa0b05532cb/episodes/1534661/en"));
    }

    @Test
    public void testGetSeriesListValidURL() throws TvDbException{
        List<Series> seriesReturn = TvdbParser.getSeriesList("http://thetvdb.com/api/GetSeries.php?seriesname=Riverdale&language=en");
        assertFalse(seriesReturn.isEmpty());
        assertEquals("Riverdale", seriesReturn.get(0).getSeriesName());
        assertEquals("311954", seriesReturn.get(0).getSeriesId());
    }

    @Test
    public void testGetSeriesListEmptyURL() {
        assertThrows(TvDbException.class, () -> TvdbParser.getSeriesList(""));
    }

    @Test
    public void testGetSeriesListNoParametersURL() {
        assertThrows(TvDbException.class, () -> TvdbParser.getSeriesList("http://thetvdb.com/api/GetSeries.php"));
    }

    @Test
    public void testGetSeriesListNoHttpURL() {
        assertThrows(TvDbException.class, () -> TvdbParser.getSeriesList("/GetSeries.php?seriesname=Riverdale&language=en"));
    }

    @Test
    public void testGetSeriesListMisspelledURL() {
        assertThrows(TvDbException.class, () -> TvdbParser.getSeriesList("/http://thetvdbb.com/appi/GetSaries.php?seriesname=Riverdale&language=en"));
    }

    @Test
    public void testGetUpdatesValidURL() throws TvDbException{
        TVDBUpdates returnUpdates = TvdbParser.getUpdates("http://thetvdb.com/api/387f00af-59ef-4ece-8637-bfa0b05532cb/updates/updates_week.xml", 0);
        assertFalse(returnUpdates == null);
    }

    @Test
    public void testGetUpdatesEmptyURL() {
        assertThrows(TvDbException.class, () -> TvdbParser.getUpdates("", 0));
    }

    @Test
    public void testGetUpdatesInvalidURL() {
        assertThrows(TvDbException.class, () -> TvdbParser.getUpdates("http://thetvdb.com/api/updates/updates_week.xml", 0));
    }

    @Test
    public void testGetUpdatesNegativeSeries() {
        assertThrows(TvDbException.class, () -> TvdbParser.getUpdates("http://thetvdb.com/api/387f00af-59ef-4ece-8637-bfa0b05532cb/updates/updates_week.xml", -5));
    }

    @Test
    public void testGetUpdatesValidURLPositiveSeries() throws TvDbException{
        TVDBUpdates returnUpdates = TvdbParser.getUpdates("http://thetvdb.com/api/387f00af-59ef-4ece-8637-bfa0b05532cb/updates/updates_week.xml", 567);
        assertFalse(returnUpdates == null);
        assertFalse(returnUpdates.getEpisodeUpdates().isEmpty());
        assertFalse(returnUpdates.getSeriesUpdates().isEmpty());
    }

    @Test
    public void testGetLanguagesValidURL() throws TvDbException {
        List<Language> returnLanguages = TvdbParser.getLanguages("http://thetvdb.com/api/387f00af-59ef-4ece-8637-bfa0b05532cb/languages");
        assertFalse(returnLanguages.isEmpty());
        assertEquals("en", returnLanguages.get(0).getId());
    }

    @Test
    public void testGetLanguagesEmptyURL() {
        assertThrows(TvDbException.class, () -> TvdbParser.getLanguages(""));
    }

    @Test
    public void testGetLanguagesNoEndpointURL() {
        assertThrows(TvDbException.class, () -> TvdbParser.getLanguages("http://thetvdb.com/api/387f00af-59ef-4ece-8637-bfa0b05532cb"));
    }

    @Test
    public void testGetLanguagesNoApiKeyURL() {
        assertThrows(TvDbException.class, () -> TvdbParser.getLanguages("http://thetvdb.com/api/languages"));
    }

    @Test
    public void testGetLanguagesNoHttpURL() {
        assertThrows(TvDbException.class, () -> TvdbParser.getLanguages("/387f00af-59ef-4ece-8637-bfa0b05532cb/languages"));
    }



}
