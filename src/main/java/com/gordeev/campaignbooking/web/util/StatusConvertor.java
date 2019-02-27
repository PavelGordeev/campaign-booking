package com.gordeev.campaignbooking.web.util;

import com.gordeev.campaignbooking.entity.Status;

import java.beans.PropertyEditorSupport;

public class StatusConvertor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(Status.getById(Integer.valueOf(text)));
    }
}