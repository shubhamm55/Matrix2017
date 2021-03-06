package spit.matrix2017.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import spit.matrix2017.R;

public class EventDetails extends AppCompatActivity {

    private boolean isFavouriteEvent;
    private boolean isReminderSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        //get intent from eventlist adapter
        if (getIntent().getStringExtra("EVENT_NAME") != null && getSupportActionBar() != null) {
            this.setTitle(getIntent().getStringExtra("EVENT_NAME"));
        } else
            this.setTitle("Some event");
        // TODO: 11/1/2016 Add logic to get selected event from db
        setDescription(getString(R.string.fake_description));
        setRules();
        setPrizes();
        setContacts("Contact Person 1", 9874563210L, "Contact Person 2", 3216549870L);
        isFavouriteEvent = false; // TODO: 11/1/2016 according to event set this value
        isReminderSet = false;  // TODO: 11/1/2016 according to event set this value

        ImageView mainImageView = (ImageView) findViewById(R.id.main_imageView);
        assert mainImageView != null;
        mainImageView.setImageResource(R.drawable.virtual_stock_market); // TODO: 11/2/2016 according to event set this value

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Snackbar snackbar = Snackbar.make(view, "Do you want to register ?", Snackbar.LENGTH_LONG);
                snackbar.setAction("Yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: 11/1/2016 Add intent to open google form in browser
                    }
                });
                snackbar.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        setFavourite(menu.findItem(R.id.action_add_to_favourites), isFavouriteEvent);
        setReminder(menu.findItem(R.id.action_set_reminder), isReminderSet);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_to_favourites:
                setFavourite(item, !isFavouriteEvent); //used as toggle
                break;
            case R.id.action_set_reminder:
                setReminder(item, !isReminderSet); //used as toggle
                break;
            case R.id.action_share:
                // TODO: 11/2/2016 create method to share event
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setTitle(final String title) {
        /*Code to make title visible only in collapsed state*/
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar_event);

        assert collapsingToolbarLayout != null;
        assert appBarLayout != null;
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(title); // setting title of page
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle("");
                    isShow = false;
                }
            }
        });
    }

    private void setDescription(String description) {
        AppCompatTextView descriptionTextView = (AppCompatTextView) findViewById(R.id.description_textView);
        assert descriptionTextView != null;
        descriptionTextView.setText(description);
    }

    private void setRules() {

        ArrayList<String> rulesList = new ArrayList<>();
        // TODO: 11/1/2016 add logic to get rules into above arraylist
        rulesList.add("Some Rule");
        rulesList.add("Much Rule");
        rulesList.add("Wow Rule");

        StringBuilder textViewString = new StringBuilder();

        for (String rule : rulesList) {
            textViewString.append("\u2022"); //bullet
            textViewString.append(" ");
            textViewString.append(rule);
            textViewString.append("\n");
        }
        textViewString.deleteCharAt(textViewString.length() - 1);

        TextView rulesTextView = (TextView) findViewById(R.id.rules_textView);
        assert rulesTextView != null;
        rulesTextView.setText(textViewString);
    }

    private void setPrizes() {
        ArrayList<String> prizesList = new ArrayList<>();
        // TODO: 11/1/2016 Add logic to get prizes into above arraylist
        prizesList.add("Some Prize");
        prizesList.add("Much Gift");
        prizesList.add("Wow Cash Prize");

        StringBuilder textViewString = new StringBuilder();

        for (String rule : prizesList) {
            textViewString.append("\u25BA"); //right arrow
            textViewString.append(" ");
            textViewString.append(rule);
            textViewString.append("\n");
        }
        textViewString.deleteCharAt(textViewString.length() - 1);

        TextView prizesTextView = (TextView) findViewById(R.id.prizes_textView);
        assert prizesTextView != null;
        prizesTextView.setText(textViewString);
    }

    private void setContacts(final String name1, final long number1, final String name2, final long number2) {
        TextView contactTextViewOne = (TextView) findViewById(R.id.contact_name_one);
        TextView contactTextViewTwo = (TextView) findViewById(R.id.contact_name_two);

        ImageButton callOne = (ImageButton) findViewById(R.id.call_contact_person_one);
        ImageButton saveOne = (ImageButton) findViewById(R.id.save_contact_person_one);
        ImageButton callTwo = (ImageButton) findViewById(R.id.call_contact_person_two);
        ImageButton saveTwo = (ImageButton) findViewById(R.id.save_contact_person_two);

        assert contactTextViewOne != null;
        contactTextViewOne.setText(name1 + "\n" + number1);
        assert contactTextViewTwo != null;
        contactTextViewTwo.setText(name2 + "\n" + number2);

        View.OnClickListener callOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                switch (v.getId()) {
                    case R.id.call_contact_person_one:
                        intent.setData(Uri.parse("tel:" + number1));
                        break;
                    case R.id.call_contact_person_two:
                        intent.setData(Uri.parse("tel:" + number2));
                        break;
                }
                startActivity(intent);
            }
        };

        View.OnClickListener saveOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI);
                switch (v.getId()) {
                    case R.id.save_contact_person_one:
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, name1);
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, "" + number1);
                        break;
                    case R.id.save_contact_person_two:
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, name2);
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, "" + number2);
                        break;
                }
                startActivity(intent);
            }
        };

        assert callOne != null;
        callOne.setOnClickListener(callOnClickListener);
        assert callTwo != null;
        callTwo.setOnClickListener(callOnClickListener);
        assert saveOne != null;
        saveOne.setOnClickListener(saveOnClickListener);
        assert saveTwo != null;
        saveTwo.setOnClickListener(saveOnClickListener);
    }

    private void setFavourite(MenuItem menuItem, boolean isFavourite) {
        isFavouriteEvent = isFavourite;
        // TODO: 11/2/2016 logic to update db
        if (isFavourite) {
            menuItem.setIcon(R.drawable.heart_red_filled);
        } else {
            menuItem.setIcon(R.drawable.heart_white_filled);
        }
    }

    private void setReminder(MenuItem menuItem, boolean isReminderSet) {
        this.isReminderSet = isReminderSet;
        // TODO: 11/2/2016 logic to update db
        if (isReminderSet) {
            menuItem.setIcon(R.drawable.bell_ring);
        } else {
            menuItem.setIcon(R.drawable.bell_ring_outline);
        }
    }
}
