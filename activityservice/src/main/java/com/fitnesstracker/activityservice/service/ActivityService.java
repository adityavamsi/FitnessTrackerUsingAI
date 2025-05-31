package com.fitnesstracker.activityservice.service;

import com.fitnesstracker.activityservice.dto.ActivityRequest;
import com.fitnesstracker.activityservice.dto.ActivityResponse;
import com.fitnesstracker.activityservice.model.Activity;
import com.fitnesstracker.activityservice.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityResponse trackActivity(ActivityRequest activityRequest) {
        Activity activity = Activity.builder()
                .userId(activityRequest.getUserId())
                .type(activityRequest.getType())
                .duration(activityRequest.getDuration())
                .caloriesBurned(activityRequest.getCaloriesBurned())
                .startTime(activityRequest.getStartTime())
                .additionalMetrics(activityRequest.getAdditionalMetrics())
                .build();
        Activity savedActivity = activityRepository.save(activity);
        return mapToActivityResponse(savedActivity);



    }

    private ActivityResponse mapToActivityResponse(Activity activity) {
        ActivityResponse activityResponse = new ActivityResponse();
        activityResponse.setId(activity.getId());
        activityResponse.setUserId(activity.getUserId());
        activityResponse.setType(activity.getType());
        activityResponse.setDuration(activity.getDuration());
        activityResponse.setCaloriesBurned(activity.getCaloriesBurned());
        activityResponse.setStartTime(activity.getStartTime());
        activityResponse.setAdditionalMetrics(activity.getAdditionalMetrics());
        activityResponse.setCreatedAt(activity.getCreatedAt());
        activityResponse.setUpdatedAt(activity.getUpdatedAt());
        return activityResponse;
    }

    public List<ActivityResponse> getUserActivities(String userId) {
        List<Activity> activities  = activityRepository.findByUserId(userId);
        return activities.stream().map(this::mapToActivityResponse).toList();
    }

    public ActivityResponse getActivityById(String activityId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new RuntimeException("Activity with id " + activityId + " is not found"));
        return mapToActivityResponse(activity);
    }
}
