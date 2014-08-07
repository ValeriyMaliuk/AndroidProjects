package com.lol.com.person;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MyActivity extends ActionBarActivity
{
    private String LOG_TAG = "MyLOGS";
    private EditText Fname;
    private EditText Lname;
    private EditText date;
    private EditText age;
    private Button send;
    private Button loadButton;
    private ImageView image;
    private static final int REQUEST = 1;
    public final int CAMERA_RESULT = 0;
    Button FlashLightControl;
    ImageView ivCamera;
    PersonDB db;
    SQLiteDatabase sqdb;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Fname = (EditText) findViewById(R.id.Fname);
        Lname = (EditText) findViewById(R.id.Lname);
        date = (EditText) findViewById(R.id.date);
        age = (EditText) findViewById(R.id.age);
        send = (Button) findViewById(R.id.send);

        image = (ImageView) findViewById(R.id.imageView);
        loadButton = (Button) findViewById(R.id.imggal);

        FlashLightControl = (Button) findViewById(R.id.imgcam);
        ivCamera = (ImageView) findViewById(R.id.imageView);

        // Обработчик изменения дня рождения
        date.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(IsDate(date.getText().toString()))
                {
                    age.setText(calcDate());
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,	int after) {}
            @Override
            public void afterTextChanged(Editable s) {      }
        });
        //обработчик отправки Email
        send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"unrealworkouter@mail.ru"});
                email.putExtra(Intent.EXTRA_SUBJECT, "PERSON");
                email.putExtra(Intent.EXTRA_TEXT,"Имя: "+Fname.getText().toString()+" " + Lname.getText().toString()+ "Дата рождения: "+date.getText().toString()+'\n'+"Возраст: "+age.getText().toString());
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Выберите email клиент :"));
            }
        });
        // Инициализируем наш класс-обёртку
        db = new PersonDB(this);
        sqdb = db.getWritableDatabase();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    public void CameraTurnClick(View v)
    {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_RESULT);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ImgFromGal(View view)
    {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bitmap img = null;

        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                img = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            image.setImageBitmap(img);
        }
        if (requestCode == CAMERA_RESULT) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ivCamera.setImageBitmap(thumbnail);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    ////////////////////////////////////////////////
    ////////////////// WORKING WITH DATABASE
    ////////////////////////////////////////////////
    public void savetoDB(View view)
    {
        // удаляем все записи
        int clearCount = sqdb.delete("PERSON", null, null);

        ContentValues cv = new ContentValues();
        // получаем данные из полей ввода
        String fname = Fname.getText().toString();
        String lname = Lname.getText().toString();
        String Age   = age.getText().toString();
        String Bday  = date.getText().toString();

        Log.d(LOG_TAG, "--- Insert in PERSON: ---");
        // подготовим данные для вставки в виде пар: наименование столбца - значение
        cv.put("fname", fname);
        cv.put("lname", lname);
        cv.put("age",   Age);
        cv.put("bday",  Bday);
        // вставляем запись и получаем ее ID
        sqdb.insert("PERSON", null, cv);

    }
    public  void  loadfromDB(View view)
    {
        // делаем запрос всех данных из таблицы mytable, получаем Cursor
        Cursor c = sqdb.query("PERSON", null, null, null, null, null, null);

        // ставим позицию курсора на первую строку выборки
        if (c.moveToFirst())
        {
            // определяем номера столбцов по имени в выборке
            int ageColIndex = c.getColumnIndex("age");
            int fnameColIndex = c.getColumnIndex("fname");
            int lnameColIndex = c.getColumnIndex("lname");
            int bdayColIndex = c.getColumnIndex("bday");

            do
            {
                Fname.setText(c.getString(fnameColIndex));
                Lname.setText(c.getString(lnameColIndex));
                age.setText(c.getString(ageColIndex));
                date.setText(c.getString(bdayColIndex));
                // переход на следующую строку
            } while (c.moveToNext());
        } else
            Log.d(LOG_TAG, "N0 rows");
        c.close();
    }
    public void show(View view)
    {
        Intent intent = new Intent(MyActivity.this, info.class);
        intent.putExtra("username", Fname.getText().toString());
        intent.putExtra("userlname", Lname.getText().toString());
        intent.putExtra("userbd", date.getText().toString());
        intent.putExtra("age", age.getText().toString());
        startActivity(intent);
    }
    private String calcDate()
    {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date myDate = null;
        try
        {
            myDate = df.parse(date.getText().toString());
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(myDate);
        dob.add(Calendar.DAY_OF_MONTH, -1);
        int Age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) <= dob.get(Calendar.DAY_OF_YEAR)) {
            Age--;
        }
        return String.valueOf(Age);
    }
    private Boolean IsDate(String str_date)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try
        {
            Date date = formatter.parse(str_date);
        }
        catch (ParseException e)
        {
            return false;
        }
        return true;
    }
}
