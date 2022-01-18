package com.android.moscow4D.fragments.map.subUI

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.moscow4D.CacheEngine
import androidx.fragment.app.FragmentManager
import com.android.moscow4D.R
import com.android.moscow4D.database.PlaceEntity
import com.android.moscow4D.fragments.map.MapController
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.os.Handler
import android.os.Looper

class BottomSheetFragment(mapController: MapController) : BottomSheetDialogFragment() {
    /**
     * This class represents bottom sheet xml and its logic
     * */

    private val mController = mapController

    private val player: MediaPlayer = MediaPlayer.create(mController.context, R.raw.music)

    private lateinit var place_name: TextView
    private lateinit var place_image: ImageView
    private lateinit var place_description: TextView
    private lateinit var route_button: Button
    private lateinit var play_button: ImageButton
    private lateinit var seek_bar: SeekBar

    private lateinit var runnable: Runnable
    private var handler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottomsheet_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        place_name = view.findViewById<TextView>(R.id.bottom_sheet_title)  // Get name TextView.
        place_image = view.findViewById<ImageView>(R.id.bottom_sheet_image)  // Get ImageView.
        place_description = view.findViewById<TextView>(R.id.bottom_sheet_description)  // Get description TextView.

        seek_bar = view.findViewById<SeekBar>(R.id.bar)

        route_button = view.findViewById<Button>(R.id.create_route_button2)
        play_button = view.findViewById<ImageButton>(R.id.play_button)

        seek_bar.progress = 0
        seek_bar.max = player.duration

        val pos: Int = 0  // Get position in CacheEngine data.

        place_name.text = arguments?.getParcelable<PlaceEntity>("place")?.place_name  // Set place name from CacheEngine data.
        place_description.text = arguments?.getParcelable<PlaceEntity>("place")?.place_name  // Set description.
        place_image.setImageResource(CacheEngine.data[pos]["Image"]!!.toInt())  // Set image.

        route_button.setOnClickListener{
            buildRoute()
        }

        play_button.setOnClickListener{
            if(!player.isPlaying){
                player.start()
                play_button.setImageResource(R.drawable.ic_pause)
            }else{
                player.pause()
                play_button.setImageResource(R.drawable.play_arrow)
            }
        }

        seek_bar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, pos: Int, changed: Boolean) {
                if(changed){
                    player.seekTo(pos)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        runnable = Runnable {
            seek_bar.progress = player.currentPosition

            handler.postDelayed(runnable, 1000)
        }

        handler.postDelayed(runnable, 1000)

        player.setOnCompletionListener {
            play_button.setImageResource(R.drawable.play_arrow)
            seek_bar.progress = 0
        }
    }

    private fun buildRoute(){
        mController.buildRouteFromMyLocation(LatLng(arguments?.getParcelable<PlaceEntity>("place")?.place_lat!!.toDouble(),
            arguments?.getParcelable<PlaceEntity>("place")?.place_lng!!.toDouble()))
    }
}