package com.faaadev.postari.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.faaadev.postari.R;
import com.faaadev.postari.http.Preferences;

public class HomeFragment extends Fragment {

    TextView username;
    Preferences preferences;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        _init(root);

        return root;
    }

    private void _init(View root){
        username = root.findViewById(R.id.tv_username);

        _implement();
    }

    private void _implement(){
        username.setText(Preferences.getUsername(getContext()));
    }
}