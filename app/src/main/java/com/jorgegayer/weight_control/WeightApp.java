package com.jorgegayer.weight_control;

public class WeightApp {
    private Profile myProfile;
    private Weight myWeight;
    public WeightApp() {

    }
    ProfileData get(String email) {
        return myProfile.get(email);
    }

    boolean checkProfile() {
        return myProfile.checkProfile();
    }

    void updateProfile(ProfileData profileData) {
        myProfile.set(profileData);
    }


}
