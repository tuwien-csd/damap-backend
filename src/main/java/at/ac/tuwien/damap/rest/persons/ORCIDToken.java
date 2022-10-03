package at.ac.tuwien.damap.rest.persons;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ORCIDToken {
    @JsonAlias({ "access_token" })
    String accessToken;

    String refreshToken;
    int expiresIn = 0;
    Instant createdAt = Instant.now(), expiresAt;

    private void _calculateExpiresAt() {
        expiresAt = createdAt.plusSeconds(expiresIn);
    }

    public boolean hasExpired() {
        if (expiresAt == null)
            _calculateExpiresAt();

        // allow for some time between token expiring and getting a new one
        // also requests after checking have to be taken into account
        int secondsGracePeriod = 60 * 10;
        return Instant.now().minusSeconds(secondsGracePeriod).isBefore(expiresAt);
    }
}
