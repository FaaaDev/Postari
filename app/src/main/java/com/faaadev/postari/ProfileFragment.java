package com.faaadev.postari;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.faaadev.postari.adapter.ItemClickListener;
import com.faaadev.postari.adapter.PetugasAdapter;
import com.faaadev.postari.http.ApiClient;
import com.faaadev.postari.http.ApiInterface;
import com.faaadev.postari.http.Preferences;
import com.faaadev.postari.model.User;
import com.faaadev.postari.screen.LoginActivity;
import com.faaadev.postari.service.UserList;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends BottomSheetDialogFragment {

    private TextView email, username, role;
    private Button logout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog d = (BottomSheetDialog) dialog;

                FrameLayout bottomSheet =  d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        init(root);

        return root;
    }

    private void init(View root){
        email = root.findViewById(R.id.email);
        username = root.findViewById(R.id.username);
        role = root.findViewById(R.id.role);
        logout = root.findViewById(R.id.logout);

        implement();
    }

    private void implement(){
        username.setText(Preferences.getUsername(getContext()));
        email.setText(Preferences.getUserId(getContext()));
        switch (Preferences.getRole(getContext())) {
            case "ortu":
                role.setText("Orang Tua");
                break;
            case "petugas_posyandu":
                role.setText("Petugas Posyandu");
                break;
            case "petugas_kesehatan":
                role.setText("Petugas Kesehatan");
                break;
            default:
                role.setText("Petugas");
                break;
        }
        logout.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Peringatan")
                    .setMessage("Anda yakin ingin keluar ?")
                    .setCancelable(true)
                    .setPositiveButton("Keluar", (dialog, which) -> {
                        dismiss();
                        Preferences.clearLoggedInUser(this.getContext());
                        startActivity(new Intent(this.getContext(), LoginActivity.class));
                        this.getActivity().finish();
                    }).setNegativeButton("Tidak", (dialog, which) -> {
                return;
            }).show();
        });
    }

}