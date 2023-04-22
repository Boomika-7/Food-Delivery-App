package com.example.myapplication.mika.adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.mika.domain.FoodDomain;
import com.example.myapplication.mika.helper.ManagementCart;
import com.example.myapplication.mika.interFACE.ChangeNumberItemsListener;
import java.util.ArrayList;

public class CartAdaptor extends RecyclerView.Adapter<CartAdaptor.ViewHolder>{
    private ArrayList<FoodDomain> foodDomains;
    private ManagementCart managementCart;
    private ChangeNumberItemsListener changeNumberItemsListener;

    public CartAdaptor(ArrayList<FoodDomain> foodDomains, Context context, ChangeNumberItemsListener changeNumberItemsListener){
        this.foodDomains=foodDomains;
        this.managementCart=new ManagementCart(context);
        this.changeNumberItemsListener=changeNumberItemsListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdaptor.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(foodDomains.get(position).getTitle());
        holder.feeEachItem.setText(String.valueOf(foodDomains.get(position).getFee()));
        holder.totalEachItem.setText(String.valueOf(Math.round((foodDomains.get(position).getNumberInCart()* foodDomains.get(position).getFee())*100)/100));
        holder.num.setText(String.valueOf(foodDomains.get(position).getNumberInCart()));

        int drawableResourceId=holder.itemView.getContext().getResources().getIdentifier(foodDomains.get(position).getPic(),"drawable",holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.pic);
        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managementCart.plusNumberFood(foodDomains,position,new ChangeNumberItemsListener(){
                    @Override
                    public void changed(){
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });
        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managementCart.minusNumberFood(foodDomains,position,new ChangeNumberItemsListener(){
                    @Override
                    public void changed(){
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,feeEachItem;
        ImageView pic,plusItem,minusItem;
        TextView totalEachItem,num;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            title=itemView.findViewById(R.id.titleTxt);
            feeEachItem=itemView.findViewById(R.id.feeEachItem);
            totalEachItem=itemView.findViewById(R.id.totalEachItem);
            pic=itemView.findViewById(R.id.picCart);
            num=itemView.findViewById(R.id.numberItemTxt);
            plusItem=itemView.findViewById(R.id.plusCartBtn);
            minusItem=itemView.findViewById(R.id.minusCartBtn);
        }
    }
}
