package edu.illinois.cs465.grocerygo.constant;

import java.util.List;

import edu.illinois.cs465.grocerygo.layout.fragment.post.PostData;

public class Constant {
    public static PostData myPost;
    public static List<PostData> postList;

    public static final int BOTTOM_HOMEPAGE_BUTTON = 0;
    public static final int BOTTOM_ONGOING_BUTTON = 1;
    public static final int BOTTOM_MAIL_BUTTON = 2;

    public static final String POST_FRAGMENT_TAG = "homepage_fragment_tag";
    public static final String ONGOING_FRAGMENT_TAG = "ongoing_fragment_tag";
    public static final String MAIL_FRAGMENT_TAG = "mail_fragment_tag";
    public static final String UNRATED = "Unrated";
    public static final int NO_STARS = -1;
}
