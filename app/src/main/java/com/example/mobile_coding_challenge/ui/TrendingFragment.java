package com.example.mobile_coding_challenge.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobile_coding_challenge.HttpGetRequest;
import com.example.mobile_coding_challenge.PaginationScrollListener;
import com.example.mobile_coding_challenge.R;
import com.example.mobile_coding_challenge.RepoRecyclerAdapter;
import com.example.mobile_coding_challenge.Repository;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import static com.example.mobile_coding_challenge.PaginationScrollListener.PAGE_START;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrendingFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    private static final String myUrl = "https://api.github.com/search/repositories?q=created:>2017-10-22&sort=stars&order=desc&page=";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private RepoRecyclerAdapter adapter;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;
    int itemCount = 0;
    private String url;

    public TrendingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_trending, container, false);

        ButterKnife.bind(this,root);
        swipeRefresh.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new RepoRecyclerAdapter(getContext(),new ArrayList<Repository>());
        mRecyclerView.setAdapter(adapter);

        // concatenate muUrl with the number of the page
        url = myUrl;
        url+= currentPage;

        // do Api call to get response
        doApiCall(url);

        /**
         * add scroll listener while user reach in bottom load more will call
         */

        mRecyclerView.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;

                // concatenate muUrl with the number of the page
                url = myUrl;
                url+= currentPage;

                // do Api call to get response
                doApiCall(url);
            }
            @Override
            public boolean isLastPage() {
                return isLastPage;
            }
            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });


        return root;

    }

    @Override
    public void onRefresh() {
        itemCount = 0;
        currentPage = PAGE_START;
        isLastPage = false;
        adapter.clear();

        // concatenate muUrl with the number of the page
        url = myUrl;
        url+= currentPage;

        // do Api call to get response
        doApiCall(url);
    }

    private void doApiCall(String url) {


        final ArrayList<Repository> ReposList = new ArrayList<Repository>();
        HttpGetRequest getRequest = new HttpGetRequest();

        String result;

        try{
            result = getRequest.execute(url).get();

            //fetch the result in the console
            System.out.println("result = " + result);

            /**
             * Cast response from String to JSON OBjects and get the results
             * */

            JSONObject jsonRoot = new JSONObject(result);

            final JSONArray reposarray = new JSONArray(jsonRoot.getString("items"));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // for all objects we get infos
                    for (int i = 0; i < reposarray.length(); i++) {

                        try {
                            // we get JSON object from JSON array
                            JSONObject obj = new JSONObject(reposarray.getString(i));
                            JSONObject jsonOwner = reposarray.getJSONObject(i).getJSONObject("owner");

                            // we make the link between Repository and JSON Object
                            Repository r = new Repository();

                            r.setRepository_name(obj.getString("name"));

                            r.setRepository_description(obj.getString("description"));

                            r.setRepository_owner_name(jsonOwner.getString("login"));

                            r.setRepository_avatar(jsonOwner.getString("avatar_url"));

                            // Convert the number of stars if it's > 1000
                            String number = obj.getString("stargazers_count");
                            StringBuffer s = new StringBuffer(number);
                            if(s.length() > 3)
                            {
                                s.insert(2,",");
                                s.insert(4,"k");
                                s.delete(5,s.length());
                                r.setRepository_number_of_stars(s.toString());
                            }
                            else
                            {
                                r.setRepository_number_of_stars(number);
                            }

                            // We add the repository to the list
                            ReposList.add(r);
                        }
                        catch (Exception e) {e.printStackTrace();}

                    }

                    /**
                     * manage progress view while loading the next page from Api
                     */

                    if (currentPage != PAGE_START) adapter.removeLoading();
                    adapter.addItems(ReposList);
                    swipeRefresh.setRefreshing(false);
                    // check weather is last page or not
                    if (currentPage < totalPage) {
                        adapter.addLoading();
                    } else {
                        isLastPage = true;
                    }
                    isLoading = false;
                }
            }, 1000);

        }
        catch (Exception e){e.printStackTrace();}


    }
}