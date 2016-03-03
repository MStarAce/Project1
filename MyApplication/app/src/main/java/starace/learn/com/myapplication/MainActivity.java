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
import android.widget.Toast;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    Button helpButton;
    EditText enterText;
    ListView todoView;
    LinkedList<String> todoList;
    ArrayAdapter<String> todoAdapter;
    Intent detailIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        setHelpButton();
        setFabButton();
        setOnItemClick();
        setOnItemLongClick();



    }



    public void initialize() {

        fab = (FloatingActionButton) findViewById(R.id.main_fab);
        helpButton = (Button) findViewById(R.id.main_help_button);
        enterText = (EditText) findViewById(R.id.main_edit_text);
        todoView = (ListView) findViewById(R.id.main_list_view);
        todoList = new LinkedList<>();
        todoAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,todoList);
        todoView.setAdapter(todoAdapter);
        detailIntent = new Intent(this, DetailActivity.class);
    }

    public void setHelpButton () {

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "DELETE: Click and hold on Item. \n ADD: Enter Text and fab Button \n EDIT: Tap on Item"
                        ,Toast.LENGTH_LONG).show();

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

            }
        });

    }

    public void setOnItemClick(){

        todoView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                detailIntent.putExtra("title", todoList.get(position));
                startActivity(detailIntent);

            }
        });

    }

    public void setOnItemLongClick() {

        todoView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                Toast.makeText(MainActivity.this, "The ToDo List " +
                        parent.getAdapter().getItem(position).toString() +
                        " Has Been Deleted.",Toast.LENGTH_SHORT).show();

                todoList.remove(position);
                todoAdapter.notifyDataSetChanged();



                return false;
            }
        });

    }


}
