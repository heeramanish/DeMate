package IE.walkmate.AM3Dementia.activities;

import android.content.Intent;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import IE.walkmate.AM3Dementia.Adapter.Custom_Listview_Category_Adapter;
import IE.walkmate.AM3Dementia.Model.Category;
import IE.walkmate.AM3Dementia.R;
import IE.walkmate.AM3Dementia.Sql.TableCategory;

/**
 * * Author: Team B40
 *  * Version: 01
 * category class responsible for the listview of recommended exercises
 */
public class CategoryActivity extends AppCompatActivity {
    private AppCompatActivity activity = CategoryActivity.this;
    private Toolbar myToolbar;
    private ActionBar actionBar;
    private ListView lvCategory;
    private ArrayList<Category> list;
    private TableCategory tableCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        init();
        initObjects();
        intListerers();
        setSupportActionBar(myToolbar);
        actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("My Exercises");


        Custom_Listview_Category_Adapter myAdapter =
                new Custom_Listview_Category_Adapter(CategoryActivity.this, R.layout.custom_listview_category, list);
        lvCategory.setAdapter(myAdapter);

        lvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category selected = (Category) parent.getAdapter().getItem(position);
                Intent addtaskIntent = new Intent(activity, AddTaskActivity.class);
                addtaskIntent.putExtra("taskid", selected.getId());
                startActivity(addtaskIntent);
            }
        });
    }



    private void initObjects() {

        tableCategory = new TableCategory(activity);
        list = new ArrayList<>();

        list.addAll(tableCategory.listCategories());
    }

    private void intListerers() {

    }

    private void init() {
        lvCategory = findViewById(R.id.lvCategory);
        myToolbar = findViewById(R.id.myToolbar);
    }
}
