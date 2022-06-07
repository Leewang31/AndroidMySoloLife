package com.example.mysololife.board

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.mysololife.R
import com.example.mysololife.cotentsList.bookmarkModel
import com.example.mysololife.databinding.ActivityBoardWriteBinding
import com.example.mysololife.databinding.FragmentTalkBinding
import com.example.mysololife.utils.FBAuth
import com.example.mysololife.utils.FBRef
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class BoardWriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBoardWriteBinding

    private val TAG = BoardWriteActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_board_write)

        binding.writeBtn.setOnClickListener {

            val title = binding.titleArea.text.toString()
            val content = binding.contentArea.text.toString()
            val uid = FBAuth.getUid()
            val time = FBAuth.getTime()

            Log.d(TAG,title)
            Log.d(TAG,content)
//          파이어 베이스 스토리지에 이미지를 저장하고 싶스비낟.

//          만약 내가 게시글을 클릭헸을 때, 게시글에 대한 정보를 받아와야 하는데
            val key = FBRef.boardRef.push().key.toString()

            FBRef.boardRef
                .child(key)
                .setValue(BoardModel(title,content, uid,time))

            Toast.makeText(this,"게시글입력완료",Toast.LENGTH_SHORT).show()
            imageUpload()
            finish()

        }
        binding.imageArea.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery,100)
        }

    }

    private fun imageUpload(){
        val storage = Firebase.storage
        val storageRef = storage.reference
        val mountainsRef = storageRef.child("mountains.jpg")

        val imageView = binding.imageArea
        // Get the data from an ImageView as bytes
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK&&requestCode==100){
            binding.imageArea.setImageURI(data?.data)
        }
    }
}