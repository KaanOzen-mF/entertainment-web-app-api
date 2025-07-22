package com.kaanozen.entertainment_app_api.dto;

import java.util.Map;


public record ThumbnailDTO(Map<String, String> trending, Map<String, String> regular) {
}