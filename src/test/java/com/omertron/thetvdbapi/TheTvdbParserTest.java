package com.omertron.thetvdbapi;

import com.omertron.thetvdbapi.model.Actor;
import com.omertron.thetvdbapi.model.Episode;
import com.omertron.thetvdbapi.tools.TvdbParser;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TheTvdbParserTest {

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




}
