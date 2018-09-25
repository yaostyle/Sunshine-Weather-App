/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.datafrominternet;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.datafrominternet.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    // (26) Create an EditText variable called mSearchBoxEditText
    EditText mSearchBoxEditText;
    // (27) Create a TextView variable called mUrlDisplayTextView
    TextView mUrlDisplayTextView;
    // (28) Create a TextView variable called mSearchResultsTextView
    TextView mSearchResultsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // (29) Use findViewById to get a reference to mSearchBoxEditText
        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);

        // (30) Use findViewById to get a reference to mUrlDisplayTextView
        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);

        // (31) Use findViewById to get a reference to mSearchResultsTextView
        mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);
    }

    private void makeGithubSearchQuery() {
        String githubQuery = mSearchBoxEditText.getText().toString();
        URL githubtSearchUrl = NetworkUtils.buildUrl(githubQuery);
        mUrlDisplayTextView.setText(githubtSearchUrl.toString());

        String githubSearchResults = null;
        new GithubQueryTask().execute(githubtSearchUrl);

    }

    public class GithubQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String githubSearchResults = null;

            try {
                githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return githubSearchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s !=null && !s.equals("")) {
                mSearchResultsTextView.setText(s);
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemThatWasSelected = item.getItemId();
        if (menuItemThatWasSelected == R.id.action_search) {
            makeGithubSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
