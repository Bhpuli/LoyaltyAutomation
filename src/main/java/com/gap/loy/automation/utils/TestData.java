package com.gap.loy.automation.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class TestData {

    JSONObject testDataObj;

    String path = "src/test/resources/test_data.json";

    public static List<String> getPromoCodes() throws IOException {
        String data = new String(Files.readAllBytes(Paths.get("src/test/resources/data/codes.txt")));
        return Arrays.stream(data.split("\n")).map(StringUtils::trimToEmpty).collect(Collectors.toList());
    }

    public TestData() {
        testDataObj = getJsonFile(path);
    }

    private JSONObject getJsonFile(String filePath) {
        try {
            String data = new String(Files.readAllBytes(Paths.get(filePath)));
            return (JSONObject) new JSONParser().parse(data);
        } catch (Exception e) {
            log.error("Failed to read test data file: {}", filePath);
            throw  new RuntimeException(e);
        }
    }

    public JSONObject getUser(String userProfile) {
        try {
            JSONObject users = (JSONObject) testDataObj.getOrDefault("users", new JSONObject());
            return (JSONObject) users.getOrDefault(userProfile, new JSONObject());
        } catch (Exception ex) {
            log.error("Failed to get user test data of {} user profile", userProfile);
            throw new RuntimeException(ex);
        }
    }

}
