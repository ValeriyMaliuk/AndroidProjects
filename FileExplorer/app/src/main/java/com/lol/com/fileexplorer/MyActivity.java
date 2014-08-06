package com.lol.com.fileexplorer;

        import java.io.File;
        import java.util.ArrayList;
        import java.util.List;

        import android.app.AlertDialog;
        import android.app.ListActivity;
        import android.content.ActivityNotFoundException;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.DialogInterface.OnClickListener;
        import android.net.Uri;
        import android.os.Bundle;
        import android.view.View;
        import android.webkit.MimeTypeMap;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

public class MyActivity extends ListActivity
{
    private List<String> directoryEntries = new ArrayList<String>();
    private File currentDirectory = new File("/");
    //when application started
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        //set main layout
        setContentView(R.layout.activity_my);
        //browse to root directory
        browseTo(new File("/"));
    }

    //browse to parent directory
    private void upOneLevel(){
        if(this.currentDirectory.getParent() != null) {
            this.browseTo(this.currentDirectory.getParentFile());
        }
    }

    //browse to file or directory
    private void browseTo(final File aDirectory){
        //if we want to browse directory
        if (aDirectory.isDirectory()){
            //fill list with files from this directory
            this.currentDirectory = aDirectory;
            fill(aDirectory.listFiles());

            //set titleManager text
            TextView titleManager = (TextView) findViewById(R.id.titleManager);
            titleManager.setText(aDirectory.getAbsolutePath());
        } else {
            //if we want to open file, show this dialog:
            //listener when YES button clicked
            OnClickListener okButtonListener = new OnClickListener(){
                public void onClick(DialogInterface arg0, int arg1) {
                    //intent to navigate file
                    String mime = get_mime_by_filename(aDirectory.getAbsolutePath());
                    Intent intent1 = new Intent();
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent1.setAction(android.content.Intent.ACTION_VIEW);

                    intent1.setDataAndType(Uri.fromFile(new File(aDirectory
                            .getAbsolutePath())), mime);

                    try {
                        startActivity(intent1);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't open: unknown file type",
                                Toast.LENGTH_SHORT).show();
                    }

                }
            };
            //listener when NO button clicked
            OnClickListener cancelButtonListener = new OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    //do nothing
                    //or add something you want
                }
            };

            //create dialog
            new AlertDialog.Builder(this)
                    .setTitle("Подтверждение") //title
                    .setMessage("Хотите открыть файл "+ aDirectory.getName() + "?") //message
                    .setPositiveButton("Да", okButtonListener) //positive button
                    .setNegativeButton("Нет", cancelButtonListener) //negative button
                    .show(); //show dialog
        }
    }
    //fill list
    private void fill(File[] files) {
        //clear list
        this.directoryEntries.clear();

        if (this.currentDirectory.getParent() != null)
            this.directoryEntries.add("..");

        //add every file into list
        for (File file : files) {
            this.directoryEntries.add(file.getAbsolutePath());
        }

        //create array adapter to show everything
        ArrayAdapter<String> directoryList = new ArrayAdapter<String>(this, R.layout.row, this.directoryEntries);
        this.setListAdapter(directoryList);
    }
    //when you clicked onto item
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //get selected file name
        int selectionRowID = position;
        String selectedFileString = this.directoryEntries.get(selectionRowID);

        //if we select ".." then go upper
        if(selectedFileString.equals("..")){
            this.upOneLevel();
        } else {
            //browse to clicked file or directory using browseTo()
            File clickedFile = null;
            clickedFile = new File(selectedFileString);
            if (clickedFile != null)
                this.browseTo(clickedFile);
        }
    }
    private String get_mime_by_filename(String filename){
        String ext;
        String type;

        int lastdot = filename.lastIndexOf(".");
        if(lastdot > 0){
            ext = filename.substring(lastdot + 1);
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(ext);
            if(type != null) {
                return type;
            }
        }
        return "application/octet-stream";
    }
}