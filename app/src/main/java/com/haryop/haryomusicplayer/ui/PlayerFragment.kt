package com.haryop.haryomusicplayer.ui

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.haryop.haryomusicplayer.databinding.FragmentPlayerBinding
import com.haryop.synpulsefrontendchallenge.utils.BaseFragmentBinding


class PlayerFragment : BaseFragmentBinding<FragmentPlayerBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPlayerBinding
        get() = FragmentPlayerBinding::inflate

    var media_url = ""
    var artist_name = ""
    var track_name = ""
    lateinit var viewbinding: FragmentPlayerBinding
    override fun setupView(binding: FragmentPlayerBinding) {
        viewbinding = binding
        arguments?.getString("media_url")?.let {
            media_url = it
        }
        arguments?.getString("artist_name")?.let {
            artist_name = it
        }
        arguments?.getString("track_name")?.let {
            track_name = it
        }

        binding.progressBar.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF.toInt(), android.graphics.PorterDuff.Mode.MULTIPLY);
        setUpTitle()
        setUpPlayer()
    }

    fun setUpTitle() = with(viewbinding) {
        titleField.text = "${artist_name} - ${track_name}"

        titleField.ellipsize = TextUtils.TruncateAt.MARQUEE
        titleField.isSelected = true
        titleField.marqueeRepeatLimit = -1
    }

    lateinit var player: SimpleExoPlayer
    fun setUpPlayer() = with(viewbinding) {
        player = SimpleExoPlayer.Builder(requireContext()).build()
        epVideoView.player = player

        val mediaItem: MediaItem = MediaItem.fromUri(media_url)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()

        player.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == Player.STATE_BUFFERING) {
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.GONE
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        player.stop()
        player.release()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
        player.release()
    }

}