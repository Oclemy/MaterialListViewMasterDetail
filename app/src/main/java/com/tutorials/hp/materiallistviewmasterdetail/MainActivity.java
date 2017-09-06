package com.tutorials.hp.materiallistviewmasterdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.CardProvider;
import com.dexafree.materialList.card.OnActionClickListener;
import com.dexafree.materialList.card.action.TextViewAction;
import com.dexafree.materialList.view.MaterialListView;
import com.squareup.picasso.RequestCreator;
import com.tutorials.hp.materiallistviewmasterdetail.mData.DataHolder;
import com.tutorials.hp.materiallistviewmasterdetail.mData.Galaxy;
/*
- Our MainActivity class.
- Derives from AppCompatActivity.
- Methods: onCreate(),initializeViews(),bindData(),createCard(),onResume().
- We use MaterializeListView as our adapterview to display cards.
- We create material cards using createCard() method.
- We bind data to our cards using bindData() method.
- When new button in card is clicked we open crud activity for adding new data.
- When edit button is clicked we open crud activity for editing data.
 */
public class MainActivity extends AppCompatActivity {

    MaterialListView materialListView;
    /*
    - When activity is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initializeViews();
    }

    /*
    - Initial Material ListView.
     */
    private void initializeViews() {
        materialListView = (MaterialListView) findViewById(R.id.material_listview);
    }

    /*
    - Bind data to Material ListView.
     */
    private void bindData() {
		materialListView.getAdapter().clearAll();
        for (Galaxy g : DataHolder.getData()) {
            this.createCard(g);
        }
    }
    /*
    - Create Card.
    - Set its title,description and image.
    - Handle action button click events.
    - Set card to adapter.
    - Set adapter to listview.
     */
    private void createCard(final Galaxy g) {
        Card card = new Card.Builder(this)
                .withProvider(new CardProvider())
               .setLayout(R.layout.material_basic_image_buttons_card_layout)
                //.setLayout(R.layout.material_image_with_buttons_card)
                //.setLayout(R.layout.material_basic_buttons_card)
                //.setLayout(R.layout.material_welcome_card_layout)
                //.setLayout(R.layout.material_small_image_card)
               //.setLayout(R.layout.material_big_image_card_layout)
                .setTitle(g.getName())
                .setTitleGravity(Gravity.CENTER_HORIZONTAL)
                .setDescription(g.getDescription())
                .setDescriptionGravity(Gravity.CENTER_HORIZONTAL)
                .setDrawable(g.getImage())
                .setDrawableConfiguration(new CardProvider.OnImageConfigListener() {
                    @Override
                    public void onImageConfigure(@NonNull RequestCreator requestCreator) {
                        //requestCreator.fit();
                        requestCreator.resize(121,121);
                    }
                })
                .addAction(R.id.left_text_button, new TextViewAction(this)
                        .setText("New")
                        .setTextResourceColor(R.color.colorPrimary)
                        .setListener(new OnActionClickListener() {
                            @Override
                            public void onActionClicked(View view, Card card) {
                                Intent i = new Intent(MainActivity.this, CrudActivity.class);
                                Context c = MainActivity.this;
                                c.startActivity(i);
                            }
                        }))
                .addAction(R.id.right_text_button, new TextViewAction(this)
                        .setText("Edit")
                        .setTextResourceColor(R.color.orange_button)
                        .setListener(new OnActionClickListener() {
                            @Override
                            public void onActionClicked(View view, Card card) {
                                Intent i = new Intent(MainActivity.this, CrudActivity.class);
                                i.putExtra("KEY_GALAXY", g);
                                Context c = MainActivity.this;
                                c.startActivity(i);
                            }
                        }))
                .endConfig()
                .build();

        //Add Card to Adapter and set the adapter to material listview.
        materialListView.getAdapter().add(card);
    }

    /*
    - When activity is resumed, bind data
     */
    @Override
    protected void onResume() {
        super.onResume();
		this.bindData();
    }
}
