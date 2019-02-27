package com.gordeev.campaignbooking.web.controller;

import com.gordeev.campaignbooking.entity.CampaignSummary;
import com.gordeev.campaignbooking.entity.Status;
import com.gordeev.campaignbooking.request.SortDirection;
import com.gordeev.campaignbooking.request.SortField;
import com.gordeev.campaignbooking.request.SummaryRequest;
import com.gordeev.campaignbooking.service.CampaignService;
import com.gordeev.campaignbooking.web.util.SortDirectionConvertor;
import com.gordeev.campaignbooking.web.util.SortFieldConvertor;
import com.gordeev.campaignbooking.web.util.StatusConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CampaignSummaryController {

    private CampaignService campaignService;

    @Autowired
    public CampaignSummaryController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping(value = "/summaries", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<CampaignSummary> findSummary(@RequestParam(required = false) Integer page,
                                             @RequestParam(required = false) String name,
                                             @RequestParam(required = false) Status status,
                                             @RequestParam(required = false) SortField sortField,
                                             @RequestParam(required = false) SortDirection sortOrder) {
        SummaryRequest request = new SummaryRequest.SummaryRequestBuilder()
                .withName(name)
                .withPage(page)
                .withStatus(status)
                .withSort(sortField, sortOrder)
                .build();

        return campaignService.findSummary(request);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(SortDirection.class, new SortDirectionConvertor());
        binder.registerCustomEditor(SortField.class, new SortFieldConvertor());
        binder.registerCustomEditor(Status.class, new StatusConvertor());
    }
}