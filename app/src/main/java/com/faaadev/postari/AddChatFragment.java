package com.faaadev.postari;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.faaadev.postari.adapter.PetugasAdapter;
import com.faaadev.postari.adapter.UserAdapter;
import com.faaadev.postari.http.ApiClient;
import com.faaadev.postari.http.ApiInterface;
import com.faaadev.postari.model.User;
import com.faaadev.postari.service.UserList;
import com.faaadev.postari.widget.LoadingDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddChatFragment extends BottomSheetDialogFragment {

    private ImageView img_loading;
    private List<User> list;
    private PetugasAdapter adapter;
    private RecyclerView rv_petugas;
    private LinearLayout loading, after;
    private ApiInterface apiInterface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_chat, container, false);

        init(root);

        return root;
    }

    private void init(View root){
        img_loading = root.findViewById(R.id.loading_img);
        rv_petugas = root.findViewById(R.id.rv_petugas);
        loading = root.findViewById(R.id.loading);
        after = root.findViewById(R.id.after);

        implement();
    }

    private void implement(){
        Glide.with(getContext()).asGif().load(R.drawable.loading).into(img_loading);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        getUserList();
    }

    private void getUserList(){
        list = new ArrayList<>();
        Call<UserList> userListCall = apiInterface.getPetugas();
        userListCall.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                if (response.body().getStatus().equals("true")){
                    list = response.body().getUser();

                    adapter = new PetugasAdapter(getContext(), list);
                    rv_petugas.setAdapter(adapter);
                    loading.setVisibility(View.GONE);
                    after.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                Toast.makeText(getContext(), "Kesalahan saat menghubungi server", Toast.LENGTH_LONG).show();
            }
        });
    }
}