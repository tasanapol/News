package com.example.art_cs19.news;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;


public class OneFragment extends Fragment{

    RecyclerView recyclerAudio1;
    private Query fDatabase;
    LinearLayoutManager layoutManager;
    private View view;
    private ProgressDialog progressbar;


    public OneFragment() {
        // Required empty public constructor
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressbar = new ProgressDialog(getActivity());
        progressbar.setMessage("กำลังโหลด");
        progressbar.show();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_one, container, false);
        recyclerAudio1 = (RecyclerView) view.findViewById(R.id.recyclerAudio1);
        fDatabase = FirebaseDatabase.getInstance().getReference().child("Audio").orderByChild("id");
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerAudio1.setHasFixedSize(true);
        recyclerAudio1.setLayoutManager(layoutManager);
        return view;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onStart() {
        super.onStart();
        final FirebaseRecyclerAdapter<AudioAdapter, OneFragment.AudioViewHolder>
                firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<AudioAdapter, OneFragment.AudioViewHolder>
                (AudioAdapter.class, R.layout.cardview_audiobook, OneFragment.AudioViewHolder.class, fDatabase) {

            @Override
            protected void populateViewHolder(OneFragment.AudioViewHolder viewHolder, final AudioAdapter model, int position) {
                final String post_key = getRef(position).getKey();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setAudio(model.getAudio());
                viewHolder.setDate(model.getDate());
                viewHolder.setTime(model.getTime());
                viewHolder.setNarrator(model.getNarrator());
                viewHolder.setId(model.getId());
                viewHolder.setImage(getActivity(), model.getImage());


                //ส่งค่า putextras post_key ไปหน้า SingleActivity
                viewHolder.fView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent singleintent = new Intent(getActivity(), SingleAudioBookActivity.class);
                        singleintent.putExtra("post_key", post_key);
                        singleintent.putExtra("audio" , model.getAudio());
                        startActivity(singleintent);
                    }
                });

                progressbar.dismiss();
            }
        };
        recyclerAudio1.setAdapter(firebaseRecyclerAdapter);


    }
    public static class AudioViewHolder extends RecyclerView.ViewHolder {

        View fView;


        public AudioViewHolder(View itemView) {
            super(itemView);
            fView = itemView;
        }

        public void setTitle(String title) {
            TextView tvNameBook = (TextView) fView.findViewById(R.id.tvNameBook);
            tvNameBook.setText(title);
        }

        public void setAudio(String audio) {
            TextView tvAudio = (TextView) fView.findViewById(R.id.tvAudio);
            tvAudio.setText(audio);
        }
        public void setDate(String date) {
            TextView tvDate = (TextView) fView.findViewById(R.id.tvDate);
            tvDate.setText(date);
        }

        public void setTime(String time) {
            TextView tvTime = (TextView) fView.findViewById(R.id.tvTime);
            tvTime.setText(time);
        }


        public void setNarrator(String narrator) {
            TextView tvUploader = (TextView) fView.findViewById(R.id.tvUploader);
            tvUploader.setText(narrator);
        }
        public void setId(String id) {
            TextView tvId = (TextView) fView.findViewById(R.id.tvId);
            tvId.setText(id);
        }

        public void setImage(Context ctx, String image) {
            ImageView imgAudio = (ImageView) fView.findViewById(R.id.imgAudio);
            Picasso.with(ctx).load(image).into(imgAudio);

        }
    }
}
