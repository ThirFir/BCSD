package com.bcsd.bcsd_week3

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.bcsd.bcsd_week3.databinding.FragmentWeek5MainBinding


class Week5MainFragment : Fragment() {
    private lateinit var binding: FragmentWeek5MainBinding
    private val registerForPermissionActivityResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeek5MainBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textCountNumber.text = "0"
        var count = 0

        binding.buttonToast.setOnClickListener {
            //Toast.makeText(this@Week4Activity, "Toast : ${binding.textCountNumber.text}", Toast.LENGTH_SHORT).show()
            AlertDialog.Builder(requireContext()).setMessage(count.toString())
                .setPositiveButton("OK") { _, _ ->
                    count = 0; binding.textCountNumber.text = "0"
                }
                .setNegativeButton("Cancel", null)
                .setNeutralButton("Neutral") { _, _ ->
                    Toast.makeText(requireContext(), "NEUTRAL", Toast.LENGTH_SHORT).show()
                }.show()
        }
        binding.buttonCount.setOnClickListener {
            ++count
            binding.textCountNumber.text = count.toString()
        }

        val notificationIntent = Intent(requireActivity(), Week4RandomActivity::class.java)


        binding.buttonRandom.setOnClickListener {
            val notificationPendingIntent = PendingIntent
                .getActivity(
                    requireActivity(),
                    NOTIFICATION_ID,
                    notificationIntent.apply {
                        putExtra("count", count)
                    },
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

            val channelId = getString(R.string.channel_id)
            val channelName = getString(R.string.channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)
            val builder = NotificationCompat.Builder(requireContext(), channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Random Number")
                .setContentText("What is the random number?????")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)

            val notificationMangerCompat = NotificationManagerCompat.from(requireContext())
            notificationMangerCompat.createNotificationChannel(channel)
            try {
                notificationMangerCompat.notify(NOTIFICATION_ID, builder.build())
            } catch (e: SecurityException) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_NOTIFICATION_POLICY),
                    0
                )
            }

        }

        /*requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.layout_week5_activity, Week5Fragment().apply {
                arguments = bundleOf("count" to count)
            }, "tag").addToBackStack("tag").commit()*/
    }
}


const val NOTIFICATION_ID = 0