package starace.learn.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

    TextView detailTitle;
    ListView detailView;
    ArrayAdapter<Item> detailAdapter;
    ArrayList<Item> itemlList;
    Button backButton;
    EditText editText;
    FloatingActionButton fab;
    Intent mainIntent;
    Bundle saveData = new Bundle();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initialize();
        setTitle();
        setBackButton();
        setFabButton();
        setOnItemClick();
        setOnItemLongCLick();

    }



    public void initialize(){

        detailTitle = (TextView) findViewById(R.id.detail_title);
        detailView = (ListView) findViewById(R.id.detail_list_view);
        itemlList = new ArrayList<>();
        detailAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, itemlList);
        backButton = (Button) findViewById(R.id.detail_back_button);
        editText = (EditText) findViewById(R.id.detail_edit_text);
        fab = (FloatingActionButton) findViewById(R.id.detail_fab);
        detailView.setAdapter(detailAdapter);

        mainIntent = new Intent(this,MainActivity.class);




    }

    public void setBackButton(){

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(mainIntent);

            }
        });

    }


    public void setFabButton(){

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Item newItem = new Item(" - " + editText.getText().toString());

                itemlList.add(newItem);
                newItem.changeColor();
                detailAdapter.notifyDataSetChanged();
                editText.getText().clear();

            }
        });

    }

    public void setOnItemClick(){

        detailView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView curView = (TextView) view;
                itemlList.get(position).changeColor();
                curView.setBackgroundResource(itemlList.get(position).getBackgroundColorResource());

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

                itemlList.remove(position);
                detailAdapter.notifyDataSetChanged();


                return false;
            }
        });

    }

    public void setTitle() {


        detailTitle.setText(getIntent().getStringExtra("title"));

    }

    @Override
    protected void onStop() {
        super.onStop();


        saveData.putParcelableArrayList("savedData", itemlList);
    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        if (saveData.size() != 0) {
//
//            itemlList = saveData.getParcelableArrayList("savedData");
//            detailAdapter.notifyDataSetChanged();
//
//        }
//
//    }




}
