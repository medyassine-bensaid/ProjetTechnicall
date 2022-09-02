package com.example.projettechnicall;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class RendezVousFragment extends Fragment {


    private FirebaseDatabase DataI = FirebaseDatabase.getInstance();
    private int i = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rendez_vous, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout rdvs = view.findViewById(R.id.rdv);

        DataI.getReference("Rendezvous").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Button btn1 = view.findViewById(R.id.sentButton);
                Button btn2 = view.findViewById(R.id.receivedButton);

                try {
                    if (i==0) {
                        rendezvs(rdvs, snapshot, "Sender", "Technician");
                        i=1;
                    }
                }
                catch (Exception e) {

                }
                DataI.getReference("Users/"+FirebaseAuth.getInstance()
                        .getCurrentUser()
                        .getUid()+"/type").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isComplete()){
                            if (task.getResult().getValue(String.class).equals("Technician")) {
                                btn1.setVisibility(View.VISIBLE);
                                btn2.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rdvs.removeAllViews();
                        rendezvs(rdvs, snapshot, "Sender", "Technician");
                    }
                });

                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rdvs.removeAllViews();
                        rendezvs(rdvs, snapshot, "Technician", "Sender");
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void rendezvs(LinearLayout rdvs, DataSnapshot snapshot, String sender, String receiver) {
        TextView empty = getView().findViewById(R.id.emptyRdv);
        LinearLayout enteteRdv = getView().findViewById(R.id.enteteRdv);

        for (DataSnapshot ds : snapshot.getChildren()) {
            if (ds.child(sender).getValue(String.class).equals(FirebaseAuth.getInstance().getUid())) {
                enteteRdv.setVisibility(View.VISIBLE);
                empty.setVisibility(View.GONE);
                FirebaseDatabase.getInstance().getReference("Users")
                        .child(ds.child(receiver).getValue(String.class))
                        .child("full_name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        DataSnapshot snapshot = task.getResult();
                        LinearLayout ll = new LinearLayout(getContext());
                        ll.setOrientation(LinearLayout.HORIZONTAL);
                        ll.setLayoutParams(new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                        ));
                        TextView nom = new TextView(getContext());
                        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 123, getResources().getDisplayMetrics());
                        nom.setLayoutParams(new LinearLayout.LayoutParams((int) px,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                        px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 115, getResources().getDisplayMetrics());
                        nom.setTextColor(getResources().getColor(R.color.black));
                        nom.setText(snapshot.getValue(String.class));
                        ll.addView(nom);
                        TextView date = new TextView(getContext());
                        date.setLayoutParams(new LinearLayout.LayoutParams((int) px,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                        date.setTextColor(getResources().getColor(R.color.black));
                        date.setText(ds.child("Date").getValue(String.class)+
                                " "+ds.child("Time").getValue(String.class));
                        ll.addView(date);
                        TextView state = new TextView(getContext());
                        if (sender.equals("Technician")) {
                            px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 65, getResources().getDisplayMetrics());

                            state.setLayoutParams(new LinearLayout.LayoutParams((int)px,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                            state.setTextColor(getResources().getColor(R.color.black));
                            ll.addView(state);
                            ViewGroup.LayoutParams lp;
                            if (ds.child("State").getValue(Integer.class)==0) {
                                state.setText("En attente");
                                ImageView iv = new ImageView(getContext());
                                Picasso.with(getContext()).load(R.drawable.check).into(iv);

                                px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18, getResources().getDisplayMetrics());

                                lp = new LinearLayout.LayoutParams((int)px, ViewGroup.LayoutParams.MATCH_PARENT);
                                iv.setLayoutParams(lp);
                                iv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        DatabaseReference DataRefR = FirebaseDatabase.getInstance().getReference("Rendezvous/"+ds.getKey());
                                        Map<String, Object> mp = new HashMap<>();
                                        mp.put("State", 1);
                                        DataRefR.updateChildren(mp).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                state.setText("Accepté");
                                                state.setLayoutParams(new LinearLayout.LayoutParams(
                                                        (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 85, getResources().getDisplayMetrics()),
                                                        ViewGroup.LayoutParams.WRAP_CONTENT
                                                ));
                                                ll.removeView(iv);
                                            }
                                        });
                                    }
                                });
                                ll.addView(iv);
                            }
                            else {
                                px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 85, getResources().getDisplayMetrics());
                                lp = new LinearLayout.LayoutParams((int)px,
                                        ViewGroup.LayoutParams.WRAP_CONTENT);
                                state.setLayoutParams(lp);
                                state.setTextColor(getResources().getColor(R.color.black));

                                state.setText("Accepté");
                            }
                            ImageView ivd = new ImageView(getContext());

                            Picasso.with(getContext()).load(android.R.drawable.ic_delete).into(ivd);
                            px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 22, getResources().getDisplayMetrics());

                            lp = new LinearLayout.LayoutParams((int)px,
                                    ViewGroup.LayoutParams.MATCH_PARENT);
                            ivd.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ds.getRef().removeValue();
                                    rdvs.removeView(ll);
                                }
                            });
                            ivd.setLayoutParams(lp);
                            ll.addView(ivd);
                        }
                        else {
                            px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 85, getResources().getDisplayMetrics());

                            state.setLayoutParams(new LinearLayout.LayoutParams((int)px,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                            state.setTextColor(getResources().getColor(R.color.black));
                            ll.addView(state);
                            if (ds.child("State").getValue(Integer.class)==0) {
                                state.setText("En attente");
                                ImageView iv = new ImageView(getContext());
                                Picasso.with(getContext()).load(android.R.drawable.ic_delete).into(iv);
                                iv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        ds.getRef().removeValue();
                                        rdvs.removeView(ll);
                                    }
                                });
                                px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 22, getResources().getDisplayMetrics());

                                ViewGroup.LayoutParams lp = new LinearLayout.LayoutParams((int)px, ViewGroup.LayoutParams.MATCH_PARENT);
                                iv.setLayoutParams(lp);
                                ll.addView(iv);
                            }
                            else {
                                state.setText("Accepté");
                            }
                        }
                        rdvs.addView(ll);
                    }
                });
            }
            else {
                enteteRdv.setVisibility(View.GONE);
                empty.setVisibility(View.VISIBLE);
            }
        }
    }
}