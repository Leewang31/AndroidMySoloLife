package com.example.mysololife.cotentsList

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mysololife.R
import com.example.mysololife.utils.FBAuth
import com.example.mysololife.utils.FBRef

//아이템을 가져오고
class BookmarkRVAdapter(
    val context: Context,
    val items: ArrayList<ContentModel>,
    val keyList: ArrayList<String>,
    val bookmarkIdList: MutableList<String>
) :
    RecyclerView.Adapter<BookmarkRVAdapter.Viewholder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookmarkRVAdapter.Viewholder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.contents_rv_item, parent, false)

        Log.d("BookmarkRVAdapter", keyList.toString())
        Log.d("BookmarkRVAdapter", bookmarkIdList.toString())
        return Viewholder(v)
    }

    //아이템을 넣을수 있게함
    override fun onBindViewHolder(holder: BookmarkRVAdapter.Viewholder, position: Int) {

        holder.bindItems(items[position], keyList[position])
    }

    //아이템 개수
    override fun getItemCount(): Int {
        return items.size
    }


    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(item: ContentModel, key: String) {

            itemView.setOnClickListener {
                Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()
                val intent = Intent(context, ContentShowActivity::class.java)
                intent.putExtra("url", item.webUrl)
                itemView.context.startActivity(intent)
            }

            val contentTitle = itemView.findViewById<TextView>(R.id.textArea)
            val imageViewArea = itemView.findViewById<ImageView>(R.id.imageArea)
            val bookmarkArea = itemView.findViewById<ImageView>(R.id.bookmarkArea)

            if (bookmarkIdList.contains(key)) {
                bookmarkArea.setImageResource(R.drawable.bookmark_color)
            } else {
                bookmarkArea.setImageResource(R.drawable.bookmark_white)
            }


            contentTitle.text = item.title

            Glide.with(context)
                .load(item.imageUrl)
                .into(imageViewArea)

        }
    }
}