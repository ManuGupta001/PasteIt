package io.github.nickdex.pasteit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.api.model.StringList;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    public static final String CLIP_ITEMS_CHILD = "clip_items";
    //TODO Add Google Sign In
    public static final String ANONYMOUS = "anonymous";
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_INVITE = 1;
    //TODO replace local dummy with Firebase Dummy Data
    static ArrayList<ClipItem> clipItems = new ArrayList<>();

    static {
        for (int i = 1; i <= 5; i++) {
            clipItems.add(new ClipItem("Blah" + i, "Nexus", "phone"));
        }
    }

    GoogleApiClient googleApiClient;
    private String username;
    // View Variables
    private ImageButton sendButton;
    private RecyclerView clipItemRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;
    private EditText itemEditText;
    // Firebase Instance Variable
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = ANONYMOUS;

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            username = firebaseUser.getDisplayName();
        }

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        // Assign fields
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        clipItemRecyclerView = (RecyclerView) findViewById(R.id.itemRecycleView);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        clipItemRecyclerView.setLayoutManager(linearLayoutManager);

        itemEditText = (EditText) findViewById(R.id.itemEditText);
        sendButton = (ImageButton) findViewById(R.id.sendButton);

        //DummyContent
        RecyclerView.Adapter<ItemViewHolder> adapter = new RecyclerView.Adapter<ItemViewHolder>() {
            @Override
            public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ItemViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.item_clip, parent, false));
            }

            @Override
            public void onBindViewHolder(ItemViewHolder holder, int position) {
                ClipItem item = clipItems.get(position);
                holder.itemTextView.setText(item.getText());
                holder.deviceTextView.setText(item.getDeviceName());
                holder.deviceImageView.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.smartphone));
            }

            @Override
            public int getItemCount() {
                return clipItems.size();
            }
        };

        clipItemRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed: " + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemTextView;
        TextView deviceTextView;
        CircleImageView deviceImageView;

        ItemViewHolder(View v) {
            super(v);
            itemTextView = (TextView) itemView.findViewById(R.id.itemTextView);
            deviceTextView = (TextView) itemView.findViewById(R.id.deviceTextView);
            deviceImageView = (CircleImageView) itemView.findViewById(R.id.deviceImageView);
        }
    }
}
