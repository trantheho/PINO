package com.example.loginpino.tutorial.video

import android.os.Build
import android.os.Bundle
import android.util.Log
import com.example.loginpino.utils.AppUtil
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment

class FragamentYoutube : YouTubePlayerSupportFragment(), YouTubePlayer.OnInitializedListener {

    companion object {
    private val TAG = FragamentYoutube::class.java!!.getSimpleName()
    private val KEY_VIDEO_ID = "KEY_VIDEO_ID"
    fun newInstance(video_id: String): FragamentYoutube {
        val youtubeFragment = FragamentYoutube()
        val bundle = Bundle()
        bundle.putString(KEY_VIDEO_ID, video_id)
        youtubeFragment.setArguments(bundle)
        return youtubeFragment
    }
}




    private var mVideoId: String? = null
    private var player: YouTubePlayer? = null


    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        val arguments = getArguments()
        if (bundle != null && bundle.containsKey(KEY_VIDEO_ID)) {
            mVideoId = bundle.getString(KEY_VIDEO_ID)
        } else if (arguments != null && arguments!!.containsKey(KEY_VIDEO_ID)) {
            mVideoId = arguments!!.getString(KEY_VIDEO_ID)
        }
        //        initialize(Constant.YOUTUBE_DEVELOPER_KEY, this);
    }

    override fun onSaveInstanceState(bundle: Bundle) {
        super.onSaveInstanceState(bundle)
        bundle.putString(KEY_VIDEO_ID, mVideoId)
    }

    override fun onResume() {
        super.onResume()
        if (player == null) {
            initialize("AIzaSyCHeggL7iOgcXWRCcDCFjbQ7RduSW2LTRY", this)
        } else if (!player!!.isPlaying()) {
            Log.d(TAG, "onResume play")
            player!!.loadVideo(mVideoId, player!!.getCurrentTimeMillis())
        }
    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider, youTubePlayer: YouTubePlayer, restored: Boolean) {
        player = youTubePlayer
        youTubePlayer.setFullscreen(false)
        youTubePlayer.setShowFullscreenButton(false)
        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
        if (!restored) {
            youTubePlayer.loadVideo(mVideoId)
        } else {
            youTubePlayer.play()
        }
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider,
        youTubeInitializationResult: YouTubeInitializationResult
    ) {
        AppUtil.toast(youTubeInitializationResult.toString())
    }

    override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (player != null) {
                player!!.release()
                player = null
            }
        }
    }
}