package org.example.bumditbul_be.auth;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class GoogleOauthClient {
    private final RestClient client = RestClient.create();

    public Map<String, Object> verifyIdToken(String idToken) {
        return client.get().uri("https://oauth2.googleapis.com/tokeninfo?id_token={token}", idToken)
                .retrieve().body(Map.class);
    }
}
