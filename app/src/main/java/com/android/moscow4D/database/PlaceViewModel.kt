import android.app.Application
import androidx.lifecycle.*
import com.android.moscow4D.database.PlaceEntity
import com.android.moscow4D.database.PlaceRepository
import com.android.moscow4D.database.PlaceRoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class PlaceViewModel(application: Application) : AndroidViewModel(application) {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allPlaces: LiveData<List<PlaceEntity>>
    val repository: PlaceRepository

    init {
        val placesDao = PlaceRoomDatabase.getDatabase(application).plaseListDao()
        repository = PlaceRepository(placesDao)
        allPlaces = repository.allPlaces
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(place: PlaceEntity) = viewModelScope.launch {
        repository.insert(place)
    }
}/*
class PlaceViewModelFactory(private val repository: PlaceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlaceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlaceViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
*/