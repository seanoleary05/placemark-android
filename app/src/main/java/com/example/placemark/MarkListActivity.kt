package com.example.placemark

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber


class MarkListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PlacedMarkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_mark_list)

        recyclerView =
            findViewById(R.id.marksRecyclerView)


        adapter = PlacedMarkAdapter(

            marks = AppData.placedMarks.findAll(),

            listener = object : OnMarkListener {



                override fun onMarkClick(mark: PlacedMarkData) {

                    editMark(mark)

                }



                override fun onMarkLongClick(mark: PlacedMarkData): Boolean {

                    Timber.d("Long press on mark id=${mark.id}, title=${mark.title}")

                    confirmDelete(mark)

                    return true

                }

            }

        )

        recyclerView.layoutManager =
            LinearLayoutManager(this)

        recyclerView.adapter = adapter

        val returnButton =
            findViewById<Button>(R.id.returnButton)

        returnButton.setOnClickListener {

            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }
    }

    override fun onResume() {
        super.onResume()

        if (::adapter.isInitialized) {

            adapter.updateMarks(
                AppData.placedMarks.findAll()
            )
        }
    }

    private fun editMark(mark: PlacedMarkData) {

        val intent =
            Intent(this, AddEditActivity::class.java)

        intent.putExtra("id", mark.id)

        startActivity(intent)
    }

    private fun confirmDelete(mark: PlacedMarkData) {

        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Delete mark?")
            .setMessage("\"${mark.title}\" will be permanently deleted.")
            .setPositiveButton("Delete") { _, _ ->
                deleteMark(mark)
            }
            .setNegativeButton("Cancel") { _, _ ->
                Timber.d("Delete cancelled for mark id=${mark.id}")
            }
            .show()
    }

    private fun deleteMark(mark: PlacedMarkData) {

        Timber.i("Deleting mark id=${mark.id}, title=${mark.title}")

        AppData.placedMarks.delete(mark.id)

        adapter.updateMarks(
            AppData.placedMarks.findAll()
        )
    }
}
