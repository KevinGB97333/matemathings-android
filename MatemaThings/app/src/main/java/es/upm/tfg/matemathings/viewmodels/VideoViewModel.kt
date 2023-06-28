package es.upm.tfg.matemathings.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.upm.tfg.matemathings.network.models.Video
import es.upm.tfg.matemathings.network.restApi
import kotlinx.coroutines.launch

enum class videosGetApiStatus { LOADING, ERROR, DONE }
class VideoViewModel : ViewModel() {

	private val _videoIds = MutableLiveData<List<Video>>()
	val videoIds : LiveData<List<Video>> = _videoIds

	private val _videosGetStatus = MutableLiveData<videosGetApiStatus>()
	val videosGetStatus : LiveData<videosGetApiStatus> = _videosGetStatus

	init {
		reset()
	}

	public fun reset() {
		_videoIds.value = mutableListOf()
		_videosGetStatus.value = videosGetApiStatus.DONE
	}

	fun getVideos(conceptId: Int){
		viewModelScope.launch {
			_videosGetStatus.value = videosGetApiStatus.LOADING
			try {
				val response = restApi.retrofitService.getVideos(conceptId)
				when (response.code()) {
					200 -> {
						_videoIds.value = response.body()?.videoIds
						_videosGetStatus.value = videosGetApiStatus.DONE
					}
					else -> {
						_videoIds.value = mutableListOf()
						_videosGetStatus.value = videosGetApiStatus.ERROR
					}
				}
			} catch (e : Exception) {
				e.printStackTrace()
				_videoIds.value = mutableListOf()
				_videosGetStatus.value = videosGetApiStatus.ERROR
			}
		}
	}
}