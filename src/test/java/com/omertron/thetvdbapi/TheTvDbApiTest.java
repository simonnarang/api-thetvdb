/*
 *      Copyright (c) 2004-2016 Matthew Altman & Stuart Boston
 *
 *      This file is part of TheTVDB API.
 *
 *      TheTVDB API is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      TheTVDB API is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with TheTVDB API.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.omertron.thetvdbapi;

import com.omertron.thetvdbapi.model.Actor;
import com.omertron.thetvdbapi.model.Banners;
import com.omertron.thetvdbapi.model.Episode;
import com.omertron.thetvdbapi.model.Language;
import com.omertron.thetvdbapi.model.Series;
import com.omertron.thetvdbapi.model.TVDBUpdates;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * JUnit tests for TheTvDb class. The tester must enter the API key for these tests to work. Requires JUnit 4.5.
 *
 * @author stuart.boston
 *
 */
public class TheTvDbApiTest extends AbstractTests {

    private final TheTVDBApi tvdb;
    private static final String LANGUAGE_ENGLISH = "en";
    private static final String TVDBID = "80348";
    private static final String SERIES_NAME = "Chuck";
    private static final String EPISODE_ID = "1534661";
    private static final String SEASON_ID = "27984";
    private static final String SEASON_YEAR = "2007";

    public TheTvDbApiTest() {
        tvdb = new TheTVDBApi("387f00af-59ef-4ece-8637-bfa0b05532cb");
    }

    @BeforeClass
    public static void setUpClass() throws TvDbException {
        doConfiguration();
    }

    @Test
    public void testNaming() throws TvDbException {
        LOG.info("testNaming");
        String seriesName = "Agents of Shield";
        List<Series> seriesList = tvdb.searchSeries(seriesName, null);
        LOG.info("Found {} matched for '{}'", seriesList.size(), seriesName);
        assertFalse("No series found for " + seriesName, seriesList.isEmpty());

        seriesName = "Agents of S.h.i.e.l.d.";
        seriesList = tvdb.searchSeries(seriesName, null);
        LOG.info("Found {} matched for '{}'", seriesList.size(), seriesName);
        assertFalse("No series found for " + seriesName, seriesList.isEmpty());
    }

    @Test
    public void testGetSeries() throws TvDbException {
        LOG.info("testGetSeries");
        Series series = tvdb.getSeries(TVDBID, LANGUAGE_ENGLISH);
        assertTrue("Wrong series name", series.getSeriesName().equals(SERIES_NAME));
        assertFalse("No genres found", series.getGenres().isEmpty());
    }

    @Test
    public void testGetAllEpisodes() throws TvDbException {
        LOG.info("testGetAllEpisodes");
        List<Episode> episodes = tvdb.getAllEpisodes(TVDBID, LANGUAGE_ENGLISH);
        assertFalse(episodes.isEmpty());
    }

    @Test
    public void testGetSeasonEpisodes() throws TvDbException {
        LOG.info("testGetSeasonEpisodes");
        List<Episode> episodes = tvdb.getSeasonEpisodes(TVDBID, 1, LANGUAGE_ENGLISH);
        assertFalse(episodes.isEmpty());
    }

    @Test
    public void testGetEpisode() throws TvDbException {
        LOG.info("testGetEpisode");
        Episode episode = tvdb.getEpisode(TVDBID, 1, 1, LANGUAGE_ENGLISH);
        assertFalse(episode.getEpisodeName().isEmpty());
    }

    @Test
    public void testGetDVDEpisode() throws TvDbException {
        LOG.info("testGetDVDEpisode");
        Episode episode = tvdb.getDVDEpisode(TVDBID, 1, 1, LANGUAGE_ENGLISH);
        assertFalse(episode.getDvdEpisodeNumber().isEmpty());
    }

    @Test
    public void testGetAbsoluteEpisode() throws TvDbException {
        LOG.info("testGetAbsoluteEpisode");
        Episode episode = tvdb.getAbsoluteEpisode(TVDBID, 1, LANGUAGE_ENGLISH);
        assertTrue("1".equals(episode.getAbsoluteNumber()));
    }

    @Test
    public void testGetSeasonYear() throws TvDbException {
        LOG.info("testGetSeasonYear");
        String year = tvdb.getSeasonYear(TVDBID, 1, LANGUAGE_ENGLISH);
        assertTrue(year.equals(SEASON_YEAR));
    }

    @Test
    public void testGetBanners() throws TvDbException {
        LOG.info("testGetBanners");
        Banners banners = tvdb.getBanners("80348");
        assertFalse("No fanart banners found", banners.getFanartList().isEmpty());
        assertFalse("No poster banners found", banners.getPosterList().isEmpty());
        assertFalse("No season banners found", banners.getSeasonList().isEmpty());
        assertFalse("No series banners found", banners.getSeriesList().isEmpty());
    }

    @Test
    public void testGetActors() throws TvDbException {
        LOG.info("testGetActors");
        List<Actor> actors = tvdb.getActors(TVDBID);
        assertFalse(actors.isEmpty());
    }

    @Test
    public void testSearchSeries() throws TvDbException {
        LOG.info("testSearchSeries");
        List<Series> seriesList = tvdb.searchSeries(SERIES_NAME, LANGUAGE_ENGLISH);
        assertFalse(seriesList.isEmpty());

        boolean found = false;
        for (Series series : seriesList) {
            if (series.getId().equals(TVDBID)) {
                found = true;
                break;
            }
        }
        assertTrue("Series not found", found);
    }

    /**
     * Test of getEpisodeById method, of class TheTVDBApi.
     *
     * @throws TvDbException re throw of exception
     */
    @Test
    public void testGetEpisodeById() throws TvDbException {
        LOG.info("getEpisodeById");
        Episode result = tvdb.getEpisodeById(EPISODE_ID, LANGUAGE_ENGLISH);
        assertEquals(TVDBID, result.getSeriesId());
        assertEquals(SEASON_ID, result.getSeasonId());
    }

    /**
     * Test of getWeeklyUpdates method, of class TheTVDBApi.
     *
     * @throws TvDbException re throw of exception
     */
    @Test
    public void testGetWeeklyUpdates() throws TvDbException {
        LOG.info("getWeeklyUpdates");
        TVDBUpdates result = tvdb.getWeeklyUpdates();
        assertFalse("No Banner results", result.getBannerUpdates().isEmpty());
        assertFalse("No Episode results", result.getEpisodeUpdates().isEmpty());
        assertFalse("No Series results", result.getSeriesUpdates().isEmpty());
    }

    /**
     * Test of getLanguages method, of class TheTVDBApi.
     *
     * @throws TvDbException re throw of exception
     */
    @Test
    public void testGetLanguages() throws TvDbException {
        LOG.info("testGetLanguages");
        List<Language> result = tvdb.getLanguages();
        assertFalse("No Languages results", result.isEmpty());
    }

    /**
     * Test for non-existent series, should return empty list
     *
     * @throws TvDbException re throw of exception
     */
    @Test
    public void test() throws TvDbException {
        String title = "fargo xsadasdasaad";
        String language = "en";
        List<Series> results = tvdb.searchSeries(title, language);

        assertFalse("Null list, should be empty", results == null);
        assertTrue("List is not empty", results.isEmpty());
    }
}
