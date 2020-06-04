package com.example.translator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.languageid.FirebaseLanguageIdentification;
import com.google.firebase.ml.naturallanguage.languageid.FirebaseLanguageIdentificationOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /** ! The first thing we do is build a HashMap to store the different country and language codes that our
         * translator will support.
         *
         * HashMaps work by storing a Key and a Value pair: you can think of it as when given a certain key, the
         * HashMap will automatically bring you the right door that fits that key. Behind that door is the value we
         * defined before. Here, we have a langauge and country code as well as a display name, so we build
         * our keys to follow a specific order (language and then country) for better locality (don't worry about
         * why this works better yet--we'll just understand that it is how we'll do things.
         *
         * The more important thing is understanding that when you ask for a specific language
         * (ex. Akan from Ghana, we should have a key that looks like Akan (Ghana) and when we give the HashMap that key,
         * a language code that looks like ak_GH is produced!
         *
         * We'll see how this is used later, but effectively we can pull from all the langauges Android supports
         * to generate a nice list of potential translation options.
         *
         * One of the design choices you will make and need to justify is whether you include the countries in the
         * final version that you store in master, or just the language. We'll see how we can do both later too.
         *
         * Your job here is to add each of the key/value pairs to our HashMap that we define below.
         */
        final HashMap<String,String> languages = new HashMap<>();
        final ArrayList<String> langArray = new ArrayList<>();
        for (String locale : Locale.getISOLanguages()) {
            Locale tempLocale = new Locale(locale);
            String curr_value = locale;
            String curr_key = tempLocale.getDisplayName();
            langArray.add(curr_key);
            //add the current language to the languages HashMap (hint, google adding elements to a hashmap)
        }

        /** ! Here, we need to do a few things:
         * 1: We need to be able to take user input somehow
         * 2: We need to translate the text
         * 3: We need to update the translated text
         *
         * A few questions naturally come up:
         * 1: How do we know when a user is done putting in text?
         * 2: How do we know what language they are typing in and what language they want it translated in?
         * 3: How do you update the new text?
         *
         * We'll explore these below...
         *
         * A few comments:
         * 1: EditText objects are how we take in user input. Notice how we have to specify the ID of the EditText
         * from the layout file.
         * 2: TextView objects are how we display some text; similarly, we need to specify what the ID of the
         * TextView is.
         */
        final EditText editTextToTranslate = (EditText) findViewById(R.id.inputTextToTranslate);
        final TextView translatedText = (TextView) findViewById(R.id.textViewTranslated);

        /** ! Here, we set an onKeyListener. What does this do?
         *
         * Below, we check if the keyCode provided matches some value. Update the key to check
         * (i.e. Enter, shift, control, etc.).
         * This tells us when the user is done entering in text.
         *
         */
        editTextToTranslate.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // !Define a new key from the list here https://developer.android.com/reference/android/view/KeyEvent to be clicked!
                if (keyCode == 0){
                    /** ! Update the TextView we defined above with the text from the EditText.
                     *
                     * Notice, we only do this once we confirm that the correct key has been pressed.
                     */
                    translatedText.setText("Fix me! Change this to be the Edited Text. ");
                }
                return false;
            }
        });

        /** ! Now, we have our languages and we have our text to translate.
         * How do we select which language you want to translate from? What were the options on the python version?
         *
         * Here, we'll need to replicate that same functionality.
         *
         * The process is a bit more complicated for automatic, so I've added in some of the Language Identification model but
         * I encourage you to test around different values for the confidence threshold, etc. and see what happens!
         * Additionally, it is up to you to figure out how you want to incorporate the automatic language detection.
         *
         */


        final Spinner sourceSpinner = (Spinner) findViewById(R.id.sourceLanguageSpinner);
        ArrayAdapter<String> sourceLangArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, langArray);
        sourceLangArrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sourceSpinner.setAdapter(sourceLangArrayAdapter);
        sourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLanguage = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + selectedLanguage, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        final Spinner destSpinner = (Spinner) findViewById(R.id.destinationLanguageSpinner);
        ArrayAdapter<String> destLangArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, langArray);
        destLangArrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        destSpinner.setAdapter(destLangArrayAdapter);
        destSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLanguage = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + selectedLanguage, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        FirebaseLanguageIdentificationOptions options = new FirebaseLanguageIdentificationOptions.Builder().setConfidenceThreshold(0.2F).build();
        FirebaseLanguageIdentification languageIdentifier = FirebaseNaturalLanguage.getInstance().getLanguageIdentification(options);

    }
}







































































