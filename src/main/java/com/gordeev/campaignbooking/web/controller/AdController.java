package com.gordeev.campaignbooking.web.controller;

import com.gordeev.campaignbooking.entity.Ad;
import com.gordeev.campaignbooking.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ad")
public class AdController {

    private AdService adService;

    @Autowired
    public AdController(AdService adService) {
        this.adService = adService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Ad findById(@PathVariable int id) {
        return adService.findById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Ad create(@RequestBody Ad ad) {
        return adService.create(ad);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Ad update(@RequestBody Ad ad, @PathVariable int id) {
        ad.setId(id);
        return adService.update(ad);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        adService.removeById(id);
    }
}