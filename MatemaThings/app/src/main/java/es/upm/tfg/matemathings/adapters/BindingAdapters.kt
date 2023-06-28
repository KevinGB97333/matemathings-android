package es.upm.tfg.matemathings.adapters

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.OriginalSize
import coil.size.Precision
import coil.transform.BlurTransformation
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import es.upm.tfg.matemathings.R
import es.upm.tfg.matemathings.network.models.Video
import es.upm.tfg.matemathings.models.concept.Concept
import es.upm.tfg.matemathings.viewmodels.conceptsGetApiStatus
import es.upm.tfg.matemathings.viewmodels.videosGetApiStatus


@BindingAdapter("listVideos")
fun bindVideos(recyclerView: RecyclerView, data: List<Video>?) {
	val adapter = recyclerView.adapter as VideoListAdapter
	adapter.submitList(data?.toMutableList())
}

@BindingAdapter("videoID")
fun bindVideoId(youTubePlayerView: YouTubePlayerView, videoId : String?) {
	if (videoId != null) {
		youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
			override fun onReady(youTubePlayer: YouTubePlayer) {
				youTubePlayer.cueVideo(videoId = videoId, 0F)
			}
		})
		youTubePlayerView.findViewTreeLifecycleOwner()?.lifecycle?.addObserver(youTubePlayerView)
	}
}
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl : String?){
	imgUrl?.let {
		val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
		imgView.load(imgUri) {
			crossfade(true)
			crossfade(400)
			precision(Precision.EXACT)
			size(OriginalSize)
			placeholder(R.drawable.loading_animation)
			error(R.drawable.baseline_broken_image_24)
		}
	}
}
@BindingAdapter("imageUrlBlur")
fun bindImageBlur(imgView: ImageView, imgUrl : String?){
	imgUrl?.let {
		val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
		imgView.load(imgUri) {
			crossfade(true)
			crossfade(400)
			precision(Precision.EXACT)
			transformations(
				BlurTransformation(imgView.context, radius = 8F)
			)
		}
	}
}
@BindingAdapter("listConcepts")
fun bindConcepts(recyclerView: RecyclerView, data: List<Concept>?) {
	val adapter = recyclerView.adapter as ConceptListAdapter
	adapter.submitList(data?.toMutableList())
}
@BindingAdapter("location")
fun bindLocation(button: Button, uri : String?) {
	button.setOnClickListener {
		val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
		button.context.startActivity(intent)
	}
}
@BindingAdapter("conceptsApiStatus")
fun bindConceptsStatus(relativeLayout: RelativeLayout, status: conceptsGetApiStatus?) {
	relativeLayout.visibility = when (status) {
		conceptsGetApiStatus.LOADING -> View.VISIBLE
		conceptsGetApiStatus.DONE -> View.GONE
		else -> { Toast.makeText(relativeLayout.context,"No se han podido obtener los conceptos", Toast.LENGTH_SHORT ).show()
			View.GONE}
	}
}
@BindingAdapter("videoApiStatus")
fun bindVideoStatus(relativeLayout: RelativeLayout, status: videosGetApiStatus?){
	relativeLayout.visibility = when (status) {
		videosGetApiStatus.LOADING -> View.VISIBLE
		videosGetApiStatus.DONE -> View.GONE
		else -> { Toast.makeText(relativeLayout.context,"No se han podido obtener los videos", Toast.LENGTH_SHORT ).show()
		View.GONE}
	}
}