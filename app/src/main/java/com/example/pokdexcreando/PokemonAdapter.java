package com.example.pokdexcreando;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pokdexcreando.models.Pokemon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {
    private static final String TAG = "POKEDEX";
    private List<Pokemon> dataList = new ArrayList<>();
    private Context context;
//test profile
    private OnItemClickListener mListener;
    public interface OnItemClickListener{
              void onItemClcik(int position);

    }
public   void setOnItemClickListener(OnItemClickListener listener){
        mListener= listener;
}
    //end
    public PokemonAdapter(Context context) {
        this.dataList =new ArrayList<>() ;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon p = dataList.get(position);
        holder.nombreTextView.setText(p.getName());
        Glide.with(context).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+p.getNumber()+".png").centerCrop().crossFade().diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.photoImageView);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void adListPokemon(ArrayList<Pokemon> listpokemon) {
        this.dataList=listpokemon;
        //Log.d(TAG,"ben"+dataset.get(0));
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
         ImageView photoImageView;
         TextView nombreTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //set onlick
               itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Log.e(TAG,"hello");
                       if( mListener != null){
                       int position = getAdapterPosition();
                       Log.e(TAG, String.valueOf(position)+"mama");
                       if(position != RecyclerView.NO_POSITION){
                          mListener.onItemClcik(position);
                       //    Log.e(TAG, String.valueOf(position));

                       }

                       }
                       //start new intent
                   }
               });

            //end
            photoImageView = itemView.findViewById(R.id.photoImageView);
            nombreTextView = itemView.findViewById(R.id.nombreTextView);

        }
    }
}
