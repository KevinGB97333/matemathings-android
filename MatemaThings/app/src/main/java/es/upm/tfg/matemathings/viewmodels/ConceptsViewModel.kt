package es.upm.tfg.matemathings.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.upm.tfg.matemathings.models.concept.Concept
import es.upm.tfg.matemathings.network.restApi
import kotlinx.coroutines.launch

enum class categoryTypes { NATURE, ART, ARCH}
enum class conceptsGetApiStatus { LOADING, ERROR, DONE }
class ConceptsViewModel : ViewModel() {

	private val _natureConcepts = MutableLiveData<List<Concept>>()
	val natureConcepts : LiveData<List<Concept>> = _natureConcepts
	private val _artConcepts = MutableLiveData<List<Concept>>()
	val artConcepts : LiveData<List<Concept>> = _artConcepts
	private val _archConcepts = MutableLiveData<List<Concept>>()
	val archConcepts : LiveData<List<Concept>> = _archConcepts

	private val _suggestedConcepts = MutableLiveData<List<Concept>>()
	val suggestedConcepts : LiveData<List<Concept>> = _suggestedConcepts

	private val _conceptDetail = MutableLiveData<Concept?>()
	val conceptDetail : LiveData<Concept?> = _conceptDetail

	private val _conceptsGetStatus = MutableLiveData<conceptsGetApiStatus>()
	val conceptsGetStatus : LiveData<conceptsGetApiStatus> = _conceptsGetStatus

	init {
		reset()
	}

	private fun reset() {
		_natureConcepts.value = mutableListOf()
		_artConcepts.value = mutableListOf()
		_archConcepts.value = mutableListOf()
		_suggestedConcepts.value = mutableListOf()
		_conceptDetail.value = null
		_conceptsGetStatus.value = conceptsGetApiStatus.DONE
	}
	//TODO AÑADIR FILTRADO POR CATEGORIA ?category=nature
	fun getConcepts() {
		viewModelScope.launch {
			_conceptsGetStatus.value = conceptsGetApiStatus.LOADING
			try {
				val response = restApi.retrofitService.getConcepts()
				when (response.code()) {
					200 -> {
						val conceptsList = response.body()!!.concepts
						_natureConcepts.value = conceptsList.filter { it.concept.category == categoryTypes.NATURE.name.lowercase() }
						_artConcepts.value = conceptsList.filter { it.concept.category == categoryTypes.ART.name.lowercase() }
						_archConcepts.value = conceptsList.filter { it.concept.category == categoryTypes.ARCH.name.lowercase() }
						_conceptsGetStatus.value = conceptsGetApiStatus.DONE
					}
					else -> {
						_artConcepts.value = mutableListOf()
						_conceptsGetStatus.value = conceptsGetApiStatus.ERROR
					}
				}
			} catch (e : Exception) {
				e.printStackTrace()
				_artConcepts.value = mutableListOf()
				_conceptsGetStatus.value = conceptsGetApiStatus.ERROR
			}
		}
	}
	fun generateSuggestedList(conceptId: Int, category: String) {
		viewModelScope.launch {
			_suggestedConcepts.value = when (category) {
				categoryTypes.NATURE.name.lowercase() -> {
					_natureConcepts.value?.filter {
						if(it.concept.id == conceptId){
							_conceptDetail.value = it
						}
						it.concept.id != conceptId
					}
						?.shuffled()
						?: listOf()
				}
				categoryTypes.ART.name.lowercase() -> {
					_artConcepts.value?.filter {
						if(it.concept.id == conceptId){
							_conceptDetail.value = it
						}
						it.concept.id != conceptId
					}
						?.shuffled()
						?: listOf()
				}
				categoryTypes.ARCH.name.lowercase() -> {
					_archConcepts.value?.filter {
						if(it.concept.id == conceptId){
							_conceptDetail.value = it
						}
						it.concept.id != conceptId
					}
						?.shuffled()
						?: listOf()
				}
				else -> listOf()
			}
		}
	}
}