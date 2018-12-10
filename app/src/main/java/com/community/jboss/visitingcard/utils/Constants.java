package com.community.jboss.visitingcard.utils;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Constants {
    public static FirebaseUser currentUser;
    public static FirebaseDatabase currentDatabase;
    public static FirebaseStorage storage;
    public static StorageReference storageReference;
    public static String userName;
    public static String userEmail;
    public static Uri userPhotoUrl;
    public static String userPhone;
    public static String userLinkedIn;
    public static String userGithub;
    public static String userTwitter;
}
