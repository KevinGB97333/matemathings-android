package es.upm.tfg.matemathings.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.upm.tfg.matemathings.databinding.VideoItemBinding
import es.upm.tfg.matemathings.network.models.Video
import es.upm.tfg.matemathings.viewmodels.VideoViewModel


class VideoListAdapter(private val videoSharedViewModel: VideoViewModel): ListAdapter<Video,VideoListAdapter.VideoViewHolder>(DiffCallback) {

	companion object DiffCallback : DiffUtil.ItemCallback<Video>() {
		override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
			return oldItem.id == newItem.id
		}

		override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
			return oldItem.id == newItem.id
		}
	}

	class VideoViewHolder(
		 private var binding: VideoItemBinding
	) : RecyclerView.ViewHolder(binding.root) {
		fun bind(video : Video, videoSharedViewModel: VideoViewModel) {
			binding.apply {
				videoViewModel = videoSharedViewModel
				videoId = video.id
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
		return VideoViewHolder(
			VideoItemBinding.inflate(LayoutInflater.from(parent.context))
		)
	}

	override fun onBindViewHolder(holder: VideoListAdapter.VideoViewHolder, position: Int) {
		val video = getItem(position)
		holder.bind(video, videoSharedViewModel)
	}
}