package edu.illinois.cs465.grocerygo.layout.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.illinois.cs465.grocerygo.R;

public class OngoingFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ongoing_fragment, container, false);
    }
    // TODO:
    // 1. Handler function for back arrow button. Should return to the homepage
    // 2. Handler function for Join and Contact Driver button. Should navigate to the message page.
    // 3. Handler function for comments button. Should return a toast or another fragment?
    // 4. What if we want to change button after join the ride?
}
