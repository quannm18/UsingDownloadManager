package com.quannm18.myapplication

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


class MainActivity : AppCompatActivity() {
    private var downloadID:Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button: Button by lazy { findViewById<Button>(R.id.button) }

        button.setOnClickListener(View.OnClickListener {
            haveStoragePermission()
        })

    }

    fun downloadVideo(url: String?) {
        val Download_Uri: Uri = Uri.parse(url)
        val request = DownloadManager.Request(Download_Uri)

        //Restrict the types of networks over which this download may proceed.
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        //Set whether this download may proceed over a roaming connection.
        request.setAllowedOverRoaming(false)
        // Visibility of the download Notification
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        //Set the title of this download, to be displayed in notifications (if enabled).
        request.setTitle("Downloading")
        //Set a description of this download, to be displayed in notifications (if enabled)
        request.setDescription("Downloading File")

        //Set the local destination for the downloaded file to a path within the application's external files directory
        /*request.setDestinationInExternalFilesDir(MainActivity.this, Environment.DIRECTORY_MOVIES, "Shivam196.mp4");*/ //For private destination

        //Set the local destination for the downloaded file to a path within the application's external files directory
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_MOVIES,
            "Shivam196.mp4"
        ) // for public destination
        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID =
            downloadManager.enqueue(request) // enqueue puts the download request in the queue.
    }
    fun downloadAgain(url: String?){
        val downloadmanager : DownloadManager =
            getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager;
        val uri = Uri.parse(url);

        val request : DownloadManager.Request =  DownloadManager.Request(uri);
        request.setTitle("My File");
        request.setDescription("Downloading");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setVisibleInDownloadsUi(false);
        request.setDestinationUri(Uri.parse("file://" + "quannm" + "/myfile.mp4"));

        downloadmanager.enqueue(request);
    }
    fun haveStoragePermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                Log.e("Permission error", "You have permission")
                downloadVideo("https://v16m.byteicdn.com/64227d571fcad5a041d9152d034b95c1/62ac0a70/video/tos/useast2a/tos-useast2a-ve-0068c004/7809159db8f44adea6e142bb33205614/?a=0&ch=0&cr=0&dr=0&lr=all&cd=0%7C0%7C0%7C0&cv=1&br=3000&bt=1500&btag=80000&cs=0&ds=6&ft=JgiFcDHhNF6VQZqG0_G-IrGC~TGWoLmApmqXKxS6B&mime_type=video_mp4&qs=0&rc=ZDo1PDc0NTY0Z2c0ZzdmZ0BpMzw2amg6Zjp3ZDMzNzczM0AyLmBjXy1iNTUxYF5fLTY2YSNqM2UvcjQwajBgLS1kMTZzcw%3D%3D&l=202206162300110102230801681B4C6AC0&cc=10")

                true
            } else {
                Log.e("Permission error", "You have asked for permission")
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
                false
            }
        } else { //you dont need to worry about these stuff below api level 23
            Log.e("Permission error", "You already have the permission")
            true
        }
    }
}