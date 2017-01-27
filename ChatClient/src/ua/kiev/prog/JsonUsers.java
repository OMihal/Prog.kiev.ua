package ua.kiev.prog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonUsers {
    private final List<String> list;

    public JsonUsers(List<String> sourceList) {
        this.list = new ArrayList<>();
        list.addAll(sourceList);
    }

    public List<String> getList() {
        return Collections.unmodifiableList(list);
    }
}
