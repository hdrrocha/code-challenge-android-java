package com.arctouch.codechallenge.home;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.model.Movie;
import com.arctouch.codechallenge.util.MovieImageUrlBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/w150";

    private Context context;

    private boolean isLoadingAdded = false;



    private List<Movie> movies;

    public HomeAdapter(Context context) {
        this.context = context;
        this.movies = new ArrayList<>();
    }

    public List<Movie> getMovies() {
        return  this.movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }

        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View view = inflater.inflate(R.layout.movie_item, parent, false);
        viewHolder = new MovieViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Movie result = this.movies.get(position);

        final MovieImageUrlBuilder movieImageUrlBuilder = new MovieImageUrlBuilder();

        switch (getItemViewType(position)) {
            case ITEM:
                final MovieViewHolder movieViewHolder = (MovieViewHolder) holder;

                movieViewHolder.titleTextView.setText(result.title);


                movieViewHolder.releaseDateTextView.setText(
                        result.releaseDate.substring(0, 4)
                );
                movieViewHolder.genresTextView.setText(TextUtils.join(", ", result.genres));
                String posterPath = result.posterPath;
                if (TextUtils.isEmpty(posterPath) == false) {
                    Glide.with(context)
                            .load(movieImageUrlBuilder.buildPosterUrl(posterPath))
                            .apply(new RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                            .into(movieViewHolder.posterImageView);
                }

                if(position %2 == 1)
                {
                    holder.itemView.setBackgroundColor(Color.parseColor("#EBEBEB"));
                }
                else
                {
                    holder.itemView.setBackgroundColor(Color.parseColor("#fafafa"));
                }
                break;

            case LOADING:
                break;
        }

    }


    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == this.movies.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

//   Helpers

    public void add(Movie r) {
        this.movies.add(r);
        notifyItemInserted(this.movies.size() - 1);
    }

    public void addAll(List<Movie> moveResults) {
        for (Movie result : moveResults) {
            add(result);
        }
    }

    public void remove(Movie r) {
        int position = this.movies.indexOf(r);
        if (position > -1) {
            this.movies.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Movie());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = this.movies.size() - 1;
        Movie result = getItem(position);

        if (result != null) {
            this.movies.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Movie getItem(int position) {
        return this.movies.get(position);
    }


//   View Holders

    protected class MovieViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView genresTextView;
        private final TextView releaseDateTextView;
        private final ImageView posterImageView;
        private ProgressBar mProgress;

        public MovieViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
            genresTextView = itemView.findViewById(R.id.genresTextView);
            releaseDateTextView = itemView.findViewById(R.id.releaseDateTextView);
            posterImageView = itemView.findViewById(R.id.posterImageView);


            mProgress = (ProgressBar) itemView.findViewById(R.id.movie_progress);
        }
    }



}
