package edu.illinois.cs465.grocerygo.layout.fragment;

import static edu.illinois.cs465.grocerygo.constant.Constant.POST_FRAGMENT_TAG;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.illinois.cs465.grocerygo.R;
import edu.illinois.cs465.grocerygo.layout.activity.ChatActivity;
import edu.illinois.cs465.grocerygo.layout.activity.PostActivity;

public class MailFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mail_fragment2, container, false);
    }

    @Nullable
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        androidx.cardview.widget.CardView cardView = view.findViewById(R.id.idCardView);
        cardView.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            startActivity(intent);
        });

    }
}
