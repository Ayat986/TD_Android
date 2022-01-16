package com.example.movieapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.MyApplication;
import com.example.movieapp.R;
import com.example.movieapp.models.MovieModel;
import com.example.movieapp.utils.Credentials;

import java.util.List;

public class MovieRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<MovieModel> mMovies;
    private OnMovieListener onMovieListener;

    private static final int DISPLAY_POP = 1;
    private static final int DISPLAY_SEARCH = 2;
    private static final int DISPLAY_UPCOM = 3;

    //here
    public Context context;
    private MyApplication globalClass;
    private List<MovieModel> favMovies;

    public MovieRecyclerView(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        //here
        context = parent.getContext();
        globalClass = (MyApplication) context.getApplicationContext();

        if(viewType == DISPLAY_SEARCH){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,  parent, false);
            return new MovieViewHolder(view, onMovieListener);
        }
        else if(viewType == DISPLAY_POP){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_movies_layout,  parent, false);
            return new Popular_View_Holder(view, onMovieListener);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_movies_layout,  parent, false);
            return new Upcoming_View_Holder(view, onMovieListener);
        }
        /*View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,  parent, false);
        return new MovieViewHolder(view, onMovieListener);*/
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
       int itemViewType = getItemViewType(i);

        MovieModel movie_id = mMovies.get(i);


        Log.d("added","value: " + mMovies.get(i));

       if(itemViewType == DISPLAY_SEARCH){
            ((MovieViewHolder)holder).title.setText(mMovies.get(i).getTitle());
        ((MovieViewHolder)holder).release_date.setText(mMovies.get(i).getRelease_date());
        ((MovieViewHolder)holder).duration.setText(""+mMovies.get(i).getVote_average());


           //vote average is over 10, and our rating bar is over 5 stars : dividing by 2
           ((MovieViewHolder)holder).ratingBar.setRating((mMovies.get(i).getVote_average())/2);


           //ImageView: Using Glide Library
           Glide.with(holder.itemView.getContext())
                   .load("https://image.tmdb.org/t/p/w500/" + mMovies.get(i).getPoster_path())
                   .into(((MovieViewHolder)holder).imageView);
       }else if (itemViewType == DISPLAY_POP){

           //vote average is over 10, and our rating bar is over 5 stars : dividing by 2
           ((Popular_View_Holder)holder).ratingBar22.setRating((mMovies.get(i).getVote_average())/2);


           //ImageView: Using Glide Library
           Glide.with(holder.itemView.getContext())
                   .load("https://image.tmdb.org/t/p/w500/" + mMovies.get(i).getPoster_path())
                   .into(((Popular_View_Holder)holder).imageView22);
       }else{

           //vote average is over 10, and our rating bar is over 5 stars : dividing by 2
           ((Upcoming_View_Holder)holder).ratingBar33.setRating((mMovies.get(i).getVote_average())/2);


           //ImageView: Using Glide Library
           Glide.with(holder.itemView.getContext())
                   .load("https://image.tmdb.org/t/p/w500/" + mMovies.get(i).getPoster_path())
                   .into(((Upcoming_View_Holder)holder).imageView33);
       }
        //HERE
        ((MovieViewHolder)holder).addFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                favMovies = globalClass.getMovies();


                boolean exist = false;
                for (int counter = 0; counter < favMovies.size(); counter++) {
                    if(favMovies.get(counter)== movie_id){
                        exist=true;
                    }
                }
                if(exist){
                    Toast.makeText(v.getContext(), "Film déja dans les favoris", Toast.LENGTH_SHORT).show();
                    Log.d("myTag", "This is my message");
                }else{
                    ((MovieViewHolder)holder).addFav.setColorFilter(context.getResources().getColor(R.color.red));
                    favMovies.add(movie_id);
                    Toast.makeText(v.getContext(), "Ajouté au favoris", Toast.LENGTH_SHORT).show();
                    Log.d("id du movie ajouté", String.valueOf(movie_id.getMovie_id()));
                    Log.d("favoris", "favoris: " + favMovies);


                }


            }
        });

    }

    @Override
    public int getItemCount() {

        if (mMovies != null){
            return mMovies.size();
        }
        return 0;
    }

    public void setmMovies(List<MovieModel> mMovies) {
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }

    //Getting the id of the movie clicked
    public MovieModel getSelectedMovie(int position){
       if(mMovies != null){
           if(mMovies.size() > 0){
               return mMovies.get(position);
           }
       }
        return null;
    }


    @Override
    public int getItemViewType(int position){
        if(Credentials.POPULAR){
            return DISPLAY_POP;
        } else if(Credentials.UPCOMING){
            return DISPLAY_UPCOM;
        }else{
            return DISPLAY_SEARCH;
        }
    }

}
