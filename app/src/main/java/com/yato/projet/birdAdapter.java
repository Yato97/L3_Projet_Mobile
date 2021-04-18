package com.yato.projet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yato.projet.Bird;

import java.util.List;

public class birdAdapter extends BaseAdapter {
    //-----------------------Variables--------------------------//
    private Context context;
    private List<Bird> bird;
    private LayoutInflater inflater;
    private Animation animation;
    //-----------------------Variables--------------------------//

    //-----------------------CONSTRUCTEUR-----------------------//
    public birdAdapter(Context context, List<Bird> bird) {
        this.context = context;
        this.bird = bird;
        this.inflater = LayoutInflater.from(context);
    }
    //-----------------------CONSTRUCTEUR-----------------------//

    @Override
    public int getCount() {
        return bird.size();
    }

    @Override
    public Bird getItem(int position) {
        return bird.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //-------------------------ATTIRBUE-------------------------//
        //----------------------------------------------------------//
        convertView = inflater.inflate(R.layout.adapteur, null);
        Bird currentBird = getItem(position); //On récupére la position du Bird en cour
        String itemName = currentBird.getNom(); //On récupére l'attribue nom du Bird en cour
        String memonic =  currentBird.getMemonic(); //Id pour selectioner les photos correspondantes
        String carte = currentBird.getCarte(); //On récupére la carte associé
        int itemVie = currentBird.getVie(); //On récupére l'attribue vie du Bird en cour
        int itemScore = currentBird.getScore(); //On récupére le score
        int meilleurescore =  currentBird.getMeilleurescore(); //Le meilleure score

        String ressource = "ally_"+memonic; // Memonic pour chemin d'accés
        int resId = context.getResources().getIdentifier(ressource, "drawable", context.getPackageName());

        animation = AnimationUtils.loadAnimation(context,R.anim.loopzoom);
        //----------------------------------------------------------//
        //-------------------------ATTIRBUE-------------------------//


        //-------------------------LAYOUT---------------------------//
        //----------------------------------------------------------//
        ImageView itemView = convertView.findViewById(R.id.perso); //ImageView
        TextView itemNameView = convertView.findViewById(R.id.persoNom); //On récupére le label d'id persoNom pour y acceder en ecriture
        TextView viewScore = convertView.findViewById(R.id.persoDescription); //Méme chose pour la vie
        TextView itemMap = convertView.findViewById(R.id.persoCarte);
        itemView.setAnimation(animation);
        itemView.setImageResource(resId);
        itemNameView.setText(itemName);
        viewScore.setText("Meilleure score : "+meilleurescore);
        itemMap.setText(carte);
        //----------------------------------------------------------//
        //-------------------------LAYOUT---------------------------//

        return convertView;
    }
}
