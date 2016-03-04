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
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity: ";
    private static final int MAIN_REQUEST_CODE = 27;
    public static final String DATA_KEY = "myDataKey";
    public static final String DATA_INDEX_KEY = "myDataIndexKey";
    public static final int ERROR_INDEX = -2;

    FloatingActionButton fab;
    Button helpButton;
    EditText enterText;
    ListView todoView;
    ArrayList<String> todoList;
    ArrayList<ArrayList<String>> detailArrayArray;
    ArrayList<String> detailList;
    ArrayAdapter<String> todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        detailList = new ArrayList<>();
        todoList = new ArrayList<>();
        detailArrayArray = new ArrayList<>();

        initializeViewsAndList();
        setHelpButton();
        setFabButton();
        setOnItemClick();
        setOnItemLongClick();

        Log.d(TAG, "onCreate method has been called in Main");


        initializeAdapter();

        todoAdapter.notifyDataSetChanged();

    }



    public void initializeViewsAndList() {

        fab = (FloatingActionButton) findViewById(R.id.main_fab);
        helpButton = (Button) findViewById(R.id.main_help_button);
        enterText = (EditText) findViewById(R.id.main_edit_text);
        todoView = (ListView) findViewById(R.id.main_list_view);

    }

    public void initializeAdapter() {

        todoAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,todoList);
        todoView.setAdapter(todoAdapter);
    }



    public void setHelpButton () {

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "DELETE: Click and hold on Item. \n ADD: Enter Text and fab Button \n EDIT: Tap on Item"
                        , Toast.LENGTH_LONG).show();

            }
        });

    }

    public void setFabButton() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                todoList.add(enterText.getText().toString());
                todoAdapter.notifyDataSetChanged();
                enterText.getText().clear();

                Log.d(TAG, "notifyDataSetChanged in FAB has triggered");

            }
        });

    }

    public void setOnItemClick(){

        todoView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent mainIntent = new Intent(MainActivity.this, DetailActivity.class);

                detailArrayArray.add(detailList);

                // send title and detail array array and position to extra
                mainIntent.putExtra("position", position);
                mainIntent.putExtra("detailList", detailArrayArray.get(position));
                mainIntent.putExtra("title", todoList.get(position));

                startActivityForResult(mainIntent, MAIN_REQUEST_CODE);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MAIN_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                if (data != null) {

                    int index = data.getIntExtra("position", ERROR_INDEX);

                        if (index != ERROR_INDEX) {
                            detailArrayArray.set(index, data.getStringArrayListExtra(("detailList")));
                        }

                }


            }


        }

    }

    public void setOnItemLongClick() {

        todoView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                Toast.makeText(MainActivity.this, "The ToDo List " +
                        parent.getAdapter().getItem(position).toString() +
                        " Has Been Deleted.", Toast.LENGTH_SHORT).show();

                todoList.remove(position);
                todoAdapter.notifyDataSetChanged();


                return false;
            }
        });


    }


}
