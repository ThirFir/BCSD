package com.bcsd.bcsd_week3.week10

import android.Manifest
import android.content.ContentUris
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bcsd.bcsd_week3.databinding.ActivityWeek10Binding
import com.bcsd.bcsd_week3.databinding.ItemMusicBinding
import java.text.SimpleDateFormat

class Week10Activity : AppCompatActivity() {

    private lateinit var binding: ActivityWeek10Binding
    private var mediaPlayer: MediaPlayer? = MediaPlayer()
    private val onPlayIconClickListener = OnClickListener {
        play()
    }
    private val onPauseIconClickListener = OnClickListener {
        pause()
    }
    private val onResetIconClickListener = OnClickListener {
        reset()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeek10Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val batteryReceiver = BatteryReceiver()
        registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        checkPermission()
        val musicList = getMusicList()
        binding.run {
            recyclerViewMusic.layoutManager = LinearLayoutManager(this@Week10Activity)
            recyclerViewMusic.adapter = MusicAdapter(musicList) {
                onMusicSelectedListener(it)
            }

            iconPlay.setOnClickListener(onPlayIconClickListener)
            iconPause.setOnClickListener(onPauseIconClickListener)
            iconReset.setOnClickListener(onResetIconClickListener)
        }
    }

    private fun startMusicForegroundService(title: String, artist: String) {
        val musicServiceIntent = Intent(this, MusicForegroundService::class.java).apply {
            putExtra("title", title)
            putExtra("artist", artist)
            action = "start"
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(musicServiceIntent)
        } else {
            startService(musicServiceIntent)
        }
    }

    private fun stopMusicForegroundService() {
        val musicServiceIntent = Intent(this, MusicForegroundService::class.java).apply {
            action = "stop"
        }
        stopService(musicServiceIntent)
    }

    private fun onMusicSelectedListener(music: Music) {
        if (mediaPlayer?.isPlaying == true)
            mediaPlayer?.reset()
        mediaPlayer?.setDataSource(this@Week10Activity, music.uri)
        mediaPlayer?.setOnPreparedListener {
            play()
            binding.run {
                textViewTitle.text = music.title
                textViewArtist.text = music.artist
            }
            startMusicForegroundService(music.title, music.artist)
        }
        mediaPlayer?.setOnCompletionListener {
            binding.run {
                iconPlay.visibility = View.VISIBLE
                iconPause.visibility = View.GONE
            }
        }
        mediaPlayer?.prepareAsync()
    }

    private fun play() {
        if (mediaPlayer?.isPlaying == false) {
            binding.run {
                iconPlay.visibility = View.GONE
                iconPause.visibility = View.VISIBLE
            }
            mediaPlayer?.start()
        }
    }

    private fun pause() {
        if (mediaPlayer?.isPlaying == true) {
            binding.run {
                iconPause.visibility = View.GONE
                iconPlay.visibility = View.VISIBLE
            }
            mediaPlayer?.pause()
        }
    }

    private fun reset() {
        if (mediaPlayer?.isPlaying == true) {
            binding.run {
                iconPause.visibility = View.GONE
                iconPlay.visibility = View.VISIBLE
            }
            mediaPlayer?.reset()
        }
        stopMusicForegroundService()
    }

    private fun checkPermission() {
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

        if(ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    0
            )
        }

        if(ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.FOREGROUND_SERVICE
        ) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.FOREGROUND_SERVICE),
                    0
            )
        }

    }

    private fun getMusicList(): MutableList<Music> {
        val musicList = mutableListOf<Music>()
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
        )

        val cursor = contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                val title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                val artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                val playtime =
                    cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))

                val uri =
                    ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)
                Log.d(
                    "Music",
                    "id: $id, title: $title, artist: $artist, playtime: $playtime, uri: $uri"
                )
                musicList.add(Music(title, artist, playtime, uri))
            }
            cursor.close()
        }
        return musicList
    }

    override fun onPause() {
        super.onPause()
    }


    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}

private data class Music(val title: String, val artist: String, val playtime: Long, val uri: Uri)

private class MusicAdapter(
    private val musicList: MutableList<Music>,
    private val onMusicClickListener: (Music) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
        holder.binding.textMusicPlaytime.text = musicPlaytimeFormat(musicList[position].playtime)

        holder.binding.root.setOnClickListener {
            onMusicClickListener(musicList[position])
        }
    }

    inner class MusicViewHolder(val binding: ItemMusicBinding) :
        RecyclerView.ViewHolder(binding.root)
}


private fun musicPlaytimeFormat(playtime: Long): String = SimpleDateFormat("mm:ss").format(playtime)
