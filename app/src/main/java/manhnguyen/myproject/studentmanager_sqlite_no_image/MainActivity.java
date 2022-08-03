package manhnguyen.myproject.studentmanager_sqlite_no_image;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DataBase dataBase;
    ListView listViewHomework;
    ArrayList<Homework> listHomework;
    HomeWorkAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Mapping();
        CreateData();
        GetData();

    }


    // Create data
    private void CreateData() {
        dataBase = new DataBase(this, "my_homework", null, 1);
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS homework(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(100))";
        dataBase.QueryData(sqlCreateTable);
//        String sqlInsertIntoTB = "INSERT INTO homework VALUES(null,'exercise 2')";
//        dataBase.QueryData(sqlInsertIntoTB);
    }

    //Mapping
    private void Mapping() {
        listViewHomework = (ListView) findViewById(R.id.listViewHomework);
        listHomework = new ArrayList<>();
        adapter = new HomeWorkAdapter(this, R.layout.homework_row, listHomework);
        listViewHomework.setAdapter(adapter);

    }

    private void GetData() {

        String sqlGetData = "SELECT * FROM homework";
        listHomework.clear();
        Cursor data = dataBase.GetData(sqlGetData);
        while (data.moveToNext()) {
            String name = data.getString(1);
            int id = data.getInt(0);
            listHomework.add(new Homework(id, name));
        }
        adapter.notifyDataSetChanged();

    }

    // make menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // select item
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAddNewHomework) {
            DialogCreateNewHomework();
        }
        return super.onOptionsItemSelected(item);
    }

    // delete
    public void DeleteHomeWork(int id, String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Do you want to remove homework: '" + name + "' ?");
        builder.setTitle("Delete My Homework!");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String sqlDelete = "DELETE FROM homework WHERE id='" + id + "'";
                dataBase.QueryData(sqlDelete);
                GetData();
                Toast.makeText(MainActivity.this, "Delete successful", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "Delete was not successful!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }


    // DialogCreate
    public void DialogCreateNewHomework() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_add_new_homework);
        dialog.setCanceledOnTouchOutside(false);
        EditText nameHomework;
        Button btnAdd, btnCancelAdding;
        nameHomework = (EditText) dialog.findViewById(R.id.editTextAddNewHomework);
        btnAdd = (Button) dialog.findViewById(R.id.buttonAddNewHomework);
        btnCancelAdding = (Button) dialog.findViewById(R.id.buttonCancelAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newHomework = nameHomework.getText().toString().trim();
                if (newHomework.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please , Enter the homework's name!", Toast.LENGTH_SHORT).show();
                } else {
                    String sqlCreateNewHomework = "INSERT INTO homework VALUES(null,'" + newHomework + "')";
                    dataBase.QueryData(sqlCreateNewHomework);
                    Toast.makeText(MainActivity.this, "Adding new homework successful!", Toast.LENGTH_SHORT).show();
                    GetData();
                    dialog.dismiss();
                }
            }
        });
        btnCancelAdding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Failed to add new homework! huhu", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();

    }

    // dialog update homework
    public void DialogUpdateHomework(String name, int id) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_update_homework);
        dialog.setCanceledOnTouchOutside(false);
        EditText nameHomework;
        Button btnEdit, btnCancelEditing;
        nameHomework = (EditText) dialog.findViewById(R.id.editTextNameEdit);
        btnEdit = (Button) dialog.findViewById(R.id.buttonEdit);
        btnCancelEditing = (Button) dialog.findViewById(R.id.buttonCancelEdit);
        nameHomework.setText(name);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newHomeworkName = nameHomework.getText().toString().trim();
                if (newHomeworkName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please ,Enter homework's name to edit", Toast.LENGTH_SHORT).show();

                } else {
                    String sql = "UPDATE homework SET name='" + newHomeworkName + "' WHERE id='" + id + "'";
                    dataBase.QueryData(sql);
                    GetData();
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, "Edit homework :'" + name + "' successful!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancelEditing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Failed to edit homework: '" + name + "' !", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
}
