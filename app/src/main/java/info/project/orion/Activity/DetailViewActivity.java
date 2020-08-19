package info.project.orion.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import info.project.orion.R;

public class DetailViewActivity extends AppCompatActivity {

    ImageView mImageView;
    TextView mTextView_title, mTextView_date;

    String imageUrl, title, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        mImageView = findViewById(R.id.imageView);
        mTextView_title = findViewById(R.id.textView_title);
        mTextView_date = findViewById(R.id.textView_date);

        Intent intent = getIntent();

        imageUrl = intent.getStringExtra("URL");
        title = intent.getStringExtra("TITLE");
        date = intent.getStringExtra("DATE");

        mTextView_title.setText(title);
        mTextView_date.setText(date);
        Glide.with(this).load(imageUrl)
                .into(mImageView);
    }
}