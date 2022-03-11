package com.jorgegayer.weight_control;

public class Profile {
    private ProfileData profileInfo;

    public Profile() {
        profileInfo = new ProfileData();
    }

    boolean checkProfile() {
        return false;
    }

    ProfileData get(String email) {
        ProfileData localProfile = new ProfileData();
        return localProfile;
    }

    void set(ProfileData myProfile) {

    }
}