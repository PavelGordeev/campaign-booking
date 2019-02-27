package com.gordeev.campaignbooking.dao.jdbc;

import com.gordeev.campaignbooking.dao.CampaignDao;
import com.gordeev.campaignbooking.entity.Ad;
import com.gordeev.campaignbooking.entity.Campaign;
import com.gordeev.campaignbooking.entity.Platform;
import com.gordeev.campaignbooking.entity.Status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback
@Transactional
public class JdbcCampaignDaoITest {

    private CampaignDao campaignDao;

    @Test
    public void testFindById() {
        Campaign actualCampaign = campaignDao.findById(1);
        System.out.println(actualCampaign);

        assertEquals(1, actualCampaign.getId());
        assertEquals("First test Campaign", actualCampaign.getName());
        assertEquals(Status.PLANNED, actualCampaign.getStatus());
        assertEquals(LocalDateTime.of(2019, Month.JUNE, 18, 0, 0), actualCampaign.getStartDate());
        assertEquals(LocalDateTime.of(2019, Month.JULY, 18, 0, 0), actualCampaign.getEndDate());

        List<Ad> actualAds = actualCampaign.getAds();
        assertNotNull(actualAds);
        assertEquals(2, actualAds.size());

        Ad firstAd = new Ad();
        firstAd.setId(1);
        firstAd.setName("first test ad");
        firstAd.setStatus(Status.PLANNED);
        firstAd.setPlatforms(Arrays.asList(Platform.WEB, Platform.IOS));
        firstAd.setAssetUrl("asset url");
        firstAd.setCampaign(new Campaign(1));

        Ad secondAd = new Ad();
        secondAd.setId(2);
        secondAd.setName("second test ad");
        secondAd.setStatus(Status.ACTIVE);
        secondAd.setPlatforms(Arrays.asList(Platform.ANDROID, Platform.IOS));
        secondAd.setAssetUrl("asset2 url");
        secondAd.setCampaign(new Campaign(1));

        assertTrue(actualAds.contains(firstAd));
        assertTrue(actualAds.contains(secondAd));

    }

    @Autowired
    public void setCampaignDao(CampaignDao campaignDao) {
        this.campaignDao = campaignDao;
    }
}