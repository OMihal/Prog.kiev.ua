package ua.kiev.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;


public class ServiceResponse {
    private Collection<String> answer;

    public ServiceResponse(Collection<String> answer) {
        this.answer = answer;
    }
    public ServiceResponse(String answer) {
        this.answer = new ArrayList<>();
        this.answer.add(answer);
    }

    public String toJSON() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    public static ServiceResponse fromJSON(String s) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(s, ServiceResponse.class);
    }

    public Collection<String> get() {
        return answer;
    }
}
