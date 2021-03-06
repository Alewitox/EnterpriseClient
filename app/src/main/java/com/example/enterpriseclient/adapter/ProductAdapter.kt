package com.example.enterpriseclient.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.enterpriseclient.R
import com.example.enterpriseclient.ReservationActivity
import com.example.enterpriseclient.model.Product

class ProductAdapter (private val mContext: Context, private val mData: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {

    var option: RequestOptions

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View
        val inflater = LayoutInflater.from(mContext)
        view = inflater.inflate(R.layout.fragment_home, parent, false)
        val viewHolder =
            MyViewHolder(
                view
            )
        return MyViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.Name.text = mData[position].name
        holder.Description.text = mData[position].description


        holder.linearHome.setOnClickListener{
            val intent = Intent(mContext, ReservationActivity::class.java)
            mContext.startActivity(intent)
        }

        // Load Image from the internet and set it into Imageview using Glide
        Glide.with(mContext).load(mData[position].image_url).apply(option)
            .into(holder.imgThumbnail)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var Name: TextView
        var Description: TextView
        var imgThumbnail: ImageView
        lateinit var linearHome: LinearLayout

        init {
            Name = itemView.findViewById(R.id.productName)
            Description = itemView.findViewById(R.id.productDescription)
            imgThumbnail = itemView.findViewById(R.id.thumbnail)
            linearHome = itemView.findViewById(R.id.linearHome)

        }
    }

    init {
        // Request option for Glide
        option = RequestOptions().centerCrop().placeholder(R.drawable.loading_shape)
            .error(R.drawable.loading_shape)
    }
}


