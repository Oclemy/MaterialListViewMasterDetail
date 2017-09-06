package com.tutorials.hp.materiallistviewmasterdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.tutorials.hp.materiallistviewmasterdetail.mData.DataHolder;
import com.tutorials.hp.materiallistviewmasterdetail.mData.Galaxy;
import java.util.ArrayList;
import java.util.List;
import me.riddhimanadib.formmaster.helper.FormBuildHelper;
import me.riddhimanadib.formmaster.model.FormElement;
import me.riddhimanadib.formmaster.model.FormHeader;
import me.riddhimanadib.formmaster.model.FormObject;
/*
- CRUD activity.
- Derives from appcompatactivity.
- Receive data from MainActivity.
- Add/Edit/Delete data.
- We receive serialized data from mainactivity and deserialize it to get Galaxy object to update.
- Otherwise if its null then the activity has been opened or adding new data.
 */
public class CrudActivity extends AppCompatActivity {

    RecyclerView recyclerviewForm;
    FormBuildHelper mFormBuilder;
    Button saveBtn,deleteBtn;
    Galaxy galaxy;
    Boolean isForUpdate = true;
    /*
    - When activity is created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        this.receiveData();
        this.initializeViews();
    }
    /*
    - Initial Views.
    - Create Form Elements.
    - Add/Edit/Delete data.
     */
    private void initializeViews() {
        // Views
        recyclerviewForm = (RecyclerView) findViewById(R.id.recyclerviewForm);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        deleteBtn.setEnabled(isForUpdate);

        mFormBuilder = new FormBuildHelper(this, recyclerviewForm);

        //CREATE FORM ELEMENTS
        FormHeader header = FormHeader.createInstance().setTitle("Galaxy Info");
        final FormElement nameEditText = FormElement.createInstance().setType(FormElement.TYPE_EDITTEXT_TEXT_SINGLELINE).setTitle("Galaxy").setHint("galaxy");
        final FormElement descEditText = FormElement.createInstance().setType(FormElement.TYPE_EDITTEXT_TEXT_MULTILINE).setTitle("Description");

        if (isForUpdate) {
            //UPDATE EDITTEXTS WITH GALAXY VALUES
            nameEditText.setValue(galaxy.getName());
            descEditText.setValue(galaxy.getDescription());
        }
        // ADD FORM ELEMENTS TO LIST
        List<FormObject> formItems = new ArrayList<>();
        formItems.add(header);
        formItems.add(nameEditText);
        formItems.add(descEditText);

        // BUILD AND DISPLAY FORM
        mFormBuilder.addFormElements(formItems);
        mFormBuilder.refreshView();

        //SAVE EITHER AFTER EDITING OR FOR NEW ITEM
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isForUpdate) {
                    //ADD NEW GALAXY
                    int id = DataHolder.galaxies.size();
                    Galaxy galaxy = new Galaxy(nameEditText.getValue(), descEditText.getValue(), id, R.drawable.cartwheel);
                    DataHolder.galaxies.add(galaxy);

                    //RESET UI
                    nameEditText.setValue("");
                    descEditText.setValue("");
                    mFormBuilder.refreshView();

                    Toast.makeText(getApplicationContext(), "Successfully Saved Data", Toast.LENGTH_SHORT).show();

                } else {

                    if (galaxy != null) {
                        //UPDATE EXISTING DATA
                        int position = galaxy.getId();
                        DataHolder.galaxies.remove(position);
                        Galaxy newGalaxy = new Galaxy(nameEditText.getValue(), descEditText.getValue(), position, R.drawable.canismajoroverdensity);
                        DataHolder.galaxies.add(position, newGalaxy);

                        Toast.makeText(getApplicationContext(), "Successfully Updated Item", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Galaxy is null.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //DELETE DATA
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isForUpdate)
                {
                    if (galaxy != null)
                    {
                        //GET ITEM POSITION AND DELETE
                        int position = galaxy.getId();
                        DataHolder.galaxies.remove(position);

                        //RESET UI
                        nameEditText.setValue("");
                        descEditText.setValue("");
                        mFormBuilder.refreshView();
                        deleteBtn.setEnabled(isForUpdate=false);
                        saveBtn.setEnabled(false);

                        Toast.makeText(getApplicationContext(), "Deleted Successfully.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //RECEIVE SERIALIZED OBJECT FROM MAINACTIVITY
    private void receiveData() {
        try {
            //DESERIALIZE DATA FROM MAINACTIVITY
            Intent i = this.getIntent();
            galaxy = (Galaxy) i.getSerializableExtra("KEY_GALAXY");

            if (galaxy == null) {
                isForUpdate = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
