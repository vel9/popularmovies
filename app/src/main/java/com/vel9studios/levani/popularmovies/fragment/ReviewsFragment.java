package com.vel9studios.levani.popularmovies.fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vel9studios.levani.popularmovies.R;
import com.vel9studios.levani.popularmovies.constants.AppConstants;
import com.vel9studios.levani.popularmovies.constants.ProjectionConstants;
import com.vel9studios.levani.popularmovies.data.FetchReviewsTask;
import com.vel9studios.levani.popularmovies.views.ReviewsAdapter;


public class ReviewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private final String LOG_TAG = ReviewsFragment.class.getSimpleName();
    private ReviewsAdapter mReviewsAdapter;

    //Uris
    private Uri mReviewsUri;
    private static final int REVIEWS_LOADER = 1;

    public ReviewsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null) {

            mReviewsUri = arguments.getParcelable(AppConstants.REVIEWS_URI_KEY);
            if (mReviewsUri != null){
                // get movieId we want to view reviews for
                String movieId = mReviewsUri.getLastPathSegment();
                // get the reviews
                if (savedInstanceState == null){

                    FetchReviewsTask fetchReviewsTask = new FetchReviewsTask(getActivity());
                    fetchReviewsTask.execute(movieId);
                }
            }
        }

        //set text elements
        View rootView = inflater.inflate(R.layout.fragment_reviews, container, false);

        // set up reviews list view + adapter
        ListView reviewsListView = (ListView) rootView.findViewById(R.id.listview_reviews);
        mReviewsAdapter = new ReviewsAdapter(getActivity(), null, 0);
        reviewsListView.setAdapter(mReviewsAdapter);
        reviewsListView.setEmptyView(rootView.findViewById(R.id.listview_reviews_empty));

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(REVIEWS_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if (id == REVIEWS_LOADER && mReviewsUri != null){

            return new CursorLoader(
                    getActivity(),
                    mReviewsUri,
                    ProjectionConstants.REVIEWS_DETAIL_COLUMNS,
                    null,
                    null,
                    null
            );
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (loader.getId() == REVIEWS_LOADER){
            if (cursor != null && cursor.moveToFirst()) {
                mReviewsAdapter.swapCursor(cursor);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() == REVIEWS_LOADER)
            mReviewsAdapter.swapCursor(null);
    }
}
