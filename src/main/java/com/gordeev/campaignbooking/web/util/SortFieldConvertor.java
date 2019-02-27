package com.gordeev.campaignbooking.web.util;

import com.gordeev.campaignbooking.request.SortField;

import java.beans.PropertyEditorSupport;

public class SortFieldConvertor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(SortField.getByName(text));
    }
}