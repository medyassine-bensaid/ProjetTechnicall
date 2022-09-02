package com.example.projettechnicall;

import android.app.TimePickerDialog;
import android.app.usage.NetworkStats;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicMarkableReference;


public class MainServicesFragment extends Fragment {

    private DatabaseReference FRef = FirebaseDatabase.getInstance().getReference();
    private StorageReference SRefO = FirebaseStorage.getInstance().getReference();
    private LinearLayout searchResult, blackBackground, pc, ec, mc, cc, mac;
    private ConstraintLayout calendar, serviceLayout, frameLayout;
    private CalendarView calendarView;
    private TextView timePickerText;
    private EditText searchBar;
    private int hours, minutes;
    private ImageView whiteBackground, back , Callbtn;
    private ScrollView techprofile;
    private Button rdvButton, btn ;
    private ImageButton commentBtn ;
    private EditText editTextComment ;



    public MainServicesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_services, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        timePickerText = view.findViewById(R.id.TimePicker);
        RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        serviceLayout = view.findViewById(R.id.serviceLayout);
        frameLayout = view.findViewById(R.id.frameLayout);
        searchBar = view.findViewById(R.id.searchBar);
        whiteBackground = view.findViewById(R.id.whiteBackground);
        back = view.findViewById(R.id.imageBack);

        searchResult = view.findViewById(R.id.searchResult);
        blackBackground = view.findViewById(R.id.blackBackground);

        techprofile = view.findViewById(R.id.technicianProfile);
        rdvButton = view.findViewById(R.id.rdvButton);
        btn = view.findViewById(R.id.submit);
        calendarView = view.findViewById(R.id.calendarView);
        calendar = view.findViewById(R.id.calendarLayout);
        commentBtn = view.findViewById(R.id.BtnSendcomment);
        editTextComment = view.findViewById(R.id.commentField);
        Callbtn = view.findViewById(R.id.call_img);


        timePickerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog(timePickerText);
            }
        });
        serviceLayout.setVisibility(View.VISIBLE);
        searchResult.setVisibility(View.GONE);
        techprofile.setVisibility(View.GONE);
        blackBackground.setVisibility(View.GONE);
        calendar.setVisibility(View.GONE);

        mac = view.findViewById(R.id.MaconChoice);
        searchByCategory(mac, "Maçon");

        pc = view.findViewById(R.id.PlombierChoice);
        searchByCategory(pc, "Plombier");

        mc = view.findViewById(R.id.MecanicienChoice);
        searchByCategory(mc, "Mecanicien");

        ec = view.findViewById(R.id.ElectricienChoice);
        searchByCategory(ec, "Electricien");

        cc = view.findViewById(R.id.ClimaticienChoice);
        searchByCategory(cc, "Climaticien");

        Drawable buttonDrawable = rdvButton.getBackground();
        buttonDrawable = DrawableCompat.wrap(buttonDrawable);

        DrawableCompat.setTint(buttonDrawable, Color.GREEN);
        rdvButton.setBackground(buttonDrawable);
        rdvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blackBackground.setVisibility(View.VISIBLE);
                calendar.setVisibility(View.VISIBLE);
            }
        });



        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                //System.out.println(ratingBar.getRating());
                System.out.println(v);
//                NetworkStats.Bucket currentUser;
//                User U = snapshot.child("");
//                Map<String, Object> mp = new HashMap<>();
//                mp.put(currentUser.getUid(), new User(
//                        U.rating));

       }
        });


        Intent intent =new Intent();
//        String postImage = getIntent().getExtras().getString("postImage") ;
//        Glide.with(this).load(postImage).into(imgPost);
//
//        String postTitle = getIntent().getExtras().getString("title");
//        txtPostTitle.setText(postTitle);
//
//        String userpostImage = getIntent().getExtras().getString("userPhoto");
//        Glide.with(this).load(userpostImage).into(imgUserPost);
//
//        String postDescription = getIntent().getExtras().getString("description");
//        txtPostDesc.setText(postDescription);
//
//        // setcomment user image
//
//        Glide.with(this).load(firebaseUser.getPhotoUrl()).into(imgCurrentUser);
//        // get post id
   //    PostKey = intent.getExtras().getString("PostKey");
//
//        String date = timestampToString(getIntent().getExtras().getLong("postDate"));
//        txtPostDateName.setText(date);
//
//
//        // ini Recyclerview Comment
//        iniRvComment();
//
//
//    }
//
//    private void iniRvComment() {
//
//        RvComment.setLayoutManager(new LinearLayoutManager(this));
//
//        DatabaseReference commentRef = firebaseDatabase.getReference(COMMENT_KEY).child(PostKey);
//        commentRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                listComment = new ArrayList<>();
//                for (DataSnapshot snap:dataSnapshot.getChildren()) {
//
//                    Comment comment = snap.getValue(Comment.class);
//                    listComment.add(comment) ;
//
//                }
//
//                commentAdapter = new CommentAdapter(getApplicationContext(),listComment);
//                RvComment.setAdapter(commentAdapter);
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
//
//
//    }
//

        
//
//    private String timestampToString(long time) {
//
//        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
//        calendar.setTimeInMillis(time);
//        String date = DateFormat.format("dd-MM-yyyy",calendar).toString();
//        return date;
//
//
//
//    }
        

