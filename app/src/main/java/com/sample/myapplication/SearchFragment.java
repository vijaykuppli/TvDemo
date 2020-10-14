package com.sample.myapplication;

import androidx.leanback.app.SearchSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.ObjectAdapter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
public class SearchFragment extends SearchSupportFragment
        implements SearchSupportFragment.SearchResultProvider {
    private static final String TAG = "leanback.SearchFragment";
    private static final int NUM_ROWS = 3;
    private static final int SEARCH_DELAY_MS = 1000;
    private ArrayObjectAdapter mRowsAdapter;
    private Handler mHandler = new Handler();
    private String mQuery;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        setBadgeDrawable(getActivity().getResources().getDrawable(R.drawable.app_icon_your_company));
        setTitle("Leanback Sample App");
        setSearchResultProvider(this);
        setOnItemViewClickedListener(new ItemClickedListener());
    }
    @Override
    public ObjectAdapter getResultsAdapter() {
        return mRowsAdapter;
    }
    @Override
    public boolean onQueryTextChange(String newQuery) {
        Log.i(TAG, String.format("Search Query Text Change %s", newQuery));
        mRowsAdapter.clear();
        loadQuery(newQuery);
        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.i(TAG, String.format("Search Query Text Submit %s", query));
        mRowsAdapter.clear();
        loadQuery(query);
        return true;
    }
    private void loadQuery(String query) {
        mQuery = query;
        mHandler.removeCallbacks(mDelayedLoad);
        if (!TextUtils.isEmpty(query) && !query.equals("nil")) {
            mHandler.postDelayed(mDelayedLoad, SEARCH_DELAY_MS);
        }
    }
    private void loadRows() {
        for (int i = 0; i < NUM_ROWS; ++i) {
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new StringPresenter());
            listRowAdapter.add("Hello world");
            listRowAdapter.add("This is a test");
            HeaderItem header = new HeaderItem(i, mQuery + " results row " + i);
            mRowsAdapter.add(new ListRow(header, listRowAdapter));
        }
    }
    private Runnable mDelayedLoad = new Runnable() {
        @Override
        public void run() {
            loadRows();
        }
    };
    private final class ItemClickedListener implements OnItemViewClickedListener {

        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            startActivity(intent);
        }
    }
}
