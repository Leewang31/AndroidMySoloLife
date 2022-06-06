package com.example.mysololife.cotentsList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mysololife.R
import com.example.mysololife.utils.FBAuth
import com.example.mysololife.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ContentsListActivity : AppCompatActivity() {

    lateinit var myRef: DatabaseReference

    val bookmarkIdList = mutableListOf<String>()

    lateinit var rvAdapter: ContentsRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contents_list)

        val items = ArrayList<ContentModel>()
        val itemKeyList = ArrayList<String>()
        rvAdapter = ContentsRVAdapter(baseContext, items, itemKeyList,bookmarkIdList);

        val database = Firebase.database

//    tipFragment 에서구분
        val category = intent.getStringExtra("category")

        if (category.equals("category1")) {
            myRef = database.getReference("contents1")

        } else if (category.equals("category2")) {
            myRef = database.getReference("contents2")
        } else if (category.equals("category3")) {
            myRef = database.getReference("contents3")
        }


//            realtimeDataBase 에서 값을 가져옴
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataModel in dataSnapshot.children) {
                    Log.d("ContentListActivity", dataModel.toString())
                    Log.d("ContentListActivity", dataModel.key.toString())
                    val item = dataModel.getValue(ContentModel::class.java)
                    items.add(item!!)
                    itemKeyList.add(dataModel.key.toString())
                }
//                동기화
                rvAdapter.notifyDataSetChanged()
                Log.d("ContentListActivity", items.toString())

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("ContentListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }

        myRef.addValueEventListener(postListener)

        val rv: RecyclerView = findViewById(R.id.rv)

        rv.adapter = rvAdapter

        rv.layoutManager = GridLayoutManager(this, 2)

        getBookmarkData()

    }
    private fun getBookmarkData(){

        //realtimeDataBase 에서 값을 가져옴

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                bookmarkIdList.clear()

                for (dataModel in dataSnapshot.children) {
                    bookmarkIdList.add(dataModel.key.toString())
                }
                Log.d("ContentListActivity : bookmark : ",bookmarkIdList.toString())
                rvAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("ContentListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }

        FBRef.bookmarkRef.child(FBAuth.getUid()).addValueEventListener(postListener)



    }
}


