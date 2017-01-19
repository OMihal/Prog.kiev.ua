package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonMessages {
    private final List<com.company.Message> list;

    public JsonMessages(List<com.company.Message> sourceList, int fromIndex) {
        this.list = new ArrayList<>();
        for (int i = fromIndex; i < sourceList.size(); i++)
            list.add(sourceList.get(i));
    }

    public List<com.company.Message> getList() {
        return Collections.unmodifiableList(list);
    }
}
