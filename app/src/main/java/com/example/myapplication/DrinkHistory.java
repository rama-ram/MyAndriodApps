package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.dbUtils.DBHelperQueries;
import com.example.dbUtils.DrinkHistoryDBManager;
import com.example.dbUtils.UserProfileDBManager;
import com.example.models.DBModel;
import com.example.common.MyContentProvider;

import java.util.Calendar;

public class DrinkHistory extends ListActivity implements
        LoaderManager.LoaderCallbacks<Cursor>{
    public static DrinkHistoryDBManager dbManager;
    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;
    private static final int DELETE_ID = Menu.FIRST + 1;
    ListView listView;
    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime,text2;
    Spinner spin;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private SimpleCursorAdapter adapter;private Uri uri;
    public void initiateDB(Context context){
        dbManager =  new DrinkHistoryDBManager(context);
        dbManager.open();
        System.out.println("connection opened");
    }
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_history);
        this.getListView().setDividerHeight(2);
        fillData();
        // check from the saved Instance
        Bundle extras = getIntent().getExtras();
        uri = (savedInstanceState == null) ? null : (Uri) savedInstanceState
                .getParcelable(MyContentProvider.CONTENT_ITEM_TYPE);

        // Or passed from the other activity
        if (extras != null) {
            uri = extras
                    .getParcelable(MyContentProvider.CONTENT_ITEM_TYPE);

            registerForContextMenu(getListView());
        }

    }
// Opens the second activity if an entry is clicked
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                DrinkHistory.this);
        final View mview = getLayoutInflater().inflate(R.layout.track_history_edit_dialog, null);
        super.onListItemClick(l, v, position, id);
        final Spinner spinner = (Spinner) mview.findViewById(R.id.spinner2);
        text2 = (EditText) mview.findViewById(R.id.dailyAmount);
        builder.setTitle("Track Fluid");
        final Button btnDatePicker = (Button) mview.findViewById(R.id.selectdate);
        final Button  btnTimePicker = (Button) mview.findViewById(R.id.selecttime);

//set values for all fields as it appears in the list view

        btnDatePicker.setText(mDay + "-" + (mMonth+ 1) + "-" + mYear);
