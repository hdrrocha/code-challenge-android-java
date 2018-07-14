package com.arctouch.codechallenge.home;

import android.content.Context;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

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


//    public static class ViewHolder extends RecyclerView.ViewHolder {
//
//
//
//        private final MovieImageUrlBuilder movieImageUrlBuilder = new MovieImageUrlBuilder();
//
//        private final TextView titleTextView;
//        private final TextView genresTextView;
//        private final TextView releaseDateTextView;
//        private final ImageView posterImageView;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            titleTextView = itemView.findViewById(R.id.titleTextView);
//            genresTextView = itemView.findViewById(R.id.genresTextView);
//            releaseDateTextView = itemView.findViewById(R.id.releaseDateTextView);
//            posterImageView = itemView.findViewById(R.id.posterImageView);
//        }
//
//        public void bind(Movie movie) {
//            titleTextView.setText(movie.title);
//            if (movie.genres != null){
//                genresTextView.setText(TextUtils.join(", ", movie.genres));
//            } else {
//                genresTextView.setText("Não informado");
//            }
//
//            releaseDateTextView.setText(movie.releaseDate);
//
//            String posterPath = movie.posterPath;
//            if (TextUtils.isEmpty(posterPath) == false) {
//                Glide.with(itemView)
//                        .load(movieImageUrlBuilder.buildPosterUrl(posterPath))
//                        .apply(new RequestOptions().placeholder(R.drawable.ic_image_placeholder))
//                        .into(posterImageView);
//            }
//        }
//    }

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);

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
        View v1 = inflater.inflate(R.layout.movie_item, parent, false);
        viewHolder = new MovieVH(v1);
        return viewHolder;
    }



//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.bind(movies.get(position));
//    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Movie result = this.movies.get(position); // Movie
        final MovieImageUrlBuilder movieImageUrlBuilder = new MovieImageUrlBuilder();
        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH movieVH = (MovieVH) holder;

                movieVH.titleTextView.setText(result.title);


                movieVH.releaseDateTextView.setText(
                        result.releaseDate.substring(0, 4)  // we want the year only
                );
                movieVH.genresTextView.setText(TextUtils.join(", ", result.genres));
                String posterPath = result.posterPath;
                if (TextUtils.isEmpty(posterPath) == false) {
                    Glide.with(context)
                            .load(movieImageUrlBuilder.buildPosterUrl(posterPath))
                            .apply(new RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                            .into(movieVH.posterImageView);
                }
//                /**
//                 * Using Glide to handle image loading.
//                 * Learn more about Glide here:
//                 *
//                 */
//                Glide
//                        .with(context)
//                        .load(BASE_URL_IMG + result.posterPath)
//                        .listener(new RequestListener<String, GlideDrawable>() {
//                            @Override
//                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//
//                                movieVH.mProgress.setVisibility(View.GONE);
//                                return false;
//                            }
//
//                            @Override
//                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                                // image ready, hide progress now
//                                movieVH.mProgress.setVisibility(View.GONE);
//                                return false;   // return false if you want Glide to handle everything else.
//                            }
//                        })
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
//                        .centerCrop()
//                        .crossFade()
//                        .into(movieVH.posterImageView);

                break;

            case LOADING:
//                Do nothing
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
//   _________________________________________________________________________________________________


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

     /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    protected class MovieVH extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView genresTextView;
        private final TextView releaseDateTextView;
        private final ImageView posterImageView;
        private ProgressBar mProgress;

        public MovieVH(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
            genresTextView = itemView.findViewById(R.id.genresTextView);
            releaseDateTextView = itemView.findViewById(R.id.releaseDateTextView);
            posterImageView = itemView.findViewById(R.id.posterImageView);


            mProgress = (ProgressBar) itemView.findViewById(R.id.movie_progress);
        }
    }



}
