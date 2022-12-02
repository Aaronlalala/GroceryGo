package edu.illinois.cs465.grocerygo.layout.fragment.mail;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.illinois.cs465.grocerygo.R;
import edu.illinois.cs465.grocerygo.layout.activity.ChatActivity;
import edu.illinois.cs465.grocerygo.layout.activity.ContactInfo;

public class MailFragment extends Fragment implements MailRecyclerViewInterface{

    // An array list that store the object of user banner
    ArrayList<ContactInfo> contactsModels = new ArrayList<>();
    public RecyclerView mailRecyclerView;
    public RecyclerView.LayoutManager myLayoutManager;
    public MailRecyclerAdapter mailAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setUpContacts();
        View rootView = inflater.inflate(R.layout.mail_fragment2, container, false);

        mailRecyclerView = rootView.findViewById(R.id.contactRecyclerView);
        myLayoutManager = new LinearLayoutManager(getActivity());
        mailAdapter = new MailRecyclerAdapter(getContext(), this.contactsModels, this);
        mailRecyclerView.setAdapter(mailAdapter);
        mailRecyclerView.setLayoutManager(myLayoutManager);
        return rootView;
    }

    private void setUpContacts() {
        // Graph string data
        String[] contactNames = getResources().getStringArray(R.array.contact_name);
        // int array that stores the images of user banner
        int[] contactImages = {R.drawable.girl, R.drawable.man, R.drawable.girl, R.drawable.man, R.drawable.girl, R.drawable.man,
                R.drawable.girl, R.drawable.man, R.drawable.girl, R.drawable.man};

        for (int i = 0; i < contactNames.length; i++) {
            // Create ContactInfo object and store in the array.
            contactsModels.add(new ContactInfo(contactNames[i], contactImages[i]));
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), ChatActivity.class);
        startActivity(intent);
    }
}
