package com.louisfellows.ironjuke.controller;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.louisfellows.ironjuke.model.Album;
import com.louisfellows.ironjuke.model.DB;
import com.louisfellows.ironjuke.model.Track;
import com.louisfellows.ironjuke.view.Player;
import com.louisfellows.ironjuke.view.UI;

public class ControllerTest {

    private Controller classUnderTest;
    private UI uiMock;
    private Player playerMock;
    private DB dbMock;
    private Album albumMock;
    private Track trackMock;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        uiMock = EasyMock.createMock(UI.class);
        playerMock = EasyMock.createMock(Player.class);
        dbMock = EasyMock.createMock(DB.class);
        albumMock = EasyMock.createMock(Album.class);
        trackMock = EasyMock.createMock(Track.class);

        classUnderTest = new Controller(dbMock, uiMock, playerMock);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAddRandom() {
        EasyMock.expect(dbMock.getRandomAlbum()).andReturn(albumMock);
        EasyMock.expect(albumMock.getRandomTrack()).andReturn(trackMock);
        playerMock.addTrack(trackMock);
        replayAll();
        classUnderTest.addRandom();
        verifyAll();
    }

    private void replayAll() {
        EasyMock.replay(uiMock);
        EasyMock.replay(playerMock);
        EasyMock.replay(dbMock);
        EasyMock.replay(albumMock);
        EasyMock.replay(trackMock);
    }

    private void verifyAll() {
        EasyMock.verify(uiMock);
        EasyMock.verify(playerMock);
        EasyMock.verify(dbMock);
        EasyMock.verify(albumMock);
        EasyMock.verify(trackMock);
    }
}
