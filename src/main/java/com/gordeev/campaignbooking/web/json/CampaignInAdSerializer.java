package com.gordeev.campaignbooking.web.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.gordeev.campaignbooking.entity.Campaign;

import java.io.IOException;

public class CampaignInAdSerializer extends JsonSerializer<Campaign> {
    @Override
    public void serialize(Campaign campaign, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(campaign.getId());
    }
}