btnTimePicker.setText(mHour + ":" + mMinute);

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 if (v == btnDatePicker) {

                                                     // Get Current Date
                                                     final Calendar c = Calendar.getInstance();
                                                     mYear = c.get(Calendar.YEAR);
                                                     mMonth = c.get(Calendar.MONTH);
                                                     mDay = c.get(Calendar.DAY_OF_MONTH);


                                                     DatePickerDialog datePickerDialog = new DatePickerDialog(DrinkHistory.this,
                                                             new DatePickerDialog.OnDateSetListener() {

                                                                 @Override
                                                                 public void onDateSet(DatePicker view, int year,
                                                                                       int monthOfYear, int dayOfMonth) {

                                                                     btnDatePicker.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                                                 }
                                                             }, mYear, mMonth, mDay);
                                                     datePickerDialog.show();
                                                 }
                                                 if (v == btnTimePicker) {

                                                     // Get Current Time
                                                     final Calendar c = Calendar.getInstance();
                                                     mHour = c.get(Calendar.HOUR_OF_DAY);
                                                     mMinute = c.get(Calendar.MINUTE);

                                                     // Launch Time Picker Dialog
                                                     TimePickerDialog timePickerDialog = new TimePickerDialog(DrinkHistory.this,
                                                             new TimePickerDialog.OnTimeSetListener() {

                                                                 @Override
                                                                 public void onTimeSet(TimePicker view, int hourOfDay,
                                                                                       int minute) {

                                                                     btnTimePicker.setText(hourOfDay + ":" + minute);
                                                                 }
                                                             }, mHour, mMinute, false);
                                                     timePickerDialog.show();
                                                 }
                                             }

                                         }
        );
        btnTimePicker.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 if (v == btnDatePicker) {

                                                     // Get Current Date
                                                     final Calendar c = Calendar.getInstance();
                                                     mYear = c.get(Calendar.YEAR);
                                                     mMonth = c.get(Calendar.MONTH);
                                                     mDay = c.get(Calendar.DAY_OF_MONTH);


                                                     DatePickerDialog datePickerDialog = new DatePickerDialog(DrinkHistory.this,
                                                             new DatePickerDialog.OnDateSetListener() {

                                                                 @Override
                                                                 public void onDateSet(DatePicker view, int year,
                                                                                       int monthOfYear, int dayOfMonth) {

                                                                     btnDatePicker.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                                                 }
                                                             }, mYear, mMonth, mDay);
                                                     datePickerDialog.show();
                                                 }
                                                 if (v == btnTimePicker) {

                                                     // Get Current Time
                                                     final Calendar c = Calendar.getInstance();
                                                     mHour = c.get(Calendar.HOUR_OF_DAY);
                                                     mMinute = c.get(Calendar.MINUTE);

                                                     // Launch Time Picker Dialog
                                                     TimePickerDialog timePickerDialog = new TimePickerDialog(DrinkHistory.this,
                                                             new TimePickerDialog.OnTimeSetListener() {

                                                                 @Override
                                                                 public void onTimeSet(TimePicker view, int hourOfDay,
                                                                                       int minute) {

                                                                     btnTimePicker.setText(hourOfDay + ":" + minute);
                                                                 }
                                                             }, mHour, mMinute, false);
                                                     timePickerDialog.show();
                                                 }
                                             }

                                         }
        );
    String[] projection = {DBModel.TrackHistory.TABLE1_COLUMN1,
                DBModel.TrackHistory.TABLE1_COLUMN2, DBModel.TrackHistory.TABLE1_COLUMN4, DBModel.TrackHistory.TABLE1_COLUMN5};
        Uri uri = Uri.parse(MyContentProvider.CONTENT_URI + "/"
                + id);
        Cursor cursor = getContentResolver().query(uri, projection, null, null,
                null);
        if (cursor != null) {
            cursor.moveToFirst();
            String name = cursor.getString(cursor
                    .getColumnIndexOrThrow(DBModel.TrackHistory.TABLE1_COLUMN1));
            String intake = cursor.getString(cursor
                    .getColumnIndexOrThrow(DBModel.TrackHistory.TABLE1_COLUMN2));
            String date = cursor.getString(cursor
                    .getColumnIndexOrThrow(DBModel.TrackHistory.TABLE1_COLUMN4));
            String time = cursor.getString(cursor
                    .getColumnIndexOrThrow(DBModel.TrackHistory.TABLE1_COLUMN5));

            for (int i = 0; i < spinner.getCount(); i++) {

                String s = (String) spinner.getItemAtPosition(i);
                if (s.equalsIgnoreCase(name)) {
                    spinner.setSelection(i);
                }
            }
            text2.setText(cursor.getString(cursor
                    .getColumnIndexOrThrow(DBModel.TrackHistory.TABLE1_COLUMN2)));
            btnDatePicker.setText(cursor.getString(cursor
                    .getColumnIndexOrThrow(DBModel.TrackHistory.TABLE1_COLUMN4)));
            btnTimePicker.setText(cursor.getString(cursor
                    .getColumnIndexOrThrow(DBModel.TrackHistory.TABLE1_COLUMN5)));
            // always close the cursor
            cursor.close();

            builder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            if (TextUtils.isEmpty(((EditText) mview.findViewById(R.id.dailyAmount)).getText().toString())) {
                                Toast.makeText(getApplicationContext(), "Please add the drink and intake", Toast.LENGTH_LONG).show();
                            } else {
                                setResult(RESULT_OK);
                                finish();
                                saveState();
                            }

                            Toast.makeText(getApplicationContext(), "Added successfully", Toast.LENGTH_LONG).show();

                        }
                    });
            builder.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            Toast.makeText(getApplicationContext(), "Nothing Added!", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    });
            builder.setView(mview);
            AlertDialog dialog = builder.create();
            dialog.show();


        }

    }
        private void saveState() {
            String name= (String) spin.getSelectedItem().toString();
            String intake= text2.getText().toString();
            String date= txtDate.getText().toString();
            String time= txtTime.getText().toString();

            // only save if either summary or description
            // is available

            if (name.length() == 0 && intake.length() == 0 && date.length() == 0 && time.length() == 0) {
                return;
            }
//query for the fluid Id from the fluid table and set it to column 1

            ContentValues values = new ContentValues();
            values.put(DBModel.TrackHistory.TABLE1_COLUMN1, DBHelperQueries.getFluidID(name));
            values.put(DBModel.TrackHistory.TABLE1_COLUMN2,  intake);
            values.put(DBModel.TrackHistory.TABLE1_COLUMN4,  date);
            values.put(DBModel.TrackHistory.TABLE1_COLUMN5,  time);
            values.put(DBModel.TrackHistory.TABLE1_COLUMN6,  MainActivity.profileSpinner.getSelectedItemId());

            if (uri == null) {
                // New todo
                uri= getContentResolver().insert(
                        MyContentProvider.CONTENT_URI, values);
            } else {
                // Update todo
                getContentResolver().update(uri, values, null, null);
            }
        }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case DELETE_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                        .getMenuInfo();
                Uri uri = Uri.parse(MyContentProvider.CONTENT_URI + "/"
                        + info.id);
                getContentResolver().delete(uri, null, null);
                fillData();
                return true;
        }
        return super.onContextItemSelected(item);
    }
        private void fillData() {

        // Fields from the database (projection)
        // Must include the _id column for the adapter to work
        String[] from = new String[] { DBModel.TrackHistory.TABLE1_COLUMN1,DBModel.TrackHistory.TABLE1_COLUMN2,DBModel.TrackHistory.TABLE1_COLUMN4,DBModel.TrackHistory.TABLE1_COLUMN5};
        // Fields on the UI to which we map
        int[] to = new int[] { R.id.eventFluidName,R.id.intake,R.id.trackhistoryDate,R.id.trackhistoryTime };

        getLoaderManager().initLoader(0, null, this);
        adapter = new SimpleCursorAdapter(this, R.layout.track_history_list_row_items, null, from,
                to, 0);
        setListAdapter(adapter);
    }


        // creates a new loader after the initLoader () call
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = { DBModel.TrackHistory._ID, DBModel.TrackHistory.TABLE1_COLUMN1, DBModel.TrackHistory.TABLE1_COLUMN2,DBModel.TrackHistory.TABLE1_COLUMN4,DBModel.TrackHistory.TABLE1_COLUMN5};
        CursorLoader cursorLoader = new CursorLoader(this,
                MyContentProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // data is not available anymore, delete reference
        adapter.swapCursor(null);
    }

}
