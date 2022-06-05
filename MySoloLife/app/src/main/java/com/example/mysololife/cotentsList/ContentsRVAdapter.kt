package com.example.mysololife.cotentsList

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mysololife.R

//아이템을 가져오고
class ContentsRVAdapter(val context : Context, val items: ArrayList<ContentModel>) :
    RecyclerView.Adapter<ContentsRVAdapter.Viewholder>() {

    interface ItemClick{
        fun onClick(view : View , position :Int)
    }
    var itemClick : ItemClick? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContentsRVAdapter.Viewholder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.contents_rv_item, parent, false)
        return Viewholder(v)
    }

    //아이템을 넣을수 있게함
    override fun onBindViewHolder(holder: ContentsRVAdapter.Viewholder, position: Int) {

        if(itemClick != null){
            holder.itemView.setOnClickListener{ v->
                itemClick?.onClick(v,position)
            }
        }
        holder.bindItems(items[position])
    }

    //아이템 개수
    override fun getItemCount(): Int {
        return items.size
    }



    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(item: ContentModel) {

            val contentTitle = itemView.findViewById<TextView>(R.id.textArea)
            val imageViewArea = itemView.findViewById<ImageView>(R.id.imageArea)
            contentTitle.text = item.title

            Glide.with(context)
                .load(item.imageUrl)
                .into(imageViewArea)

        }
    }
}