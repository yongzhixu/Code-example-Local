package io.reflectoring.featureflags.ff4j;

import io.reflectoring.featureflags.FeatureFlagService;
import org.ff4j.FF4j;
import org.springframework.stereotype.Component;

@Component("ff4j")
public class FF4JFeatureFlagService implements FeatureFlagService {

    private final FF4j ff4j;

    public FF4JFeatureFlagService(FF4j ff4j) {
        this.ff4j = ff4j;
    }

    @Override
    public Boolean isGlobalBooleanFeatureActive() {
        return ff4j.check("global-boolean-flag");
    }

    @Override
    public Boolean isPercentageRolloutActive() {
        return ff4j.check("global-percentage-rollout");
    }

    @Override
    public Integer getNumberFlag() {
        return null;
    }

    @Override
    public Boolean isUserActionTargetedFeatureActive() {
        return null;
    }

    @Override
    public Boolean isNewServiceEnabled() {
        return null;
    }

}
