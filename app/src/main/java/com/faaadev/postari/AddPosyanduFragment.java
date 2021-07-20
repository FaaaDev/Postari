package com.faaadev.postari;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class AddPosyanduFragment extends BottomSheetDialogFragment {

    private CardView menu1, menu2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_posyandu, container, false);

        menu1 = root.findViewById(R.id.menu1);
        menu2 = root.findViewById(R.id.menu2);

        menu1.setOnClickListener(v -> {
            dismiss();
            AddLocationPosyanduFragment addLocationPosyanduFragment = new AddLocationPosyanduFragment();
            addLocationPosyanduFragment.show(getParentFragmentManager(), addLocationPosyanduFragment.getTag());
        });

        menu2.setOnClickListener(v -> {
            dismiss();
            AddJadwalPosyanduFragment addJadwalPosyanduFragment = new AddJadwalPosyanduFragment();
            addJadwalPosyanduFragment.show(getParentFragmentManager(), addJadwalPosyanduFragment.getTag());
        });

        return root;
    }
}