package com.bcsd.bcsd_week3

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bcsd.bcsd_week3.databinding.ActivityWeek9Binding
import com.bcsd.bcsd_week3.databinding.ItemMusicBinding

class Week9Activity : AppCompatActivity() {

    private lateinit var binding: ActivityWeek9Binding
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeek9Binding.inflate(layoutInflater)
        setContentView(binding.root)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_AUDIO
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_MEDIA_AUDIO),
                0
            )
        }
        val musicList = mutableListOf<Music>()
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DATA
        )

        val cursor = contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )

        if (cursor != null) {
            Log.d("dddddddd", "1")
            while (cursor.moveToNext()) {
                Log.d("dddddddd", "2")
                val title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                val artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                val data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))

                musicList.add(Music(title, artist, data))
                Log.d("Music", "Title: $title, Artist: $artist, Path: $data")
            }
            cursor.close()
        }

        binding.recyclerViewMusic.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewMusic.adapter = MusicAdapter(musicList)

    }
}

data class Music(val title: String, val artist: String, val playtime: String) {

}

class MusicAdapter(private val musicList: MutableList<Music>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MusicViewHolder(
            ItemMusicBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = musicList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MusicViewHolder).binding.textMusicTitle.text = musicList[position].title
        holder.binding.textMusicArtist.text = musicList[position].artist
        holder.binding.textMusicPlaytime.text = musicList[position].playtime.toString()
    }

    inner class MusicViewHolder(val binding: ItemMusicBinding) :
        RecyclerView.ViewHolder(binding.root)
}
