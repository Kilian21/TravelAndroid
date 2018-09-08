package com.example.markusbink.travelapp.Routeplaner;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.markusbink.travelapp.R;
import java.util.ArrayList;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

    private ArrayList<RoutePlaner_EntityOne> routePlanerEntityOne;
    private ArrayList<RoutePlaner_EntityTwo> routePlanerEntityTwo;
    private ArrayList<RoutePlaner_EntityThree> routePlanerEntityThree;


    private int extra1,extra2,extra3;


    public RvAdapter(ArrayList<RoutePlaner_EntityOne> routePlanerEntityOne, ArrayList<RoutePlaner_EntityTwo> routePlanerEntityTwo, ArrayList<RoutePlaner_EntityThree> routePlanerEntityThree, int extra1, int extra2, int extra3){
        this.routePlanerEntityOne = routePlanerEntityOne;
        this.routePlanerEntityTwo = routePlanerEntityTwo;
        this.routePlanerEntityThree = routePlanerEntityThree;
        this.extra1 = extra1;
        this.extra2 = extra2;
        this.extra3 = extra3;
    }





    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView elementArrowDown;
        TextView elementTransportation;
        TextView elementDestination;

        public ViewHolder(View itemView) {

            super(itemView); // Gibt die Elemente an die onBindViewHolder - Methode weiter
            elementArrowDown = itemView.findViewById(R.id.imageView);
            elementTransportation = itemView.findViewById(R.id.transportation_show);
            elementDestination = itemView.findViewById(R.id.destination_show);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View singleElement = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.singleitem_routeplaner, null);

        return new ViewHolder(singleElement);
        //Wird an den Konstruktor der innere Klasse übergeben, um dort die Layouts zuzuweisen (Beides ein "View")
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {


        if (extra1 == 1 && extra2 == 0 && extra3 == 0 ){

            viewHolder.elementDestination.setText(routePlanerEntityOne.get(position).getDestination());
            viewHolder.elementTransportation.setText(routePlanerEntityOne.get(position).getTransportation());

        }else if (extra2 == 2 && extra1 == 0 && extra3 == 0){

            viewHolder.elementDestination.setText(routePlanerEntityTwo.get(position).getDestination2());
            viewHolder.elementTransportation.setText(routePlanerEntityTwo.get(position).getTransportation2());

        }else if (extra3 == 3 && extra1 == 0 && extra2 == 0){

            viewHolder.elementDestination.setText(routePlanerEntityThree.get(position).getDestination3());
            viewHolder.elementTransportation.setText(routePlanerEntityThree.get(position).getTransportation3());

        }


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                if (extra1 == 1 && extra2 == 0 && extra3 == 0 ){

                    routePlanerEntityOne.remove(position);
                    notifyDataSetChanged();

                }else if (extra2 == 2 && extra1 == 0 && extra3 == 0){

                    routePlanerEntityTwo.remove(position);
                    notifyDataSetChanged();

                }else if (extra3 == 3 && extra1 == 0 && extra2 == 0){

                    routePlanerEntityThree.remove(position);
                    notifyDataSetChanged();
                }


                return false;
            }
        });





    }

    @Override
    public int getItemCount() {


        if (extra1 == 1 && extra2 == 0 && extra3 == 0 ){

            return routePlanerEntityOne.size();

        }else if (extra2 == 2 && extra1 == 0 && extra3 == 0) {

            return routePlanerEntityTwo.size();

        }else if (extra3 == 3 && extra1 == 0 && extra2 == 0){

            return routePlanerEntityThree.size();
        }
        return getItemCount();

    }
}
