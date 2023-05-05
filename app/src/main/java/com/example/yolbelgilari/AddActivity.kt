package com.example.yolbelgilari

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.yolbelgilari.databinding.ActivityAddBinding
import com.example.yolbelgilari.db.Constants
import com.example.yolbelgilari.db.MyDbHelper
import com.example.yolbelgilari.models.MySign
import java.io.File
import java.io.FileOutputStream

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var lastSignDb: MySign
    private lateinit var photoUri: Uri
    private lateinit var sign: MySign
    private lateinit var name: String
    private lateinit var photoPath: String
    private lateinit var about: String
    var databaseState = false
    var typeState = false
    var isEdit = false
    var typePosition = 0
    var photoSelectedState = false
    private var imageFileName = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myDefault()
        myDbHelper = MyDbHelper(this)
        if (myDbHelper.getAllSign().isEmpty()) {
            databaseState = true
        } else {
            lastSignDb = myDbHelper.getAllSign().last()
        }

        checkEdit()

        binding.imageSign.setOnClickListener {
            getImageContent.launch("image/*")
        }

        binding.btnSave.setOnClickListener {
            name = binding.edtName.text.toString().trim()
            about = binding.edtAbout.text.toString().trim()

            if (isEdit) {
                editSign()
            } else {
                addSign()
            }

        }
    }

    private fun myDefault() {
        val adapter = ArrayAdapter(
            this,
            androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
            Constants.TYPE_ARRAY
        )
        binding.apply {
            toolBar.setNavigationOnClickListener {
                finish()
            }
            spinnerType.setAdapter(adapter)

            spinnerType.setOnItemClickListener { _, _, i, _ ->
                typePosition = i
                typeState = true

            }
        }
    }

    private fun addSign() {
        if (typeState && name.isNotEmpty() && photoSelectedState) {
            savePhoto()
            sign = MySign(name, about, typePosition, 0, photoPath)
            myDbHelper.addSign(sign)
            Toast.makeText(this, "Saqlandi", Toast.LENGTH_SHORT).show()
            Constants.VIEW_PAGER_ITEM_POSITION = sign.type!!
            finish()
        } else {
            Toast.makeText(this, "Ro'yxat bo'sh", Toast.LENGTH_SHORT).show()
        }
    }


    private fun editSign() {
        if (name.isNotEmpty()) {
            if (photoSelectedState) {
                val inputStream = contentResolver?.openInputStream(photoUri)
                val file = File(filesDir, "${sign.id}.jpg")
                photoPath = file.absolutePath
                val fileOutputStream = FileOutputStream(file)
                inputStream?.copyTo(fileOutputStream)
                inputStream?.close()
                fileOutputStream.close()

                sign.photoPath = photoPath
            }
            sign.name = name
            sign.about = about
            sign.type = typePosition

            myDbHelper.editSign(sign)
            Constants.SIGN_EDITED = true
            Constants.TEMP_SIGN = sign
            Toast.makeText(this, "Saqlandi", Toast.LENGTH_SHORT).show()
            Constants.VIEW_PAGER_ITEM_POSITION = sign.type!!
            finish()
        }
    }

    private fun savePhoto() {
        imageFileName = if (databaseState) {
            1
        } else {
            lastSignDb.id!! + 1
        }
        val inputStream = contentResolver.openInputStream(photoUri)
        val file = File(filesDir, "$imageFileName.jpg")
        photoPath = file.absolutePath
        val fileOutputStream = FileOutputStream(file)
        inputStream?.copyTo(fileOutputStream)
        inputStream?.close()
        fileOutputStream.close()
    }

    private fun checkEdit() {
        isEdit = intent.getBooleanExtra("isEdit", false)
        if (isEdit) {
            sign = intent.getSerializableExtra("sign") as MySign
            binding.apply {
                edtName.setText(sign.name)
                edtAbout.setText(sign.about)
                spinnerType.setText(Constants.TYPE_ARRAY[sign.type!!])
                imageSign.setImageURI(Uri.parse(sign.photoPath))
                typePosition = sign.type!!
                val adapter2 = ArrayAdapter<String>(
                    this@AddActivity,
                    com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                    Constants.TYPE_ARRAY
                )
                spinnerType.setAdapter(adapter2)
            }
        }
    }

    private val getImageContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        it ?: return@registerForActivityResult
        binding.imageSign.setImageURI(it)
        photoUri = it
        photoSelectedState = true
    }
}