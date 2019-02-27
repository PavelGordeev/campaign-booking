package com.gordeev.campaignbooking.web.util;

import com.gordeev.campaignbooking.request.SortDirection;

import java.beans.PropertyEditorSupport;

public class SortDirectionConvertor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(SortDirection.getByName(text));
    }
}