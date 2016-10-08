package io.github.nickdex.pasteit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView itemTextView;
        public TextView deviceTextView;
        public CircleImageView deviceImageView;

        public ItemViewHolder(View v) {
            super(v);
            itemTextView = (TextView) itemView.findViewById(R.id.itemTextView);
            deviceTextView = (TextView) itemView.findViewById(R.id.deviceTextView);
            deviceImageView = (CircleImageView) itemView.findViewById(R.id.deviceImageView);
        }
    }
}
