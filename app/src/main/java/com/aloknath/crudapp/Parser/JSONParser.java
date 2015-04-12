package com.aloknath.crudapp.Parser;

import com.aloknath.crudapp.Objects.ItemObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ALOKNATH on 4/12/2015.
 */
public class JSONParser {

    private static List<ItemObject> itemObjects = new ArrayList<>();

    public static List<ItemObject> parseFeed(String jsonString){

        ObjectMapper mapper = new ObjectMapper();
        try {

            itemObjects = mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, ItemObject.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return itemObjects;
    }
}
