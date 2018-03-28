package com.ai.dorvis.constraintlayout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

       EditText editText1,editText2,editText3,editText4;
       SQLiteDatabase dbase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText1=(EditText)findViewById(R.id.EditText);
        editText2=(EditText)findViewById(R.id.EditText2);
        editText3=(EditText)findViewById(R.id.EditText3);
        editText4=(EditText)findViewById(R.id.EditText4);

        dbase = openOrCreateDatabase("mydb", Context.MODE_PRIVATE,null);
        dbase.execSQL("create table if not exists employee(id number,name varchar(20)," +
                "address varchar(50),design varchar(20))");

    }
    public void insert(View view){
        ContentValues values = new ContentValues();
        values.put("id",Integer.parseInt(editText1.getText().toString()));
        values.put("name",editText2.getText().toString());
        values.put("address",editText3.getText().toString());
        values.put("design",editText4.getText().toString());
        dbase.insert("employee",null,values);
        Toast.makeText(getApplicationContext(),"data inserted successfully",Toast.LENGTH_LONG).show();
        editText1.setText("");
        editText2.setText("");
        editText3.setText("");
        editText4.setText("");
    }

    public void read(View view) {
        Cursor cursor = dbase.query("employee",new String[]{"id","name","address","design"},
                null,null,null,null,null);
        while (cursor.moveToNext()){
            String msg = cursor.getInt(0)+"\n"+cursor.getString(1)+"\n"+cursor.getString(2)+
                    "\n"+cursor.getString(3);

            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();

        }

    }

    public void update(View view) {

        ContentValues values = new ContentValues();
        values.put("id",Integer.parseInt(editText1.getText().toString()));
        values.put("name",editText2.getText().toString());
        values.put("address",editText3.getText().toString());
        values.put("design",editText4.getText().toString());
       long count= dbase.update("employee",values,"id?",new String[]{editText1.getText().toString()});
       if (count>0){
           Toast.makeText(getApplicationContext(),"data updated successfully",Toast.LENGTH_LONG).show();
           editText1.setText("");
           editText2.setText("");
           editText3.setText("");
           editText4.setText("");

       }else {
           Toast.makeText(getApplicationContext(),"data is not updated ",Toast.LENGTH_LONG).show();
       }
    }
// delete the data into sqlite database
    public void delete(View view) {
        long count = dbase.delete("employee","id=? and design=?",new String[]{editText1.getText().toString(),
        editText4.getText().toString()});
        if (count>0){
            Toast.makeText(getApplicationContext(),"data deleted successfull",Toast.LENGTH_LONG).show();
        }else
        {
            Toast.makeText(getApplicationContext(),"data not deleted ",Toast.LENGTH_LONG).show();

        }
    }
}
