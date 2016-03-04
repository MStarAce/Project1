package starace.learn.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by mstarace on 3/1/16.
 */
public class DetailActivity extends AppCompatActivity {

    public static final String TAG = "DetailActivity: ";

    TextView detailTitle;
    ListView detailView;
    ArrayAdapter<String> detailAdapter;
    ArrayList<String> itemList;
    Button backButton;
    EditText editText;
    FloatingActionButton fab;
    Intent mainIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initializeViewsAndList();
        setTitle();
        setBackButton();
        setFabButton();
        setOnItemLongCLick();

        Log.d(TAG, "onCreate has run");

        itemList = getData();

        initializeAdapter();


        detailAdapter.notifyDataSetChanged();

    }

    private ArrayList<String> getData(){
        Intent fromMain = getIntent();
        if (fromMain == null){
            return null;
        }
        return fromMain.getStringArrayListExtra("detailList");
    }

    public void initializeViewsAndList(){

        detailTitle = (TextView) findViewById(R.id.detail_title);
        detailView = (ListView) findViewById(R.id.detail_list_view);
        itemList = new ArrayList<>();
        backButton = (Button) findViewById(R.id.detail_back_button);
        editText = (EditText) findViewById(R.id.detail_edit_text);
        fab = (FloatingActionButton) findViewById(R.id.detail_fab);
        mainIntent = new Intent(this, MainActivity.class);

    }

    public void initializeAdapter() {

        detailAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, itemList);
        detailView.setAdapter(detailAdapter);

    }

    public void setBackButton(){

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendNewListBack();
                finish();

            }
        });

    }

    public void setFabButton(){

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editText.getText().toString().isEmpty()) {

                    Toast.makeText(DetailActivity.this, "Please enter some text before trying to save new item.",
                            Toast.LENGTH_LONG).show();

                } else {

                    String newItem = (" - " + editText.getText().toString());
                    itemList.add(newItem);
                    detailAdapter.notifyDataSetChanged();
                    editText.getText().clear();
                    Log.d(TAG, "notifyDataSetChanged in FAB has triggered");
                }
            }
        });

    }


    public void setOnItemLongCLick() {

        detailView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                Toast.makeText(DetailActivity.this, "The Item " +
                        parent.getAdapter().getItem(position).toString() +
                        " Has Been Deleted.", Toast.LENGTH_SHORT).show();

                itemList.remove(position);
                detailAdapter.notifyDataSetChanged();

                return false;
            }
        });

    }

    public void setTitle() {

        Intent getTitle = getIntent();

        if (getTitle != null) {

            detailTitle.setText(getTitle.getStringExtra("title"));
        }
    }

    private void sendNewListBack(){
        Intent backToMain = getIntent();
        if (backToMain == null){
            return;
        }
        backToMain.putExtra("detailList", itemList);
        setResult(RESULT_OK, backToMain);
        finish();
    }

}