///////////////////
        
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.equals("")) {
                    System.out.println(searchBar.getText().toString());
                    searchResult.removeAllViews();
                    String res = searchBar.getText().toString();
                    String S = !res.equals("")?SignUp.toTitle(res):"";
                    String SN = !S.equals("")?String.copyValueOf(Character.toChars(S.charAt(0)+1)):" ";
                    FRef.child("Users")
                            .orderByChild("full_name")
                            .startAt(S)
                            .endBefore(SN)
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                        System.out.println(ds.child("type").getValue(String.class));
                                        System.out.println(ds.getKey());
                                        System.out.println(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        if ((ds.child("type").getValue(String.class).equals("Technician"))
                                                &&(!ds.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))) {
                                            showSnapshot(ds);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        whiteBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBar.setVisibility(View.GONE);
                searchBar.setVisibility(View.VISIBLE);
            }
        });
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBar.setVisibility(View.GONE);
                searchBar.setVisibility(View.VISIBLE);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainServicesFragment.this)
                        .navigate(R.id.action_mainServicesFragment_to_technicianFragment);
            }
        });
        searchBar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    serviceLayout.setVisibility(View.GONE);
                    searchResult.setVisibility(View.VISIBLE);
                    techprofile.setVisibility(View.GONE);
                }
                else {
                    serviceLayout.setVisibility(View.VISIBLE);
                    searchResult.setVisibility(View.GONE);
                    techprofile.setVisibility(View.GONE);
                }
            }
        });

    }

    private void searchByCategory(LinearLayout llv, String cat) {
        llv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serviceLayout.setVisibility(View.GONE);
                searchResult.setVisibility(View.VISIBLE);
                searchResult.removeAllViews(); FRef.child("Users")
                        .orderByChild("full_name")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    FRef.child("Specialities/"+ds.getKey()).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                             if ((ds.child("type").getValue(String.class).equals("Technician"))
                                                    &&(snapshot.getValue(String.class).equals(cat))
                                                    &&(!ds.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))) {
                                                showSnapshot(ds);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
            }
        });
    }

    private void showSnapshot(DataSnapshot ds) {LinearLayout ll = new LinearLayout(getContext());
        ll.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ImageView iv = new ImageView(getContext());
        iv.setLayoutParams(new LinearLayout.LayoutParams(
                250,
                250)
        );
        final Uri[] uripic = {null};
        try {
            System.out.println("ProfilePics/"+ds.getKey()+".jpg");
            SRefO.child("ProfilePics/"+ds.getKey()+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    uripic[0] = uri;
                    Picasso.with(getContext()).load(uri).transform(new CircleTransform()).into(iv);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Picasso.with(getContext()).load(R.drawable.img).into(iv);
                }
            });
        } catch (Exception e) {
            Picasso.with(getContext()).load(R.drawable.img).into(iv);
        }

        ll.addView(iv);
        LinearLayout llv = new LinearLayout(getContext());
        llv.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        llv.setOrientation(LinearLayout.VERTICAL);
        TextView nom = new TextView(getContext());
        ViewGroup.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 100
        );
        nom.setTextColor(getResources().getColor(R.color.black));
        nom.setLayoutParams(lp);
        nom.setTypeface(ResourcesCompat.getFont(getContext(), R.font.pt_sans_bold_italic));
        nom.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
        nom.setText(ds.child("full_name").getValue().toString());
        llv.addView(nom);
        TextView type = new TextView(getContext());
        FRef.child("Specialities/"+ds.getKey()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                type.setText(dataSnapshot.getValue(String.class));
            }
        });
        type.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        type.setTypeface(ResourcesCompat.getFont(getContext(), R.font.pt_sans_bold_italic));
        llv.addView(type);
        ImageView stars = new ImageView(getContext());
        //stars.setImageResource(R.drawable.five_stars);
        Picasso.with(getContext()).load(R.drawable.five_stars).into(stars);
        stars.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        llv.addView(stars);
        ll.addView(llv);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view = getView();
                techprofile.setVisibility(View.VISIBLE);
                searchResult.setVisibility(View.GONE);
                TextView nomProfile = view.findViewById(R.id.nom);
                TextView typeProfile = view.findViewById(R.id.type);
                TextView eva = view.findViewById(R.id.textEvaluation);
                ImageView img = view.findViewById(R.id.profilePicture);

                nomProfile.setText(nom.getText().toString());
                typeProfile.setText(type.getText().toString());
                eva.setText("Evaluer "+nom.getText().toString()+"!");
                if (uripic[0]==null){
                    Picasso.with(getContext())
                            .load(R.drawable.img)
                            .into(img);
                }
                else {
                    Picasso.with(getContext())
                            .load(uripic[0])
                            .transform(new CircleTransform())
                            .into(img);
                }
                String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

                cancelButton(rdvButton, id, ds.getKey());
                greyButton(rdvButton, id, ds.getKey());

                //
                commentBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference commentReference = firebaseDatabase.getReference().child("Comment").push();
                        String comment_content = editTextComment.getText().toString();
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        String uid = firebaseUser.getUid();
                        String uname = ds.getKey();
