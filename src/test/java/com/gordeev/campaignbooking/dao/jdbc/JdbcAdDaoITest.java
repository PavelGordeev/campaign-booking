package com.gordeev.campaignbooking.dao.jdbc;

import com.gordeev.campaignbooking.dao.AdDao;
import com.gordeev.campaignbooking.entity.Ad;
import com.gordeev.campaignbooking.entity.Campaign;
import com.gordeev.campaignbooking.entity.Platform;
import com.gordeev.campaignbooking.entity.Status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Rollback
@Transactional
public class JdbcAdDaoITest {

    private AdDao adDao;

    @Test
    public void testFindById() {
        // prepare
        Ad expectedAd = new Ad();
        expectedAd.setId(1);
        expectedAd.setName("first test ad");
        expectedAd.setStatus(Status.PLANNED);
        expectedAd.setPlatforms(Arrays.asList(Platform.WEB, Platform.IOS));
        expectedAd.setAssetUrl("asset url");

        // when
        Ad actualAd = adDao.findById(1);

        // then
        assertNotNull(actualAd);
        assertEquals(expectedAd, actualAd);

    }

    @Test
    public void testCreate() {
        Ad newAd = new Ad();
        newAd.setName("some test ad");
        newAd.setPlatforms(Arrays.asList(Platform.WEB, Platform.IOS));
        newAd.setAssetUrl("asset url");
        newAd.setStatus(Status.PLANNED);
        newAd.setCampaign(new Campaign(1));

        Ad createdAd = adDao.create(newAd);
        assertNotNull(createdAd);
        assertEquals(newAd, createdAd);

        Ad adFromDb = adDao.findById(createdAd.getId());
        assertNotNull(adFromDb);
        assertEquals(createdAd, adFromDb);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testCreateRollbackPlatformInsertException() {
        List<Platform> mockPlatforms = mock(List.class);
        when(mockPlatforms.iterator()).thenThrow(new UnsupportedOperationException());

        Ad newAd = new Ad();
        newAd.setName("some test ad");
        newAd.setPlatforms(mockPlatforms);
        newAd.setAssetUrl("asset url");
        newAd.setStatus(Status.PLANNED);
        newAd.setCampaign(new Campaign(1));

        try {
            adDao.create(newAd);
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
            // expected exception here
        }

        verify(mockPlatforms, times(1)).iterator();
        adDao.findById(3);
    }

    @Test
    public void testUpdate() {
        Ad adFromDb = adDao.findById(1);
        adFromDb.setName("updated ad");
        adFromDb.setPlatforms(Collections.singletonList(Platform.ANDROID));
        adFromDb.setAssetUrl("updated url");
        adFromDb.setStatus(Status.PAUSED);
        adFromDb.setCampaign(new Campaign(2));

        Ad updatedAd = adDao.update(adFromDb);
        assertEquals(adFromDb, updatedAd);

        Ad updatedAdFromDb = adDao.findById(1);
        assertEquals(adFromDb, updatedAdFromDb);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testRemoveById() {
        Ad adFromDb = adDao.findById(1);
        assertNotNull(adFromDb);

        adDao.removeById(1);

        adDao.findById(1);
    }

    @Autowired
    public void setAdDao(AdDao adDao) {
        this.adDao = adDao;
    }
}