//                String uimg = firebaseUser.getPhotoUrl().toString();
                        Comment comment = new Comment(comment_content,uid/*,uimg*/,uname);

                        commentReference.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "commentaire ajouté", Toast.LENGTH_SHORT).show();
                                editTextComment.setText("");
                                commentBtn.setVisibility(View.VISIBLE);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "une erreur se produit", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                //
                Callbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel: "+ ds.child("phone").getValue(String.class)));
                        System.out.println(ds.child("phone").getValue(String.class));
                        startActivity(intent);
                    }
                });
                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Random rand = new Random();
                                System.out.println(RandomID());
                                DatabaseReference DataRefR = FirebaseDatabase.getInstance().getReference("Rendezvous/"+RandomID());
                                Map<String, Object> mp = new HashMap<>();
                                mp.put("Sender", id);
                                mp.put("Technician",ds.getKey());
                                mp.put("Date", LocalDate.of(i, i1, i2).toString());
                                mp.put("State", 0);
                                mp.put("Time",timePickerText.getText().toString() );
                                DataRefR.updateChildren(mp).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        calendar.setVisibility(View.GONE);
                                        blackBackground.setVisibility(View.GONE);
                                        techprofile.setVisibility(View.VISIBLE);
                                        cancelButton(rdvButton, id, ds.getKey());
                                        greyButton(rdvButton, id, ds.getKey());
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
        searchResult.addView(ll);
    }

    private void showTimeDialog(TextView timePickerEdit) {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                hours = i;
                minutes = i1;
                calendar.set(Calendar.HOUR_OF_DAY, i);
                calendar.set(Calendar.MINUTE, i1);
                timePickerEdit.setText(TimeFormatting(hours, minutes));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

        timePickerDialog.updateTime(hours, minutes);
        timePickerDialog.show();
    }
    private void greyButton(Button rdvButton, String sender, String receiver) {


        FirebaseDatabase.getInstance()
                .getReference("Rendezvous")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            if ((ds.child("Sender").getValue(String.class).equals(sender))
                                    &&(ds.child("Technician").getValue(String.class).equals(receiver))
                                        &&(ds.child("State").getValue(Integer.class)==1)) {
                                Drawable buttonDrawable = rdvButton.getBackground();
                                buttonDrawable = DrawableCompat.wrap(buttonDrawable);

                                DrawableCompat.setTint(buttonDrawable, Color.GRAY);
                                rdvButton.setBackground(buttonDrawable);
                                rdvButton.setText("Rendez-vous accepté");
                                rdvButton.setOnClickListener(null);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    private void cancelButton(Button rdvButton, String sender, String receiver) {


        FirebaseDatabase.getInstance()
                .getReference("Rendezvous")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            if ((ds.child("Sender").getValue(String.class).equals(sender))
                                    &&(ds.child("Technician").getValue(String.class).equals(receiver))
                                        &&(ds.child("State").getValue(Integer.class)==0)){
                                Drawable buttonDrawable = rdvButton.getBackground();
                                buttonDrawable = DrawableCompat.wrap(buttonDrawable);

                                DrawableCompat.setTint(buttonDrawable, Color.RED);
                                rdvButton.setBackground(buttonDrawable);
                                rdvButton.setText("Annuler le rendez-vous");
                                rdvButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        blackBackground = getView().findViewById(R.id.blackBackground);
                                        calendar = getView().findViewById(R.id.calendarLayout);
                                        ds.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                greenButton(rdvButton, blackBackground);
                                            }
                                        });
                                    }
                                });
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private String RandomID() {
        String S = "";
        Random rand = new Random();
        for (int i=0;i<27; i++) {
            int X = 0+ rand.nextInt(3);
            char[] c;
            if (X == 0){
                c = Character.toChars('a'+rand.nextInt(26));
            }
            else if (X==1) {
                c = Character.toChars('A'+rand.nextInt(26));
            }
            else {
                c = Character.toChars('0'+rand.nextInt(10));
            }
            S += String.copyValueOf(c);
        }
        return S;
    }

    private void greenButton (Button rdvButton,LinearLayout bb) {
        Drawable buttonDrawable = rdvButton.getBackground();
        buttonDrawable = DrawableCompat.wrap(buttonDrawable);

        DrawableCompat.setTint(buttonDrawable, Color.GREEN);
        rdvButton.setBackground(buttonDrawable);
        rdvButton.setText("Prendre un rendez-vous");
        rdvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bb.setVisibility(View.VISIBLE);
                calendar.setVisibility(View.VISIBLE);
            }
        });

    }

    String TimeFormatting (int hours, int minutes) {
        String hh,mm;
        hh = hours+"";
        mm = minutes+"";
        return ((hh.length()==2)&&(mm.length()==2))?hh+":"+mm:
                ((hh.length()<2)&&(mm.length()==2))?"0"+hh+":"+mm:
                ((hh.length()==2)&&(mm.length()<2))?hh+":0"+mm:
                        "0"+hh+":0"+mm;
    }
}